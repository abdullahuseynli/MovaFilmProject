package com.example.mova_projectfinal.screen.fillprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.core.util.Validators
import com.example.mova_projectfinal.domain.model.UserProfile
import com.example.mova_projectfinal.domain.usecase.GetCurrentUserUseCase
import com.example.mova_projectfinal.domain.usecase.SaveProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase,
    getCurrentUser: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        FillProfileContract.State(email = getCurrentUser()?.email.orEmpty())
    )
    val state = _state.asStateFlow()

    private val _effect = Channel<FillProfileContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: FillProfileContract.Intent) {
        when (intent) {
            is FillProfileContract.Intent.PhotoSelected ->
                _state.update { it.copy(photoUri = intent.uri ?: it.photoUri) }

            is FillProfileContract.Intent.FullNameChanged ->
                _state.update { it.copy(fullName = intent.value, fullNameError = null) }

            is FillProfileContract.Intent.NicknameChanged ->
                _state.update { it.copy(nickname = intent.value, nicknameError = null) }

            is FillProfileContract.Intent.PhoneChanged ->
                _state.update { it.copy(phone = intent.value, phoneError = null) }

            is FillProfileContract.Intent.GenderSelected ->
                _state.update { it.copy(gender = intent.value) }

            FillProfileContract.Intent.ContinueClicked -> save()

            FillProfileContract.Intent.SkipClicked ->
                sendEffect(FillProfileContract.Effect.NavigateToHome)

            FillProfileContract.Intent.BackClicked ->
                sendEffect(FillProfileContract.Effect.NavigateBack)
        }
    }

    private fun save() {
        val current = _state.value
        val fullNameError = Validators.fullNameError(current.fullName)
        val nicknameError = Validators.nicknameError(current.nickname)
        val phoneError = Validators.phoneError(current.phone)

        if (fullNameError != null || nicknameError != null || phoneError != null) {
            _state.update {
                it.copy(
                    fullNameError = fullNameError,
                    nicknameError = nicknameError,
                    phoneError = phoneError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val profile = UserProfile(
                fullName = current.fullName,
                nickname = current.nickname,
                email = current.email,
                phone = current.phone,
                gender = current.gender
            )

            when (val result = saveProfileUseCase(profile)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(FillProfileContract.Effect.NavigateToHome)
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(FillProfileContract.Effect.ShowMessage(result.message))
                }
            }
        }
    }

    private fun sendEffect(effect: FillProfileContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}