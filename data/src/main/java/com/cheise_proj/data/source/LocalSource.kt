package com.cheise_proj.data.source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalSource {

    //region FILES

    //region REPORT
    fun getReports(): Observable<List<FilesData>>

    fun getReport(identifier: String): Single<FilesData>

    fun saveReport(filesDataList: List<FilesData>)
    //endregion


    //region ASSIGNMENT
    fun getAssignments(): Observable<List<FilesData>>

    fun getAssignment(identifier: String): Single<FilesData>

    fun saveAssignment(filesDataList: List<FilesData>)
    //endregion

    //region CIRCULAR
    fun getCirculars(): Observable<List<FilesData>>

    fun getCircular(identifier: String): Single<FilesData>

    fun saveCircular(filesDataList: List<FilesData>)
    //endregion
    //endregion

    //region MESSAGE
    fun saveMessages(messageDataList: List<MessageData>)

    fun getMessages(): Observable<List<MessageData>>

    fun getMessage(identifier: Int): Single<MessageData>
    //endregion

    //region USER
    fun saveUser(userData: UserData)

    fun getUser(username: String, password: String): Single<UserData>

    fun saveProfile(profileData: ProfileData)

    fun getProfile(identifier: String): Single<ProfileData>
    //endregion
}