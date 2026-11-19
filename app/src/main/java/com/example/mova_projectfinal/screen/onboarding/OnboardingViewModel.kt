package com.example.mova_projectfinal.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova_projectfinal.domain.usecase.SetOnboardingDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingDone: SetOnboardingDoneUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<OnboardingContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: OnboardingContract.Intent) {
        when (intent) {
            is OnboardingContract.Intent.PageChanged -> {
                _state.update { it.copy(currentPage = intent.page) }
            }

            OnboardingContract.Intent.GetStartedClicked -> {
                viewModelScope.launch {
                    setOnboardingDone()
                    _effect.send(OnboardingContract.Effect.NavigateToLetsYouIn)
                }
            }
        }
    }
}