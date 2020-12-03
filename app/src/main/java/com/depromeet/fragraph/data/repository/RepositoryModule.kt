package com.depromeet.fragraph.data.repository

import com.depromeet.fragraph.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun authRepository(impl: DataAuthRepository): AuthRepository

    @Singleton
    @Binds
    abstract fun userRepository(impl: DataUserRepository): UserRepository

    @Singleton
    @Binds
    abstract fun incenseRepository(impl: DataFragraphRepository): IncenseRepository

    @Singleton
    @Binds
    abstract fun keywordRepository(impl: DataFragraphRepository): KeywordRepository

    @Singleton
    @Binds
    abstract fun reportRepository(impl: DataFragraphRepository): ReportRepository

    @Singleton
    @Binds
    abstract fun historyRepository(impl: DataFragraphRepository): HistoryRepository

    @Singleton
    @Binds
    abstract fun meditationRepository(impl: DataFragraphRepository): MeditationRepository
}