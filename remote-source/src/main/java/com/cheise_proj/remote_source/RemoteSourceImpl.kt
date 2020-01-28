package com.cheise_proj.remote_source

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.ProfileDtoDataMapper
import com.cheise_proj.remote_source.mapper.UserDtoDataMapper
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.*
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDtoDataMapper: UserDtoDataMapper,
    private val profileDtoDataMapper: ProfileDtoDataMapper
) : RemoteSource {
    companion object {
        const val NO_CONNECTIVITY = "No internet connection"
        const val INVALID_CREDENTIALS = "username or password invalid"
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
                    println("apiService.getAuthenticateUser error ${it.message}")
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

    override fun getProfile(): Observable<ProfileData> {
        return apiService.getProfile()
            .map {
                println("dto ${it.student} \n ${it.teacher}")
                if (it.student != null) return@map profileDtoDataMapper.dtoToData(it.student)
                return@map profileDtoDataMapper.dtoToData(it.teacher)
            }
            .toObservable()
    }

}