package com.example.mova_projectfinal.screen.onboarding

class OnboardingContract {

    data class State(
        var currentPage: Int = 0
    )

    sealed interface Intent {
        data class PageChanged(val page: Int) : Intent
        data object GetStartedClicked : Intent
    }

    sealed interface Effect {
        data object NavigateToLetsYouIn : Effect
    }
}