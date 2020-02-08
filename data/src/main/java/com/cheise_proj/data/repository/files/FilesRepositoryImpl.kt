package com.cheise_proj.data.repository.files

import com.cheise_proj.data.cache.*
import com.cheise_proj.data.mapper.files.FilesDataEntityMapper
import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.repository.FilesRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val filesDataEntityMapper: FilesDataEntityMapper
) : FilesRepository {

    //region BILL
    override fun getBills(): Observable<List<FilesEntity>> {
        val billObservable: Observable<List<FilesEntity>>
        val identifier = "bill"
        val cacheBill = BillCache.getBill(identifier)

        val local = localSource.getBills()
            .map { t: List<FilesData> ->
                filesDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getBill()
            .map { t: List<FilesData> ->
                localSource.saveBill(t)
                filesDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        billObservable = if (cacheBill != null) {
            println("Remote source NOT invoked")
            val cache = filesDataEntityMapper.dataToEntityList(cacheBill)
            Observable.just(cache)
        } else {
            remote
        }

        return billObservable
            .map { t: List<FilesEntity> ->
                if (cacheBill == null) {
                    BillCache.addBill(
                        identifier,
                        filesDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getBill(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getBill(identifier).toObservable()
            .map { t: FilesData ->
                val data = filesDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region TIMETABLE
    override fun getTimeTables(): Observable<List<FilesEntity>> {
        val timeTableObservable: Observable<List<FilesEntity>>
        val identifier = "timetable"
        val cacheTimeTable = TimeTableCache.getTimeTable(identifier)

        val local = localSource.getTimeTables()
            .map { t: List<FilesData> ->
                filesDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getTimeTable()
            .map { t: List<FilesData> ->
                localSource.saveTimeTable(t)
                filesDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        timeTableObservable = if (cacheTimeTable != null) {
            println("Remote source NOT invoked")
            val cache = filesDataEntityMapper.dataToEntityList(cacheTimeTable)
            Observable.just(cache)
        } else {
            remote
        }

        return timeTableObservable
            .map { t: List<FilesEntity> ->
                if (cacheTimeTable == null) {
                    TimeTableCache.addTimeTable(
                        identifier,
                        filesDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getTimeTable(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getTimeTable(identifier).toObservable()
            .map { t: FilesData ->
                val data = filesDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region REPORT
    override fun getReports(): Observable<List<FilesEntity>> {
        val reportObservable: Observable<List<FilesEntity>>
        val identifier = "report"
        val cacheReport = ReportCache.getReport(identifier)

        val local = localSource.getReports()
            .map { t: List<FilesData> ->
                filesDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getReport()
            .map { t: List<FilesData> ->
                localSource.saveReport(t)
                filesDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        reportObservable = if (cacheReport != null) {
            println("Remote source NOT invoked")
            val cache = filesDataEntityMapper.dataToEntityList(cacheReport)
            Observable.just(cache)
        } else {
            remote
        }

        return reportObservable
            .map { t: List<FilesEntity> ->
                if (cacheReport == null) {
                    ReportCache.addReport(
                        identifier,
                        filesDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }


    override fun getReport(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getReport(identifier).toObservable()
            .map { t: FilesData ->
                val data = filesDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }

    //endregion


    //region ASSIGNMENT
    override fun getAssignments(): Observable<List<FilesEntity>> {
        val assignmentObservable: Observable<List<FilesEntity>>
        val identifier = "assignment"
        val cacheAssignment = AssignmentCache.getAssignment(identifier)

        val local = localSource.getAssignments()
            .map { t: List<FilesData> ->
                filesDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getAssignment()
            .map { t: List<FilesData> ->
                localSource.saveAssignment(t)
                filesDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        assignmentObservable = if (cacheAssignment != null) {
            println("Remote source NOT invoked")
            val cache = filesDataEntityMapper.dataToEntityList(cacheAssignment)
            Observable.just(cache)
        } else {
            remote
        }

        return assignmentObservable
            .map { t: List<FilesEntity> ->
                if (cacheAssignment == null) {
                    AssignmentCache.addAssignment(
                        identifier,
                        filesDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getAssignment(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getAssignment(identifier).toObservable()
            .map { t: FilesData ->
                val data = filesDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region CIRCULAR
    override fun getCirculars(): Observable<List<FilesEntity>> {
        val circularObservable: Observable<List<FilesEntity>>
        val identifier = "circular"
        val cacheCircular = CircularCache.getCirculars(identifier)
        val local = localSource.getCirculars()
            .map { t: List<FilesData> ->
                filesDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getCircular()
            .map { t: List<FilesData> ->
                localSource.saveCircular(t)
                filesDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        circularObservable = if (cacheCircular != null) {
            println("Remote source NOT invoked")
            val cache = filesDataEntityMapper.dataToEntityList(cacheCircular)
            Observable.just(cache)
        } else {
            remote
        }

        return circularObservable
            .map { t: List<FilesEntity> ->
                if (cacheCircular == null) {
                    CircularCache.addCirculars(
                        identifier,
                        filesDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getCircular(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getCircular(identifier).toObservable()
            .map { t: FilesData ->
                val data = filesDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }
    //endregion
}