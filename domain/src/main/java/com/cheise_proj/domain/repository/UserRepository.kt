package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.user.UserEntity
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun authenticateUser(username: String, password: String, role: String): Observable<UserEntity>
}