package com.example.mova_projectfinal.di

import com.example.mova_projectfinal.data.repository.AuthRepositoryImpl
import com.example.mova_projectfinal.data.repository.ProfileRepositoryImpl
import com.example.mova_projectfinal.data.repository.UserPrefsRepositoryImpl
import com.example.mova_projectfinal.domain.repository.AuthRepository
import com.example.mova_projectfinal.domain.repository.ProfileRepository
import com.example.mova_projectfinal.domain.repository.UserPrefsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserPrefsRepository(impl: UserPrefsRepositoryImpl): UserPrefsRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}