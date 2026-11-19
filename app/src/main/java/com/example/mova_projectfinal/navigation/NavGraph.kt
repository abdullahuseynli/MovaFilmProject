package com.example.mova_projectfinal.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mova_projectfinal.screen.LetsYouIn.LetsYouInScreen
import com.example.mova_projectfinal.screen.fillprofile.FillProfileScreen
import com.example.mova_projectfinal.screen.forgotpassword.ForgotPasswordScreen
import com.example.mova_projectfinal.screen.login.LoginScreen
import com.example.mova_projectfinal.screen.newpassword.CreateNewPasswordScreen
import com.example.mova_projectfinal.screen.onboarding.OnboardingScreen
import com.example.mova_projectfinal.screen.register.RegisterScreen
import com.example.mova_projectfinal.screen.splash.SplashScreen
import com.example.mova_projectfinal.ui.theme.MovaBackground

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                },
                onNavigateToLetsYouIn = {
                    navController.navigate(Screen.LetsYouIn) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Onboarding> {
            OnboardingScreen(
                onNavigateToLetsYouIn = {
                    navController.navigate(Screen.LetsYouIn) {
                        popUpTo(Screen.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.LetsYouIn> {
            LetsYouInScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login) },
                onNavigateToRegister = { navController.navigate(Screen.Register) }
            )
        }

        composable<Screen.Login> {
            LoginScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.LetsYouIn) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register) {
                        popUpTo(Screen.LetsYouIn)
                    }
                },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword) }
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.LetsYouIn)
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.FillProfile) {
                        popUpTo(Screen.LetsYouIn) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Screen.FillProfile> {
            FillProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.FillProfile) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Home> {
            PlaceholderScreen("Home")
        }

        composable<Screen.CreateNewPassword> {
            CreateNewPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.LetsYouIn)
                    }
                }
            )
        }


    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MovaBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = Color.White)
    }
}