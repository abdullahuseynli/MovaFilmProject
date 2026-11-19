package com.example.mova_projectfinal.domain.usecase

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.UserProfile
import com.example.mova_projectfinal.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile): Resource<Unit> =
        repository.saveProfile(
            profile.copy(
                fullName = profile.fullName.trim(),
                nickname = profile.nickname.trim(),
                phone = profile.phone.trim()
            )
        )
}