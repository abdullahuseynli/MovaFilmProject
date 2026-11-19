package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.domain.repository.UserPrefsRepository
import javax.inject.Inject

class SetOnboardingDoneUseCase @Inject constructor(
    private val repository: UserPrefsRepository
) {
    suspend operator fun invoke() = repository.setOnboardingDone()
}