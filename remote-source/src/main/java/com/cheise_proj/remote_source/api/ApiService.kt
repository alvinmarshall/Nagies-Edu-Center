package com.cheise_proj.remote_source.api

import com.cheise_proj.remote_source.model.dto.files.AssignmentsDto
import com.cheise_proj.remote_source.model.dto.files.CircularsDto
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

    //region FILES
    @GET("file/path")
    fun getAssignment(
        @Query("type") type: String = "assignment",
        @Query("format") format: String = "image"
    ): Observable<AssignmentsDto>


    @GET("file/path")
    fun getCircular(
        @Query("type") type: String = "circular",
        @Query("format") format: String = "image"
    ): Observable<CircularsDto>
    //endregion

    //region MESSAGES
    @GET("message")
    fun getMessages(): Observable<MessagesDto>
    //endregion

    //region USERS
    @POST("users")
    fun getAuthenticateUser(
        @Query("role") role: String,
        @Body credentials: LoginRequest
    ): Single<UserDto>

    @GET("users/profile")
    fun getProfile(): Single<ProfileDto>
    //endregion
}