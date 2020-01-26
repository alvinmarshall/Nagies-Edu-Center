package com.cheise_proj.parentapp.di.module.remote

import com.cheise_proj.common_module.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class TokenService(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader(AUTHORIZATION_HEADER, token).build()
        return chain.proceed(request)
    }
}