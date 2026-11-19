package com.example.mova_projectfinal.domain.repository

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun register(email: String, password: String): Resource<User>
    suspend fun sendPasswordReset(email: String): Resource<Unit>
    fun getCurrentUser(): User?
    fun logout()
}