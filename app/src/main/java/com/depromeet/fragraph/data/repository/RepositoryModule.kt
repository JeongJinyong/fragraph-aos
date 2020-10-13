package com.depromeet.fragraph.data.repository

import com.depromeet.fragraph.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun authRepository(impl: DataAuthRepository): AuthRepository

}