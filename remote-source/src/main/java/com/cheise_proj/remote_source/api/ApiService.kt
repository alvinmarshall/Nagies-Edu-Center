package com.cheise_proj.remote_source.api

import com.cheise_proj.remote_source.model.dto.files.*
import com.cheise_proj.remote_source.model.dto.message.ComplaintsDto
import com.cheise_proj.remote_source.model.dto.message.MessagesDto
import com.cheise_proj.remote_source.model.dto.people.PeopleDto
import com.cheise_proj.remote_source.model.dto.user.PasswordDto
import com.cheise_proj.remote_source.model.dto.user.ProfileDto
import com.cheise_proj.remote_source.model.dto.user.UserDto
import com.cheise_proj.remote_source.model.request.ChangePasswordRequest
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    //region PEOPLE
    @GET("students/teacher")
    fun getClassTeacher(): Observable<PeopleDto>

    @GET("teacher/student")
    fun getClassStudent(): Observable<PeopleDto>
    //endregion

    //region FILES

    //region ASSIGNMENT
    @Multipart
    @POST("file/uploads")
    fun uploadAssignment(
        @Part file: MultipartBody.Part,
        @Query("type") type: String = "assignment"
    ): Observable<UploadDto>
    //endregion

    //region RECEIPT
    @Multipart
    @POST("file/uploads")
    fun uploadReceipt(
        @Part file: MultipartBody.Part,
        @Query("type") type: String = "receipt"
    ): Observable<UploadDto>
    //endregion

    //region BILL
    @GET("file/path")
    fun getBilling(
        @Query("type") type: String = "bill",
        @Query("format") format: String = "image"
    ): Observable<BillsDto>
    //endregion

    //region TIMETABLE
    @GET("file/path")
    fun getTimeTable(
        @Query("type") type: String = "timetable",
        @Query("format") format: String = "image"
    ): Observable<TimeTablesDto>

    //endregion
    //region REPORT
    @GET("file/path")
    fun getReport(
        @Query("type") type: String = "report",
        @Query("format") format: String = "image"
    ): Observable<ReportsDto>
    //endregion

    //region ASSIGNMENT
    @GET("file/path")
    fun getAssignment(
        @Query("type") type: String = "assignment",
        @Query("format") format: String = "image"
    ): Observable<AssignmentsDto>
    //endregion

    //region CIRCULAR
    @GET("file/path")
    fun getCircular(
        @Query("type") type: String = "circular",
        @Query("format") format: String = "image"
    ): Observable<CircularsDto>
    //endregion
    //endregion

    //region MESSAGES

    //complaint
    @GET("message")
    fun getComplaint(
        @Query("from") from: String = "complaint"
    ): Observable<ComplaintsDto>

    //message
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

    @POST("users/change_password")
    fun changeAccountPassword(
        @Body changePassRequest: ChangePasswordRequest
    ): Observable<PasswordDto>
    //endregion
}