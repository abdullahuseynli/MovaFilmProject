package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.domain.model.User
import com.example.mova_projectfinal.domain.repository.AuthRepository
import jakarta.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): User? = repository.getCurrentUser()
}