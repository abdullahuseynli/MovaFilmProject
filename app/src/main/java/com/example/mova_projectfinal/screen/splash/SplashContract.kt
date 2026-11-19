package com.example.mova_projectfinal.screen.splash

class SplashContract {

    data object State

    sealed interface Intent {
        data object StartTimer : Intent
    }

    sealed interface Effect {
        data object NavigateToOnboarding : Effect
        data object NavigateToLetsYouIn : Effect
        data object NavigateToHome : Effect
    }
}