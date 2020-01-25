package com.cheise_proj.remote_source.api

import com.cheise_proj.remote_source.model.dto.UserDto
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @POST("users")
    fun getAuthenticateUser(
        @Query("role") role:String,
        @Body credentials: LoginRequest
    ): Observable<UserDto>
}