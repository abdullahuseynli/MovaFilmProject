package com.example.mova_projectfinal.screen.newpassword

class CreateNewPasswordContract {

    data class State(
        val password: String = "",
        val confirmPassword: String = "",
        val rememberMe: Boolean = false,
        val isPasswordVisible: Boolean = false,
        val isConfirmVisible: Boolean = false,
        val passwordError: String? = null,
        val confirmError: String? = null,
        val isLoading: Boolean = false,
        val showSuccessDialog: Boolean = false
    )

    sealed interface Intent {
        data class PasswordChanged(val value: String) : Intent
        data class ConfirmChanged(val value: String) : Intent
        data class RememberMeChanged(val value: Boolean) : Intent
        data object TogglePasswordVisibility : Intent
        data object ToggleConfirmVisibility : Intent
        data object ContinueClicked : Intent
        data object DialogDismissed : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        data object NavigateToLogin : Effect
    }
}