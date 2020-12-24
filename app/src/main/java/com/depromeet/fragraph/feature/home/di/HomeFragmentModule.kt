package com.depromeet.fragraph.feature.home.di

import androidx.fragment.app.Fragment
import com.depromeet.fragraph.feature.home.HomeFragment
import com.depromeet.fragraph.feature.signin.SignInFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object HomeFragmentModule {

    @Provides
    fun providesHomeFragment(fragment: Fragment): HomeFragment = fragment as HomeFragment
}