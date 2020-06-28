package com.cheise_proj.data.source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import io.reactivex.Observable
import okhttp3.MultipartBody

interface RemoteSource {


    //region DELETE FILES
    fun deleteReport(identifier: String, url: String): Observable<Boolean>

    fun deleteAssignment(identifier: String, url: String): Observable<Boolean>
    //endregion


    //region PEOPLE
    fun getPeople(type: String): Observable<List<PeopleData>>
    //endregion

    //region FILES
    fun uploadVideo(
        file: MultipartBody.Part
    ): Observable<Int>

    fun uploadReport(
        file: MultipartBody.Part,
        refNo: MultipartBody.Part,
        fullName: MultipartBody.Part
    ): Observable<Int>

    fun uploadAssignment(file: MultipartBody.Part): Observable<Int>

    fun uploadReceipt(file: MultipartBody.Part): Observable<Int>

    fun getVideo(): Observable<List<FilesData>>

    fun getBill(): Observable<List<FilesData>>

    fun getTimeTable(): Observable<List<FilesData>>

    fun getReport(): Observable<List<FilesData>>

    fun getAssignment(): Observable<List<FilesData>>

    fun getCircular(): Observable<List<FilesData>>

    //endregion

    //region MESSAGE
    fun sendComplaint(content: String, identifier: String?): Observable<Boolean>

    fun sendMessage(
        content: String,
        receiverName: String?,
        identifier: String?
    ): Observable<Boolean>

    fun getSentComplaint(): Observable<List<ComplaintData>>
    fun getComplaint(): Observable<List<ComplaintData>>

    fun getSentMessages(): Observable<List<MessageData>>

    fun getMessages(): Observable<List<MessageData>>

    //endregion

    //region USER
    fun authenticateUser(role: String, username: String, password: String): Observable<UserData>

    fun getProfile(): Observable<ProfileData>

    fun changePassword(oldPassword: String, newPassword: String): Observable<Boolean>
    //endregion
}