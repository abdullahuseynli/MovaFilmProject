package com.example.mova_projectfinal.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.ui.theme.MovaBackground


@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToLetsYouIn: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashContract.Effect.NavigateToOnboarding -> onNavigateToOnboarding()
                SplashContract.Effect.NavigateToLetsYouIn -> onNavigateToLetsYouIn()
                SplashContract.Effect.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MovaBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.mova_logo),
            contentDescription = "Mova logo",
            modifier = Modifier.size(120.dp)
        )
    }
}
