package com.example.mova_projectfinal.screen.LetsYouIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LetsYouInViewModel : ViewModel() {

    private val _effect = Channel<LetsYouInContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LetsYouInContract.Intent) {
        when (intent) {
            LetsYouInContract.Intent.SignInWithPasswordClicked ->
                sendEffect(LetsYouInContract.Effect.NavigateToLogin)

            LetsYouInContract.Intent.SignUpClicked ->
                sendEffect(LetsYouInContract.Effect.NavigateToRegister)

            LetsYouInContract.Intent.FacebookClicked ->
                sendEffect(LetsYouInContract.Effect.ShowMessage("Facebook login coming soon"))

            LetsYouInContract.Intent.GoogleClicked ->
                sendEffect(LetsYouInContract.Effect.ShowMessage("Google login coming soon"))

            LetsYouInContract.Intent.AppleClicked ->
                sendEffect(LetsYouInContract.Effect.ShowMessage("Apple login coming soon"))
        }
    }

    private fun sendEffect(effect: LetsYouInContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}