package com.example.mova_projectfinal.data.repository

import com.example.mova_projectfinal.core.util.Resource
import com.example.mova_projectfinal.domain.model.User
import com.example.mova_projectfinal.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<User> = safeCall {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user!!.toDomain()
    }

    override suspend fun register(email: String, password: String): Resource<User> = safeCall {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user!!.toDomain()
    }

    override suspend fun sendPasswordReset(email: String): Resource<Unit> = safeCall {
        auth.sendPasswordResetEmail(email).await()
        Unit
    }

    override fun getCurrentUser(): User? = auth.currentUser?.toDomain()

    override fun logout() = auth.signOut()

    private suspend fun <T> safeCall(block: suspend () -> T): Resource<T> = try {
        Resource.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Resource.Error(e.toUserMessage())
    }

    private fun FirebaseUser.toDomain() = User(id = uid, email = email.orEmpty())

    private fun Exception.toUserMessage(): String = when (this) {
        is FirebaseAuthInvalidUserException -> "No account found with this email"
        is FirebaseAuthInvalidCredentialsException -> "Incorrect email or password"
        is FirebaseAuthUserCollisionException -> "This email is already registered"
        is FirebaseAuthWeakPasswordException -> "Password is too weak"
        is FirebaseNetworkException -> "No internet connection"
        else -> "Something went wrong. Please try again"
    }
}