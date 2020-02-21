package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.files.FilesEntity
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody

interface FilesRepository {

    //region UPLOAD FILES
    fun uploadReport(
        file: MultipartBody.Part,
        refNo: MultipartBody.Part,
        fullName: MultipartBody.Part
    ): Observable<Int>

    fun uploadAssignment(file: MultipartBody.Part): Observable<Int>

    fun uploadReceipt(file: MultipartBody.Part): Observable<Int>
    //endregion

    fun getBills(): Observable<List<FilesEntity>>
    fun getBill(identifier: String): Observable<List<FilesEntity>>

    fun getTimeTables(): Observable<List<FilesEntity>>
    fun getTimeTable(identifier: String): Observable<List<FilesEntity>>

    fun getReports(): Observable<List<FilesEntity>>
    fun getReport(identifier: String): Observable<List<FilesEntity>>

    fun getAssignments(): Observable<List<FilesEntity>>
    fun getAssignment(identifier: String): Observable<List<FilesEntity>>

    fun getCirculars(): Observable<List<FilesEntity>>
    fun getCircular(identifier: String): Observable<List<FilesEntity>>
}