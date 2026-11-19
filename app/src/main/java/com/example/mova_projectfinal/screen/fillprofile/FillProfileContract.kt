package com.example.mova_projectfinal.screen.fillprofile

import android.net.Uri

class FillProfileContract {

    data class State(
        val photoUri: Uri? = null,
        val fullName: String = "",
        val nickname: String = "",
        val email: String = "",
        val phone: String = "",
        val gender: String = "",
        val fullNameError: String? = null,
        val nicknameError: String? = null,
        val phoneError: String? = null,
        val isLoading: Boolean = false
    ) {
        companion object {
            val genders = listOf("Male", "Female", "Other")
        }
    }

    sealed interface Intent {
        data class PhotoSelected(val uri: Uri?) : Intent
        data class FullNameChanged(val value: String) : Intent
        data class NicknameChanged(val value: String) : Intent
        data class PhoneChanged(val value: String) : Intent
        data class GenderSelected(val value: String) : Intent
        data object ContinueClicked : Intent
        data object SkipClicked : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        data object NavigateToHome : Effect
        data class ShowMessage(val message: String) : Effect
    }
}