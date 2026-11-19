package com.example.mova_projectfinal.screen.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.core.util.Validators
import com.example.mova_projectfinal.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<LoginContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.EmailChanged ->
                _state.update { it.copy(email = intent.value, emailError = null) }

            is LoginContract.Intent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, passwordError = null) }

            is LoginContract.Intent.RememberMeChanged ->
                _state.update { it.copy(rememberMe = intent.value) }

            LoginContract.Intent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            LoginContract.Intent.SignInClicked -> signIn()

            LoginContract.Intent.ForgotPasswordClicked ->
                sendEffect(LoginContract.Effect.NavigateToForgotPassword)

            LoginContract.Intent.SignUpClicked ->
                sendEffect(LoginContract.Effect.NavigateToRegister)

            LoginContract.Intent.BackClicked ->
                sendEffect(LoginContract.Effect.NavigateBack)

            is LoginContract.Intent.SocialClicked ->
                sendEffect(LoginContract.Effect.ShowMessage("${intent.provider} login coming soon"))
        }
    }

    private fun signIn() {
        val current = _state.value
        val emailError = Validators.emailError(current.email)
        val passwordError = Validators.passwordError(current.password)

        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = loginUseCase(current.email, current.password)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(LoginContract.Effect.NavigateToHome)
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(LoginContract.Effect.ShowMessage(result.message))
                }
            }
        }
    }

    private fun sendEffect(effect: LoginContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
