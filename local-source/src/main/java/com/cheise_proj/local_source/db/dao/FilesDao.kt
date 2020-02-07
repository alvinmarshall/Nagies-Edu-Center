package com.cheise_proj.local_source.db.dao

import androidx.room.*
import com.cheise_proj.local_source.model.files.AssignmentLocal
import com.cheise_proj.local_source.model.files.CircularLocal
import com.cheise_proj.local_source.model.files.ReportLocal
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilesDao {

    //region REPORT
    //region CIRCULAR
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
        deleteCircular()
        saveReport(reportLocalList)
    }
    //endregion
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