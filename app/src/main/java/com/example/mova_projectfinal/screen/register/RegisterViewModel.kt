package com.example.mova_projectfinal.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.core.util.Validators
import com.example.mova_projectfinal.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<RegisterContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: RegisterContract.Intent) {
        when (intent) {
            is RegisterContract.Intent.EmailChanged ->
                _state.update { it.copy(email = intent.value, emailError = null) }

            is RegisterContract.Intent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, passwordError = null) }

            is RegisterContract.Intent.RememberMeChanged ->
                _state.update { it.copy(rememberMe = intent.value) }

            RegisterContract.Intent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            RegisterContract.Intent.SignUpClicked -> signUp()

            RegisterContract.Intent.SignInClicked ->
                sendEffect(RegisterContract.Effect.NavigateToLogin)

            RegisterContract.Intent.BackClicked ->
                sendEffect(RegisterContract.Effect.NavigateBack)

            is RegisterContract.Intent.SocialClicked ->
                sendEffect(RegisterContract.Effect.ShowMessage("${intent.provider} sign up coming soon"))
        }
    }

    private fun signUp() {
        val current = _state.value
        val emailError = Validators.emailError(current.email)
        val passwordError = Validators.passwordError(current.password)

        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = registerUseCase(current.email, current.password)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(RegisterContract.Effect.NavigateToHome)
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(RegisterContract.Effect.ShowMessage(result.message))
                }
            }
        }
    }

    private fun sendEffect(effect: RegisterContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}