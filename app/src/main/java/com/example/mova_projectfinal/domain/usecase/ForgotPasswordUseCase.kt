package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.domain.repository.AuthRepository
import jakarta.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.sendPasswordReset(email.trim())
}