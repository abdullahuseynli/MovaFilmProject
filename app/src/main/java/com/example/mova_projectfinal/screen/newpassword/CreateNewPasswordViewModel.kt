package com.example.mova_projectfinal.screen.newpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.core.util.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewPasswordViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CreateNewPasswordContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<CreateNewPasswordContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: CreateNewPasswordContract.Intent) {
        when (intent) {
            is CreateNewPasswordContract.Intent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, passwordError = null) }

            is CreateNewPasswordContract.Intent.ConfirmChanged ->
                _state.update { it.copy(confirmPassword = intent.value, confirmError = null) }

            is CreateNewPasswordContract.Intent.RememberMeChanged ->
                _state.update { it.copy(rememberMe = intent.value) }

            CreateNewPasswordContract.Intent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            CreateNewPasswordContract.Intent.ToggleConfirmVisibility ->
                _state.update { it.copy(isConfirmVisible = !it.isConfirmVisible) }

            CreateNewPasswordContract.Intent.ContinueClicked -> submit()

            CreateNewPasswordContract.Intent.DialogDismissed -> {
                _state.update { it.copy(showSuccessDialog = false) }
                sendEffect(CreateNewPasswordContract.Effect.NavigateToLogin)
            }

            CreateNewPasswordContract.Intent.BackClicked ->
                sendEffect(CreateNewPasswordContract.Effect.NavigateBack)
        }
    }

    private fun submit() {
        val current = _state.value
        val passwordError = Validators.passwordError(current.password)
        val confirmError =
            Validators.confirmPasswordError(current.password, current.confirmPassword)

        if (passwordError != null || confirmError != null) {
            _state.update { it.copy(passwordError = passwordError, confirmError = confirmError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1200)
            _state.update { it.copy(isLoading = false, showSuccessDialog = true) }
        }
    }

    private fun sendEffect(effect: CreateNewPasswordContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}