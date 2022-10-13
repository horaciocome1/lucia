package io.github.horaciocome1.lucia.api

import io.github.horaciocome1.lucia.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class LuciaInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", BuildConfig.API_KEY)
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()
        return chain.proceed(request)
    }
}