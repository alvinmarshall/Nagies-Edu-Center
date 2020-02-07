package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.files.FilesEntity
import io.reactivex.Observable

interface FilesRepository {

    fun getReports(): Observable<List<FilesEntity>>
    fun getReport(identifier: String): Observable<List<FilesEntity>>

    fun getAssignments(): Observable<List<FilesEntity>>
    fun getAssignment(identifier: String): Observable<List<FilesEntity>>

    fun getCirculars(): Observable<List<FilesEntity>>
    fun getCircular(identifier: String): Observable<List<FilesEntity>>
}