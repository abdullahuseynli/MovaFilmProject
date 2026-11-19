package com.example.mova_projectfinal.screen.register

class RegisterContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val rememberMe: Boolean = false,
        val isPasswordVisible: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
        val isLoading: Boolean = false
    )

    sealed interface Intent {
        data class EmailChanged(val value: String) : Intent
        data class PasswordChanged(val value: String) : Intent
        data class RememberMeChanged(val value: Boolean) : Intent
        data object TogglePasswordVisibility : Intent
        data object SignUpClicked : Intent
        data object SignInClicked : Intent
        data object BackClicked : Intent
        data class SocialClicked(val provider: String) : Intent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        data object NavigateToLogin : Effect
        data object NavigateToHome : Effect
        data class ShowMessage(val message: String) : Effect
    }
}