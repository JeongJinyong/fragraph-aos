package com.depromeet.fragraph.data.repository

import com.depromeet.fragraph.domain.repository.AuthRepository
import com.depromeet.fragraph.domain.repository.IncenseRepository
import com.depromeet.fragraph.domain.repository.ReportRepository
import com.depromeet.fragraph.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun authRepository(impl: DataAuthRepository): AuthRepository

    @Binds
    abstract fun userRepository(impl: DataUserRepository): UserRepository

    @Binds
    abstract fun incenseRepository(impl: DataFragraphRepository): IncenseRepository

    @Binds
    abstract fun reportRepository(impl: DataFragraphRepository): ReportRepository
}