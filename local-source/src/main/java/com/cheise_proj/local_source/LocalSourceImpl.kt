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
import com.cheise_proj.local_source.extensions.*
import com.cheise_proj.local_source.model.files.*
import com.cheise_proj.local_source.model.message.ComplaintLocal
import com.cheise_proj.local_source.model.message.MessageLocal
import com.cheise_proj.local_source.model.people.PeopleLocal
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val messageDao: MessageDao,
    private val filesDao: FilesDao,
    private val peopleDao: PeopleDao


) : LocalSource {
    //region PEOPLE
    override fun getPeopleList(): Observable<List<PeopleData>> {
        println("getPeopleList...")
        return peopleDao.getPeopleList().map { t: List<PeopleLocal> ->
            t.asLocalList()
        }
    }

    override fun getPeople(identifier: String): Single<PeopleData> {
        println("getPeople...")
        return peopleDao.getPeople(identifier).map { t: PeopleLocal ->
            t.asData()
        }
    }

    override fun savePeople(peopleDataList: List<PeopleData>) {
        println("savePeople...")
        peopleDao.clearAndInsertPeople(peopleDataList.asDataList())
    }
    //endregion

    //region FILES

    //region VIDEO

    override fun getVideos(): Observable<List<FilesData>> {
        return filesDao.getVideos().map { t: List<VideoLocal> ->
            println("getVideos...")
            t.asVideoLocalList()
        }
    }

    override fun getVideo(identifier: String): Single<FilesData> {
        return filesDao.getVideo(identifier).map { t: VideoLocal ->
            println("getVideo with identifier: $identifier ...")
            t.asVideoData()
        }
    }

    override fun saveVideo(filesDataList: List<FilesData>) {
        println("saveVideos...")
        filesDao.clearAndInsertVideo(filesDataList.asVideoDataList())
    }

    //endregion

    //region BILL
    override fun getBills(): Observable<List<FilesData>> {
        return filesDao.getBills().map { t: List<BillLocal> ->
            println("getBills...")
            t.asBillLocalList()
        }
    }

    override fun getBill(identifier: String): Single<FilesData> {
        return filesDao.getBill(identifier).map { t: BillLocal ->
            println("getBill with identifier: $identifier ...")
            t.asBillData()
        }
    }

    override fun saveBill(filesDataList: List<FilesData>) {
        println("saveBill...")
        filesDao.clearAndInsertBill(filesDataList.asBillDataList())
    }
    //endregion

    //region TIMETABLE
    override fun getTimeTables(): Observable<List<FilesData>> {
        return filesDao.getTimeTables().map { t: List<TimeTableLocal> ->
            println("getTimeTables...")
            t.asTimeTableLocalList()
        }
    }

    override fun getTimeTable(identifier: String): Single<FilesData> {
        return filesDao.getTimeTable(identifier).map { t: TimeTableLocal ->
            println("getTimeTable with identifier: $identifier ...")
            t.asTimeTableData()
        }
    }

    override fun saveTimeTable(filesDataList: List<FilesData>) {
        println("saveTimeTable...")
        filesDao.clearAndInsertTimeTable(filesDataList.asTimeTableDataList())
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
            t.asReportLocalList()
        }
    }

    override fun getReport(identifier: String): Single<FilesData> {
        return filesDao.getReport(identifier).map { t: ReportLocal ->
            println("getReport with identifier: $identifier ...")
            t.asReportData()
        }
    }

    override fun saveReport(filesDataList: List<FilesData>) {
        println("saveReport...")
        filesDao.clearAndInsertReport(filesDataList.asReportDataList())
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
            t.asAssignmentLocalList()
        }
    }

    override fun getAssignment(identifier: String): Single<FilesData> {
        return filesDao.getAssignment(identifier).map { t: AssignmentLocal ->
            println("getAssignment with identifier: $identifier ...")
            t.asAssignmentData()
        }
    }

    override fun saveAssignment(filesDataList: List<FilesData>) {
        println("saveAssignment...")
        filesDao.clearAndInsertAssignment(filesDataList.asAssignmentDataList())
    }
    //endregion

    //region CIRCULAR
    override fun getCirculars(): Observable<List<FilesData>> {
        return filesDao.getCirculars().map { t: List<CircularLocal> ->
            println("getCirculars...")
            t.asCircularLocalList()
        }
    }

    override fun getCircular(identifier: String): Single<FilesData> {
        return filesDao.getCircular(identifier).map { t: CircularLocal ->
            println("getCircular with identifier: $identifier ...")
            t.asCircleData()
        }
    }

    override fun saveCircular(filesDataList: List<FilesData>) {
        println("saveCircular...")
        filesDao.clearAndInsertCircular(filesDataList.asCircleDataList())
    }
    //endregion
    //endregion

    //region MESSAGE

    //complaint
    override fun saveComplaint(complaintDataList: List<ComplaintData>) {
        with(complaintDataList.asLocalList()) {
            println("saveComplaint")
            messageDao.clearAndInsertComplaints(this)
        }
    }

    override fun getComplaints(): Observable<List<ComplaintData>> {
        println("getComplaints")
        return messageDao.getComplaints()
            .map { t: List<ComplaintLocal> -> t.asDataList() }
    }

    override fun getComplaint(identifier: String): Single<ComplaintData> {
        println("getComplaint")
        return messageDao.getComplaint(identifier)
            .map { t: ComplaintLocal -> t.asData() }
    }

    //message
    override fun saveMessages(messageDataList: List<MessageData>) {
        with(messageDataList.asLocalList()) {
            println("saving Messages...")
            messageDao.clearAndInsertMessages(this)
        }
    }

    override fun getMessages(): Observable<List<MessageData>> {
        println("getMessages...")
        return messageDao.getMessages()
            .map { t: List<MessageLocal> -> t.asDataList() }
    }

    override fun getMessage(identifier: Int): Single<MessageData> {
        println("getMessage...")
        return messageDao.getMessage(identifier)
            .map { t: MessageLocal -> t.asData() }
    }

    override fun saveUser(userData: UserData) {
        println("saveUser...")
        with(userData.asLocal()) {
            userDao.clearAndInsertUser(this)
        }
    }
    //endregion

    //region USER
    override fun getUser(username: String, password: String): Single<UserData> {
        println("getUser...")
        return userDao.getUser(username, password).map { t -> t.asData() }
    }

    override fun saveProfile(profileData: ProfileData) {
        println("saveProfile...")
        userDao.saveProfile(profileData.asLocal())
    }

    override fun getProfile(identifier: String): Single<ProfileData> {
        println("getProfile...")
        return userDao.getProfile(identifier).map { p -> p.asData() }
    }

    override fun updatePassword(identifier: String, newPassword: String) {
        println("updatePassword...")
        userDao.updatePassword(identifier, newPassword)
    }
    //endregion
}