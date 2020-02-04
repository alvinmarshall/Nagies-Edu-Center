package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.files.FilesEntity
import io.reactivex.Observable

interface FilesRepository {
    fun getCirculars(): Observable<List<FilesEntity>>
    fun getCircular(identifier: String): Observable<List<FilesEntity>>
}