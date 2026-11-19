package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.User
import com.example.mova_projectfinal.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> =
        repository.login(email.trim(), password)
}