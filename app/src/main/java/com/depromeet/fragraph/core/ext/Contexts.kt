package com.depromeet.fragraph.core.ext

import android.content.Context
import android.content.SharedPreferences
import com.depromeet.fragraph.core.KEY_AUTH_SHARED

fun Context.authSharedPreferences(): SharedPreferences = getSharedPreferences(KEY_AUTH_SHARED, Context.MODE_PRIVATE)