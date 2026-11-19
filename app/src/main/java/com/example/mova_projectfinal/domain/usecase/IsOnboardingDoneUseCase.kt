package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.domain.repository.UserPrefsRepository
import javax.inject.Inject

class IsOnboardingDoneUseCase @Inject constructor(
    private val repository: UserPrefsRepository
) {
    suspend operator fun invoke(): Boolean = repository.isOnboardingDone()
}