package com.example.mova_projectfinal.core.util

object Validators {

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun emailError(email: String): String? = when {
        email.isBlank() -> "Email is required"
        !emailRegex.matches(email.trim()) -> "Enter a valid email"
        else -> null
    }

    fun passwordError(password: String): String? = when {
        password.isBlank() -> "Password is required"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }

    fun fullNameError(name: String): String? = when {
        name.isBlank() -> "Full name is required"
        name.trim().length < 2 -> "Name is too short"
        else -> null
    }

    fun nicknameError(nickname: String): String? = when {
        nickname.isBlank() -> "Nickname is required"
        else -> null
    }

    fun phoneError(phone: String): String? {
        if (phone.isBlank()) return null
        val digits = phone.filter { it.isDigit() }
        return if (digits.length !in 7..15) "Enter a valid phone number" else null
    }

    fun confirmPasswordError(password: String, confirm: String): String? = when {
        confirm.isBlank() -> "Please confirm your password"
        password != confirm -> "Passwords do not match"
        else -> null
    }
}