package com.example.mova_projectfinal.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object LetsYouIn : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object ForgotPassword : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object FillProfile : Screen

    @Serializable
    data object CreateNewPassword : Screen
}