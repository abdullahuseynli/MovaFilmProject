package com.example.mova_projectfinal.screen.forgotpassword

class ForgotPasswordContract {

    data class State(
        val email: String = "",
        val emailError: String? = null,
        val isLoading: Boolean = false,
        val isEmailSent: Boolean = false
    )

    sealed interface Intent {
        data class EmailChanged(val value: String) : Intent
        data object SendClicked : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        data class ShowMessage(val message: String) : Effect
    }
}