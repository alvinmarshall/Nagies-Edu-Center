package com.cheise_proj.data.source

import com.cheise_proj.data.model.user.UserData
import io.reactivex.Observable

interface RemoteSource {
    fun authenticateUser(role: String, username: String, password: String): Observable<UserData>
}