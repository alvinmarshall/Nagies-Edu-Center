package com.cheise_proj.remote_source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.extensions.asDTOList
import com.cheise_proj.remote_source.extensions.asData
import com.cheise_proj.remote_source.extensions.asDataList
import com.cheise_proj.remote_source.model.dto.files.*
import com.cheise_proj.remote_source.model.dto.message.ComplaintsDto
import com.cheise_proj.remote_source.model.dto.message.MessagesDto
import com.cheise_proj.remote_source.model.dto.message.SentMessageDto
import com.cheise_proj.remote_source.model.dto.people.PeopleDto
import com.cheise_proj.remote_source.model.request.ChangePasswordRequest
import com.cheise_proj.remote_source.model.request.ComplaintRequest
import com.cheise_proj.remote_source.model.request.LoginRequest
import com.cheise_proj.remote_source.model.request.MessageRequest
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.util.*
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val apiService: ApiService

) : RemoteSource {

    companion object {
        private const val NO_CONNECTIVITY = "No internet connection"
        private const val INVALID_CREDENTIALS = "username or password invalid"
        private const val HTTP_OK = 200
    }


    //region DELETE FILES
    //region REPORT
    override fun deleteReport(identifier: String, url: String): Observable<Boolean> {
        return apiService.deleteReport(identifier, url).map { t: DeleteDto ->
            println("remote message: ${t.message}")
            if (t.status == HTTP_OK) return@map true
            return@map false
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    //region ASSIGNMENT
    override fun deleteAssignment(identifier: String, url: String): Observable<Boolean> {
        return apiService.deleteAssignment(identifier, url).map { t: DeleteDto ->
            println("remote message: ${t.message}")
            if (t.status == HTTP_OK) return@map true
            return@map false
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion
    //endregion

    override fun getPeople(type: String): Observable<List<PeopleData>> {
        return when (type) {
            TYPE_TEACHER -> {
                apiService.getClassTeacher()
                    .map { t: PeopleDto ->
                        t.teacher!!.asDataList()
                    }
            }
            else -> {
                apiService.getClassStudent()
                    .map { t: PeopleDto ->
                        t.student.asDataList()
                    }
            }

        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )

    }


    //region FILES
    override fun uploadVideo(file: MultipartBody.Part): Observable<Int> {
        return apiService.uploadVideo(file)
            .map { t: UploadDto ->
                return@map t.status
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    override fun uploadReport(
        file: MultipartBody.Part,
        refNo: MultipartBody.Part,
        fullName: MultipartBody.Part
    ): Observable<Int> {
        return apiService.uploadReport(file, refNo, fullName)
            .map { t: UploadDto ->
                return@map t.status
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )

    }

    override fun uploadAssignment(file: MultipartBody.Part): Observable<Int> {
        return apiService.uploadAssignment(file)
            .map { t: UploadDto ->
                return@map t.status
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    //region RECEIPT
    override fun uploadReceipt(file: MultipartBody.Part): Observable<Int> {
        return apiService.uploadReceipt(file)
            .map { t: UploadDto ->
                return@map t.status
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    //endregion

    //region VIDEO
    override fun getVideo(): Observable<List<FilesData>> {
        return apiService.getVideos().map { t: VideossDto ->
            t.data.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    //region BILL
    override fun getBill(): Observable<List<FilesData>> {
        return apiService.getBilling().map { t: BillsDto ->
            t.data.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    //region TIMETABLE
    override fun getTimeTable(): Observable<List<FilesData>> {
        return apiService.getTimeTable().map { t: TimeTablesDto ->
           t.data.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    //region REPORT
    override fun getReport(): Observable<List<FilesData>> {
        return apiService.getReport().map { t: ReportsDto ->
            t.data.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    override fun getAssignment(): Observable<List<FilesData>> {
        return apiService.getAssignment().map { t: AssignmentsDto ->
                t.data.asDTOList()
            }
            .onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    //region CIRCULAR
    override fun getCircular(): Observable<List<FilesData>> {
        return apiService.getCircular().map { t: CircularsDto ->
            t.data.asDataList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )

    }

    override fun sendComplaint(content: String, identifier: String?): Observable<Boolean> {
        return apiService.sendComplaint(ComplaintRequest(content, identifier))
            .map { t: SentMessageDto ->
                if (t.sentResponse.status == HTTP_OK) return@map true
                return@map false
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    override fun sendMessage(
        content: String,
        receiverName: String?,
        identifier: String?
    ): Observable<Boolean> {
        return apiService.sendMessage(MessageRequest(content, receiverName, identifier))
            .map { t: SentMessageDto ->
                println("SentMessageDto $t")
                if (t.sentResponse.status == HTTP_OK) return@map true
                return@map false
            }.onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }
                }
            )
    }

    override fun getSentComplaint(): Observable<List<ComplaintData>> {
        return apiService.getSentComplaint().map { t: ComplaintsDto ->
            println("dto ${t.complaint}")
            t.complaint.asDataList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion
    //endregion

    //region MESSAGES

    //complaint
    override fun getComplaint(): Observable<List<ComplaintData>> {
        return apiService.getComplaint().map { t: ComplaintsDto ->
            println("dto ${t.complaint}")
            t.complaint.asDataList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }

    override fun getSentMessages(): Observable<List<MessageData>> {
        return apiService.getSentMessages().map { t: MessagesDto ->
            t.message.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )

    }

    //message
    override fun getMessages(): Observable<List<MessageData>> {
        return apiService.getMessages().map { t: MessagesDto ->
            t.message.asDTOList()
        }.onErrorResumeNext(
            Function {
                it.message?.let { msg ->
                    when {
                        msg.contains("Unable to resolve host") -> {
                            Observable.error(Throwable(NO_CONNECTIVITY))
                        }
                        else -> {
                            Observable.error(Throwable(msg))
                        }
                    }
                }
            }
        )
    }
    //endregion

    //region USER
    override fun getProfile(): Observable<ProfileData> {
        return apiService.getProfile()
            .map {
                if (it.student != null) return@map it.student.asData()
                return@map it.teacher.asData()
            }
            .toObservable()
    }

    override fun changePassword(oldPassword: String, newPassword: String): Observable<Boolean> {
        return apiService.changeAccountPassword(
                ChangePasswordRequest(
                    oldPassword,
                    newPassword,
                    newPassword
                )
            )
            .map {
                if (it.status == 200) {
                    return@map true
                }
                return@map false
            }
            .onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            msg.contains("400") -> {
                                Observable.error(Throwable("changing account password failed..."))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }

                }
            )
    }

    override fun authenticateUser(
        role: String,
        username: String,
        password: String
    ): Observable<UserData> {
        return apiService.getAuthenticateUser(
                role.toLowerCase(Locale.ENGLISH),
                LoginRequest(username, password)
            )
            .map {
                return@map it.asData()
            }
            .toObservable()
            .onErrorResumeNext(
                Function {
                    it.message?.let { msg ->
                        when {
                            msg.contains("Unable to resolve host") -> {
                                Observable.error(Throwable(NO_CONNECTIVITY))
                            }
                            msg.contains("HTTP 401") -> {
                                Observable.error(Throwable(INVALID_CREDENTIALS))
                            }
                            else -> {
                                Observable.error(Throwable(msg))
                            }
                        }
                    }

                }
            )

    }
    //endregion

}