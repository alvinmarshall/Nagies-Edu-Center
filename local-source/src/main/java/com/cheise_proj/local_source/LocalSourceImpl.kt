package com.cheise_proj.local_source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.files.AssignmentLocalDataMapper
import com.cheise_proj.local_source.mapper.files.CircularLocalDataMapper
import com.cheise_proj.local_source.mapper.files.ReportLocalDataMapper
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.files.AssignmentLocal
import com.cheise_proj.local_source.model.files.CircularLocal
import com.cheise_proj.local_source.model.files.ReportLocal
import com.cheise_proj.local_source.model.message.MessageLocal
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userLocalDataMapper: UserLocalDataMapper,
    private val profileLocalDataMapper: ProfileLocalDataMapper,
    private val messageDao: MessageDao,
    private val messageLocalDataMapper: MessageLocalDataMapper,
    private val filesDao: FilesDao,
    private val circularLocalDataMapper: CircularLocalDataMapper,
    private val assignmentLocalDataMapper: AssignmentLocalDataMapper,
    private val reportLocalDataMapper: ReportLocalDataMapper

) : LocalSource {
    //region REPORT
    override fun getReports(): Observable<List<FilesData>> {
        return filesDao.getReports().map { t: List<ReportLocal> ->
            println("getReports...")
            reportLocalDataMapper.localToDataList(t)
        }
    }

    override fun getReport(identifier: String): Single<FilesData> {
        return filesDao.getReport(identifier).map { t: ReportLocal ->
            println("getReport with identifier: $identifier ...")
            reportLocalDataMapper.localToData(t)
        }
    }

    override fun saveReport(filesDataList: List<FilesData>) {
        println("saveReport...")
        filesDao.clearAndInsertReport(reportLocalDataMapper.dataToLocalList(filesDataList))
    }
    //endregion

    //region ASSIGNMENT
    override fun getAssignments(): Observable<List<FilesData>> {
        return filesDao.getAssignments().map { t: List<AssignmentLocal> ->
            println("getAssignments...")
            assignmentLocalDataMapper.localToDataList(t)
        }
    }

    override fun getAssignment(identifier: String): Single<FilesData> {
        return filesDao.getAssignment(identifier).map { t: AssignmentLocal ->
            println("getAssignment with identifier: $identifier ...")
            assignmentLocalDataMapper.localToData(t)
        }
    }

    override fun saveAssignment(filesDataList: List<FilesData>) {
        println("saveAssignment...")
        filesDao.clearAndInsertAssignment(assignmentLocalDataMapper.dataToLocalList(filesDataList))
    }
    //endregion

    //region CIRCULAR
    override fun getCirculars(): Observable<List<FilesData>> {
        return filesDao.getCirculars().map { t: List<CircularLocal> ->
            println("getCirculars...")
            circularLocalDataMapper.localToDataList(t)
        }
    }

    override fun getCircular(identifier: String): Single<FilesData> {
        return filesDao.getCircular(identifier).map { t: CircularLocal ->
            println("getCircular with identifier: $identifier ...")
            circularLocalDataMapper.localToData(t)
        }
    }

    override fun saveCircular(filesDataList: List<FilesData>) {
        println("saveCircular...")
        filesDao.clearAndInsertCircular(circularLocalDataMapper.dataToLocalList(filesDataList))
    }
    //endregion

    //region MESSAGE
    override fun saveMessages(messageDataList: List<MessageData>) {
        with(messageLocalDataMapper.dataToLocalList(messageDataList)) {
            println("saving Messages")
            messageDao.clearAndInsertMessages(this)
        }
    }

    override fun getMessages(): Observable<List<MessageData>> {
        println("getMessages")
        return messageDao.getMessages()
            .map { t: List<MessageLocal> -> messageLocalDataMapper.localToDataList(t) }
    }

    override fun getMessage(identifier: Int): Single<MessageData> {
        println("getMessage")
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