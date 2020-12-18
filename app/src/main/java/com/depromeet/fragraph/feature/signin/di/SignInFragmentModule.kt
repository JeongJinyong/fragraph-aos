package com.depromeet.fragraph.feature.signin.di

import androidx.fragment.app.Fragment
import com.depromeet.fragraph.R
import com.depromeet.fragraph.feature.signin.SignInFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object SignInFragmentModule {

    @Provides
    fun provideSignInFragment(fragment: Fragment): SignInFragment = fragment as SignInFragment

    @Provides
    fun providesGoogleSignInOptions(
        fragment: Fragment
    ): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(fragment.getString(R.string.google_client_id))
            .requestEmail()
            .build()
    }

    @Provides
    fun providesGoogleSignInClient(
        fragment: Fragment,
        gso: GoogleSignInOptions,
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(fragment.requireContext(), gso)
    }
}