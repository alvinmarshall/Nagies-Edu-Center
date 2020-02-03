package com.cheise_proj.remote_source.api

import com.cheise_proj.remote_source.model.dto.message.MessagesDto
import com.cheise_proj.remote_source.model.dto.user.ProfileDto
import com.cheise_proj.remote_source.model.dto.user.UserDto
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("users")
    fun getAuthenticateUser(
        @Query("role") role: String,
        @Body credentials: LoginRequest
    ): Single<UserDto>

    @GET("users/profile")
    fun getProfile(): Single<ProfileDto>

    @GET("message")
    fun getMessages(): Observable<MessagesDto>
}