package com.cheise_proj.parentapp.di.module.remote

import android.content.Context
import android.content.SharedPreferences
import com.cheise_proj.common_module.AUTHORIZATION_HEADER
import com.cheise_proj.parentapp.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenService @Inject constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token =
            sharedPreferences.getString(context.getString(R.string.pref_login_user_token_key), null)
                ?: ""
        var request = chain.request()
        request = request.newBuilder().addHeader(AUTHORIZATION_HEADER, token).build()
        return chain.proceed(request)
    }
}