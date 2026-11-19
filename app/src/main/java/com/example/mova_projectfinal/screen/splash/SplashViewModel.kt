package com.example.mova_projectfinal.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.domain.usecase.GetCurrentUserUseCase
import com.example.mova_projectfinal.domain.usecase.IsOnboardingDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val isOnboardingDone: IsOnboardingDoneUseCase
) : ViewModel() {

    private val _effect = Channel<SplashContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(SplashContract.Intent.StartTimer)
    }

    fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.StartTimer -> startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3000)

            val effect = when {
                getCurrentUser() != null -> SplashContract.Effect.NavigateToHome
                isOnboardingDone() -> SplashContract.Effect.NavigateToLetsYouIn
                else -> SplashContract.Effect.NavigateToOnboarding
            }
            _effect.send(effect)
        }
    }
}
