package com.example.mova_projectfinal.domain.repository

interface UserPrefsRepository {
    suspend fun isOnboardingDone(): Boolean
    suspend fun setOnboardingDone()
}