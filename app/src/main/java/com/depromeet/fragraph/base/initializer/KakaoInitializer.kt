package com.depromeet.fragraph.base.initializer

import android.app.Application
import com.depromeet.fragraph.R
import com.kakao.sdk.common.KakaoSdk

class KakaoInitializer: AppInitializer {
    override fun initialize(application: Application) {
        KakaoSdk.init(application, application.getString(R.string.kakao_sdk_app_key))
    }
}