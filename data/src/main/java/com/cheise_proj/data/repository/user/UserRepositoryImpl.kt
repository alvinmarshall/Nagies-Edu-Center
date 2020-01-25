package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.UserDataEntityMapper
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val userDataEntityMapper: UserDataEntityMapper
) : UserRepository {
    override fun authenticateUser(
        username: String,
        password: String,
        role: String
    ): Observable<UserEntity> {
        val remote = remoteSource.authenticateUser(role, username, password).map {
            it.password = password
            it.username = username
            localSource.saveUser(it)
            userDataEntityMapper.dataToEntity(it)
        }
        return localSource.getUser(username, password).map {
            userDataEntityMapper.dataToEntity(it)
        }.toObservable()
            .onErrorResumeNext(
                Function {
                    remote
                }
            )
    }

}