package com.cheise_proj.local_source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.PeopleDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.files.*
import com.cheise_proj.local_source.mapper.message.ComplaintLocalDataMapper
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.people.PeopleLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.files.*
import com.cheise_proj.local_source.model.message.ComplaintLocal
import com.cheise_proj.local_source.model.message.MessageLocal
import com.cheise_proj.local_source.model.people.PeopleLocal
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
    private val reportLocalDataMapper: ReportLocalDataMapper,
    private val timeTableLocalDataMapper: TimeTableLocalDataMapper,
    private val billLocalDataMapper: BillLocalDataMapper,
    private val peopleDao: PeopleDao,
    private val peopleLocalDataMapper: PeopleLocalDataMapper,
    private val complaintLocalDataMapper: ComplaintLocalDataMapper,
    private val videoLocalDataMapper: VideoLocalDataMapper

) : LocalSource {
    //region PEOPLE
    override fun getPeopleList(): Observable<List<PeopleData>> {
        println("getPeopleList...")
        return peopleDao.getPeopleList().map { t: List<PeopleLocal> ->
            peopleLocalDataMapper.localToDataList(t)
        }
    }

    override fun getPeople(identifier: String): Single<PeopleData> {
        println("getPeople...")
        return peopleDao.getPeople(identifier).map { t: PeopleLocal ->
            peopleLocalDataMapper.localToData(t)
        }
    }

    override fun savePeople(peopleDataList: List<PeopleData>) {
        println("savePeople...")
        peopleDao.clearAndInsertPeople(peopleLocalDataMapper.dataToLocalList(peopleDataList))
    }
    //endregion

    //region FILES

    //region VIDEO

    override fun getVideos(): Observable<List<FilesData>> {
        return filesDao.getVideos().map { t: List<VideoLocal> ->
            println("getVideos...")
            videoLocalDataMapper.localToDataList(t)
        }
    }

    override fun getVideo(identifier: String): Single<FilesData> {
        return filesDao.getVideo(identifier).map { t: VideoLocal ->
            println("getVideo with identifier: $identifier ...")
            videoLocalDataMapper.localToData(t)
        }
    }

    override fun saveVideo(filesDataList: List<FilesData>) {
        println("saveVideos...")
        filesDao.clearAndInsertVideo(videoLocalDataMapper.dataToLocalList(filesDataList))
    }

    //endregion

    //region BILL
    override fun getBills(): Observable<List<FilesData>> {
        return filesDao.getBills().map { t: List<BillLocal> ->
            println("getBills...")
            billLocalDataMapper.localToDataList(t)
        }
    }

    override fun getBill(identifier: String): Single<FilesData> {
        return filesDao.getBill(identifier).map { t: BillLocal ->
            println("getBill with identifier: $identifier ...")
            billLocalDataMapper.localToData(t)
        }
    }

    override fun saveBill(filesDataList: List<FilesData>) {
        println("saveBill...")
        filesDao.clearAndInsertBill(billLocalDataMapper.dataToLocalList(filesDataList))
    }
    //endregion

    //region TIMETABLE
    override fun getTimeTables(): Observable<List<FilesData>> {
        return filesDao.getTimeTables().map { t: List<TimeTableLocal> ->
            println("getTimeTables...")
            timeTableLocalDataMapper.localToDataList(t)
        }
    }

    override fun getTimeTable(identifier: String): Single<FilesData> {
        return filesDao.getTimeTable(identifier).map { t: TimeTableLocal ->
            println("getTimeTable with identifier: $identifier ...")
            timeTableLocalDataMapper.localToData(t)
        }
    }

    override fun saveTimeTable(filesDataList: List<FilesData>) {
        println("saveTimeTable...")
        filesDao.clearAndInsertTimeTable(timeTableLocalDataMapper.dataToLocalList(filesDataList))
    }
    //endregion

    //region REPORT
    override fun deleteReport(identifier: String) {
        println("deleteReport...")
        filesDao.deleteReportByIdentifier(identifier)
    }

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
    override fun deleteAssignment(identifier: String) {
        println("deleteAssignment...")
        filesDao.deleteAssignmentByIdentifier(identifier)
    }

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
    //endregion

    //region MESSAGE

    //complaint
    override fun saveComplaint(complaintDataList: List<ComplaintData>) {
        with(complaintLocalDataMapper.dataToLocalList(complaintDataList)) {
            println("saveComplaint")
            messageDao.clearAndInsertComplaints(this)
        }
    }

    override fun getComplaints(): Observable<List<ComplaintData>> {
        println("getComplaints")
        return messageDao.getComplaints()
            .map { t: List<ComplaintLocal> -> complaintLocalDataMapper.localToDataList(t) }
    }

    override fun getComplaint(identifier: String): Single<ComplaintData> {
        println("getComplaint")
        return messageDao.getComplaint(identifier)
            .map { t: ComplaintLocal -> complaintLocalDataMapper.localToData(t) }
    }

    //message
    override fun saveMessages(messageDataList: List<MessageData>) {
        with(messageLocalDataMapper.dataToLocalList(messageDataList)) {
            println("saving Messages...")
            messageDao.clearAndInsertMessages(this)
        }
    }

    override fun getMessages(): Observable<List<MessageData>> {
        println("getMessages...")
        return messageDao.getMessages()
            .map { t: List<MessageLocal> -> messageLocalDataMapper.localToDataList(t) }
    }

    override fun getMessage(identifier: Int): Single<MessageData> {
        println("getMessage...")
        return messageDao.getMessage(identifier)
            .map { t: MessageLocal -> messageLocalDataMapper.localToData(t) }
    }

    override fun saveUser(userData: UserData) {
        println("saveUser...")
        with(userLocalDataMapper.dataToLocal(userData)) {
            userDao.clearAndInsertUser(this)
        }
    }
    //endregion

    //region USER
    override fun getUser(username: String, password: String): Single<UserData> {
        println("getUser...")
        return userDao.getUser(username, password).map { userLocalDataMapper.localToData(it) }
    }

    override fun saveProfile(profileData: ProfileData) {
        println("saveProfile...")
        userDao.saveProfile(profileLocalDataMapper.dataToLocal(profileData))
    }

    override fun getProfile(identifier: String): Single<ProfileData> {
        println("getProfile...")
        return userDao.getProfile(identifier).map { profileLocalDataMapper.localToData(it) }
    }

    override fun updatePassword(identifier: String, newPassword: String) {
        println("updatePassword...")
        userDao.updatePassword(identifier, newPassword)
    }
    //endregion
}