package com.cheise_proj.local_source

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.message.MessageLocal
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userLocalDataMapper: UserLocalDataMapper,
    private val profileLocalDataMapper: ProfileLocalDataMapper,
    private val messageDao: MessageDao,
    private val messageLocalDataMapper: MessageLocalDataMapper
) : LocalSource {
    //region MESSAGE
    override fun saveMessages(messageDataList: List<MessageData>) {
        with(messageLocalDataMapper.dataToLocalList(messageDataList)) {
            println("saving Messages")
            messageDao.saveMessages(this)
        }
    }

    override fun getMessages(): Observable<List<MessageData>> {
        return messageDao.getMessages()
            .map { t: List<MessageLocal> -> messageLocalDataMapper.localToDataList(t) }
    }

    override fun getMessage(identifier: Int): Single<MessageData> {
        return messageDao.getMessage(identifier)
            .map { t: MessageLocal -> messageLocalDataMapper.localToData(t) }
    }

    override fun saveUser(userData: UserData) {
        with(userLocalDataMapper.dataToLocal(userData)) {
            userDao.saveUser(this)
        }
    }
    //endregion

    //region USER
    override fun getUser(username: String, password: String): Single<UserData> {
        return userDao.getUser(username, password).map { userLocalDataMapper.localToData(it) }
    }

    override fun saveProfile(profileData: ProfileData) {
        userDao.saveProfile(profileLocalDataMapper.dataToLocal(profileData))
    }

    override fun getProfile(identifier: String): Single<ProfileData> {
        return userDao.getProfile(identifier).map { profileLocalDataMapper.localToData(it) }
    }
    //endregion
}