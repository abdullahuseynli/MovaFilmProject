package com.example.mova_projectfinal.domain.repository

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.UserProfile

interface ProfileRepository {
    suspend fun saveProfile(profile: UserProfile): Resource<Unit>
}