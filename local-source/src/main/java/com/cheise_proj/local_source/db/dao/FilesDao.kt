package com.cheise_proj.local_source.db.dao

import androidx.room.*
import com.cheise_proj.local_source.model.files.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilesDao {

    //region BILL
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBill(billLocalList: List<BillLocal>)

    @Query("SELECT * FROM bill ORDER BY id DESC")
    fun getBills(): Observable<List<BillLocal>>

    @Query("SELECT * FROM bill WHERE id = :identifier")
    fun getBill(identifier: String): Single<BillLocal>

    @Query("DELETE FROM bill")
    fun deleteBill()

    @Transaction
    fun clearAndInsertBill(billLocalList: List<BillLocal>) {
        deleteBill()
        saveBill(billLocalList)
    }
    //endregion

    //region TIMETABLE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTimeTable(timeTableLocalList: List<TimeTableLocal>)

    @Query("SELECT * FROM timetable ORDER BY id DESC")
    fun getTimeTables(): Observable<List<TimeTableLocal>>

    @Query("SELECT * FROM timetable WHERE id = :identifier")
    fun getTimeTable(identifier: String): Single<TimeTableLocal>

    @Query("DELETE FROM timetable")
    fun deleteTimeTable()

    @Transaction
    fun clearAndInsertTimeTable(timeTableLocalList: List<TimeTableLocal>) {
        deleteTimeTable()
        saveTimeTable(timeTableLocalList)
    }
    //endregion

    //region REPORT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReport(reportLocalList: List<ReportLocal>)

    @Query("SELECT * FROM report ORDER BY id DESC")
    fun getReports(): Observable<List<ReportLocal>>

    @Query("SELECT * FROM report WHERE id = :identifier")
    fun getReport(identifier: String): Single<ReportLocal>

    @Query("DELETE FROM report")
    fun deleteReport()

    @Transaction
    fun clearAndInsertReport(reportLocalList: List<ReportLocal>) {
        deleteReport()
        saveReport(reportLocalList)
    }
    //endregion

    //region ASSIGNMENT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAssignment(assignmentLocal: List<AssignmentLocal>)

    @Query("SELECT * FROM assignment ORDER BY id DESC")
    fun getAssignments(): Observable<List<AssignmentLocal>>

    @Query("SELECT * FROM assignment WHERE id = :identifier")
    fun getAssignment(identifier: String): Single<AssignmentLocal>

    @Query("DELETE FROM assignment")
    fun deleteAssignment()

    @Transaction
    fun clearAndInsertAssignment(assignmentLocal: List<AssignmentLocal>) {
        deleteAssignment()
        saveAssignment(assignmentLocal)
    }
    //endregion


    //region CIRCULAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCircular(circularLocalList: List<CircularLocal>)

    @Query("SELECT * FROM circular ORDER BY id DESC")
    fun getCirculars(): Observable<List<CircularLocal>>

    @Query("SELECT * FROM circular WHERE id = :identifier")
    fun getCircular(identifier: String): Single<CircularLocal>

    @Query("DELETE FROM circular")
    fun deleteCircular()

    @Transaction
    fun clearAndInsertCircular(circularLocalList: List<CircularLocal>) {
        deleteCircular()
        saveCircular(circularLocalList)
    }
    //endregion
}