package com.cheise_proj.data.source

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import io.reactivex.Observable
import io.reactivex.Single

interface LocalSource {

    //region DELETE FILES
    fun deleteReport(identifier: String)

    fun deleteAssignment(identifier: String)
    //endregion

    //region PEOPLE
    fun getPeopleList(): Observable<List<PeopleData>>

    fun getPeople(identifier: String): Single<PeopleData>

    fun savePeople(peopleDataList: List<PeopleData>)
    //endregion

    //region FILES

    //region REPORT
    fun getBills(): Observable<List<FilesData>>

    fun getBill(identifier: String): Single<FilesData>

    fun saveBill(filesDataList: List<FilesData>)
    //endregion

    //region REPORT
    fun getTimeTables(): Observable<List<FilesData>>

    fun getTimeTable(identifier: String): Single<FilesData>

    fun saveTimeTable(filesDataList: List<FilesData>)
    //endregion

    //region REPORT
    fun getReports(): Observable<List<FilesData>>

    fun getReport(identifier: String): Single<FilesData>

    fun saveReport(filesDataList: List<FilesData>)
    //endregion


    //region ASSIGNMENT
    fun getAssignments(): Observable<List<FilesData>>

    fun getAssignment(identifier: String): Single<FilesData>

    fun saveAssignment(filesDataList: List<FilesData>)
    //endregion

    //region CIRCULAR
    fun getCirculars(): Observable<List<FilesData>>

    fun getCircular(identifier: String): Single<FilesData>

    fun saveCircular(filesDataList: List<FilesData>)
    //endregion
    //endregion

    //region MESSAGE

    //complaint
    fun saveComplaint(complaintDataList: List<ComplaintData>)

    fun getComplaints(): Observable<List<ComplaintData>>

    fun getComplaint(identifier: String): Single<ComplaintData>

    //message
    fun saveMessages(messageDataList: List<MessageData>)

    fun getMessages(): Observable<List<MessageData>>

    fun getMessage(identifier: Int): Single<MessageData>
    //endregion

    //region USER
    fun saveUser(userData: UserData)

    fun getUser(username: String, password: String): Single<UserData>

    fun saveProfile(profileData: ProfileData)

    fun getProfile(identifier: String): Single<ProfileData>

    fun updatePassword(identifier: String, newPassword: String)
    //endregion
}