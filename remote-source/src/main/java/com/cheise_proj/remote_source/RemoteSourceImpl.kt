package com.cheise_proj.remote_source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.files.CircularDtoDataMapper
import com.cheise_proj.remote_source.mapper.files.FilesDtoDataMapper
import com.cheise_proj.remote_source.mapper.message.MessageDtoDataMapper
import com.cheise_proj.remote_source.mapper.people.PeopleDtoDataMapper
import com.cheise_proj.remote_source.mapper.user.ProfileDtoDataMapper
import com.cheise_proj.remote_source.mapper.user.UserDtoDataMapper
import com.cheise_proj.remote_source.model.dto.files.*
import com.cheise_proj.remote_source.model.dto.message.MessagesDto
import com.cheise_proj.remote_source.model.dto.people.PeopleDto
import com.cheise_proj.remote_source.model.request.ChangePasswordRequest
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import java.util.*
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDtoDataMapper: UserDtoDataMapper,
    private val profileDtoDataMapper: ProfileDtoDataMapper,
    private val messageDtoDataMapper: MessageDtoDataMapper,
    private val circularDtoDataMapper: CircularDtoDataMapper,
    private val filesDtoDataMapper: FilesDtoDataMapper,
    private val peopleDtoDataMapper: PeopleDtoDataMapper

) : RemoteSource {

    companion object {
        private const val NO_CONNECTIVITY = "No internet connection"
        private const val INVALID_CREDENTIALS = "username or password invalid"
    }

    override fun getPeople(type: String): Observable<List<PeopleData>> {
        return when (type) {
            TYPE_TEACHER -> {
                apiService.getClassTeacher()
                    .map { t: PeopleDto ->
                        peopleDtoDataMapper.dtoToDataList(t.teacher!!)
                    }
            }
            else -> {
                apiService.getClassStudent()
                    .map { t: PeopleDto ->
                        peopleDtoDataMapper.dtoToDataList(t.student)
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

    //region RECEIPT
    override fun uploadReceipt(file: MultipartBody.Part): Observable<Int> {
        return apiService.uploadReceipt(file)
            .map { t: UploadDto ->
                return@map t.status
            }
    }
    //endregion

    //region BILL
    override fun getBill(): Observable<List<FilesData>> {
        return apiService.getBilling().map { t: BillsDto ->
            filesDtoDataMapper.dtoToDataList(t.data)
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
            filesDtoDataMapper.dtoToDataList(t.data)
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
            filesDtoDataMapper.dtoToDataList(t.data)
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
            filesDtoDataMapper.dtoToDataList(t.data)
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
            circularDtoDataMapper.dtoToDataList(t.data)
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
    override fun getMessages(): Observable<List<MessageData>> {
        return apiService.getMessages().map { t: MessagesDto ->
            messageDtoDataMapper.dtoToDataList(t.message)
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
                if (it.student != null) return@map profileDtoDataMapper.dtoToData(it.student)
                return@map profileDtoDataMapper.dtoToData(it.teacher)
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
                return@map userDtoDataMapper.dtoToData(it)
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