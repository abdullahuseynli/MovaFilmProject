package com.example.mova_projectfinal.screen.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.core.util.Validators
import com.example.mova_projectfinal.domain.usecase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<ForgotPasswordContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: ForgotPasswordContract.Intent) {
        when (intent) {
            is ForgotPasswordContract.Intent.EmailChanged ->
                _state.update { it.copy(email = intent.value, emailError = null) }

            ForgotPasswordContract.Intent.SendClicked -> sendResetLink()

            ForgotPasswordContract.Intent.BackClicked ->
                sendEffect(ForgotPasswordContract.Effect.NavigateBack)
        }
    }

    private fun sendResetLink() {
        val email = _state.value.email
        val emailError = Validators.emailError(email)

        if (emailError != null) {
            _state.update { it.copy(emailError = emailError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = forgotPasswordUseCase(email)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, isEmailSent = true) }
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(ForgotPasswordContract.Effect.ShowMessage(result.message))
                }
            }
        }
    }

    private fun sendEffect(effect: ForgotPasswordContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}