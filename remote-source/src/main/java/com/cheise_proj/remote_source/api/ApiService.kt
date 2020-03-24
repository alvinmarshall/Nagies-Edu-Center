package com.cheise_proj.remote_source.api

import com.cheise_proj.remote_source.model.dto.files.*
import com.cheise_proj.remote_source.model.dto.message.ComplaintsDto
import com.cheise_proj.remote_source.model.dto.message.MessagesDto
import com.cheise_proj.remote_source.model.dto.message.SentMessageDto
import com.cheise_proj.remote_source.model.dto.people.PeopleDto
import com.cheise_proj.remote_source.model.dto.user.PasswordDto
import com.cheise_proj.remote_source.model.dto.user.ProfileDto
import com.cheise_proj.remote_source.model.dto.user.UserDto
import com.cheise_proj.remote_source.model.request.ChangePasswordRequest
import com.cheise_proj.remote_source.model.request.ComplaintRequest
import com.cheise_proj.remote_source.model.request.LoginRequest
import com.cheise_proj.remote_source.model.request.MessageRequest
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    //region SENT COMPLAINT
    @GET("message/sent")
    fun getSentComplaint(): Observable<ComplaintsDto>
    //endregion

    //region SENT MESSAGE
    @GET("message/sent")
    fun getSentMessages(): Observable<MessagesDto>
    //endregion

    //region SEND MESSAGE
    // teacher sending this message to entire class / specific parent
    @POST("message")
    fun sendMessage(
        @Body message: MessageRequest,
        @Query("to") to: String = "parent"
    ): Observable<SentMessageDto>


    @POST("message")
    // parent sending this message to specific class teacher
    fun sendComplaint(
        @Body complaint: ComplaintRequest,
        @Query("to") to: String = "teacher"
    ): Observable<SentMessageDto>
    //endregion

    //region DELETE FILES
    @DELETE("file/{id}")
    fun deleteReport(
        @Path("id") id: String?,
        @Query("path") path: String?,
        @Query("type") type: String = "report",
        @Query("format") format: String = "image"
    ): Observable<DeleteDto>

    @DELETE("file/{id}")
    fun deleteAssignment(
        @Path("id") id: String?,
        @Query("path") path: String?,
        @Query("type") type: String = "assignment",
        @Query("format") format: String = "image"
    ): Observable<DeleteDto>
    //endregion

    //region PEOPLE
    @GET("students/teacher")
    fun getClassTeacher(): Observable<PeopleDto>

    @GET("teacher/student")
    fun getClassStudent(): Observable<PeopleDto>
    //endregion

    //region FILES

    //region VIDEO
    @Multipart
    @POST("file/upload/video")
    fun uploadVideo(
        @Part file: MultipartBody.Part
    ): Observable<UploadDto>
    //endregion

    //region REPORT
    @Multipart
    @POST("file/uploads")
    fun uploadReport(
        @Part file: MultipartBody.Part,
        @Part refNo: MultipartBody.Part,
        @Part fullName: MultipartBody.Part,
        @Query("type") type: String = "report"
    ): Observable<UploadDto>
    //endregion

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

    //region VIDEO
    @GET("file/videos")
    fun getVideos(): Observable<VideossDto>
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