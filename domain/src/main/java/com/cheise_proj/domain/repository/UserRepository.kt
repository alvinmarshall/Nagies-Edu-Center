package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity
import io.reactivex.Observable

interface UserRepository {
    fun authenticateUser(username: String, password: String, role: String): Observable<UserEntity>
    fun getStudentProfile(identifier:String):Observable<ProfileEntity>
    fun getTeacherProfile(identifier: String):Observable<ProfileEntity>
}