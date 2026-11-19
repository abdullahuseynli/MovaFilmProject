package com.example.mova_projectfinal.screen.LetsYouIn

class LetsYouInContract {
    data object State

    sealed interface Intent {
        data object FacebookClicked : Intent
        data object GoogleClicked : Intent
        data object AppleClicked : Intent
        data object SignInWithPasswordClicked : Intent
        data object SignUpClicked : Intent
    }

    sealed interface Effect {
        data object NavigateToLogin : Effect
        data object NavigateToRegister : Effect
        data class ShowMessage(val message: String) : Effect
    }
}