package com.example.mova_projectfinal.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.mova_projectfinal.domain.repository.UserPrefsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPrefsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPrefsRepository {

    private val onboardingKey = booleanPreferencesKey("onboarding_done")

    override suspend fun isOnboardingDone(): Boolean =
        dataStore.data.first()[onboardingKey] ?: false

    override suspend fun setOnboardingDone() {
        dataStore.edit { prefs -> prefs[onboardingKey] = true }
    }
}