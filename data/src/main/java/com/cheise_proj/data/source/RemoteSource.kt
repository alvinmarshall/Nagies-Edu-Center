package com.cheise_proj.data.source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import io.reactivex.Observable

interface RemoteSource {

    //region FILES

    fun getTimeTable(): Observable<List<FilesData>>

    fun getReport(): Observable<List<FilesData>>

    fun getAssignment(): Observable<List<FilesData>>

    fun getCircular(): Observable<List<FilesData>>

    //endregion

    //region MESSAGE
    fun getMessages(): Observable<List<MessageData>>

    //endregion

    //region USER
    fun authenticateUser(role: String, username: String, password: String): Observable<UserData>

    fun getProfile(): Observable<ProfileData>
    //endregion
}