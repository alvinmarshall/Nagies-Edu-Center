package com.cheise_proj.local_source

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.UserLocalDataMapper
import io.reactivex.Single
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userLocalDataMapper: UserLocalDataMapper,
    private val profileLocalDataMapper: ProfileLocalDataMapper
) : LocalSource {
    override fun saveUser(userData: UserData) {
        with(userLocalDataMapper.dataToLocal(userData)) {
            userDao.saveUser(this)
        }
    }


    override fun getUser(username: String, password: String): Single<UserData> {
        return userDao.getUser(username, password).map { userLocalDataMapper.localToData(it) }
    }

    override fun saveProfile(profileData: ProfileData) {
        userDao.saveProfile(profileLocalDataMapper.dataToLocal(profileData))
    }

    override fun getProfile(identifier: String): Single<ProfileData> {
        return userDao.getProfile(identifier).map { profileLocalDataMapper.localToData(it) }
    }
}