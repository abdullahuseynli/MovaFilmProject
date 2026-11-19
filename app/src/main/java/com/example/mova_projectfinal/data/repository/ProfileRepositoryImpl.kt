package com.example.mova_projectfinal.data.repository

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.UserProfile
import com.example.mova_projectfinal.domain.repository.ProfileRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun saveProfile(profile: UserProfile): Resource<Unit> {
        val uid = auth.currentUser?.uid
            ?: return Resource.Error("You are not signed in")

        return try {
            val data = mapOf(
                "fullName" to profile.fullName,
                "nickname" to profile.nickname,
                "email" to profile.email,
                "phone" to profile.phone,
                "gender" to profile.gender
            )
            firestore.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .await()
            Resource.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseNetworkException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error("Could not save your profile. Please try again")
        }
    }
}