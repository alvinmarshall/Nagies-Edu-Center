package com.cheise_proj.data.repository.files

import com.cheise_proj.data.cache.*
import com.cheise_proj.data.extensions.asDataList
import com.cheise_proj.data.extensions.asEntity
import com.cheise_proj.data.extensions.asEntityList
import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.repository.FilesRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : FilesRepository {


    //region DELETE FILES
    override fun deleteReport(identifier: String, url: String): Observable<Boolean> {
        return remoteSource.deleteReport(identifier, url)
            .map { isDeleted ->
                if (isDeleted) {
                    ReportCache.removeItem(identifier.toInt())
                    localSource.deleteReport(identifier)
                    return@map isDeleted
                }
                return@map isDeleted
            }
    }

    override fun deleteAssignment(identifier: String, url: String): Observable<Boolean> {
        return remoteSource.deleteAssignment(identifier, url)
            .map { isDeleted ->
                if (isDeleted) {
                    AssignmentCache.removeItem(identifier.toInt())
                    localSource.deleteAssignment(identifier)
                    return@map isDeleted
                }
                return@map isDeleted
            }
    }

    //endregion


    //region UPLOADS

    //region UPLOAD VIDEO
    override fun uploadVideos(file: MultipartBody.Part): Observable<Int> {
        return remoteSource.uploadVideo(file)
    }
    //endregion

    //region UPLOAD REPORT
    override fun uploadReport(
        file: MultipartBody.Part,
        refNo: MultipartBody.Part,
        fullName: MultipartBody.Part
    ): Observable<Int> {
        return remoteSource.uploadReport(file, refNo, fullName)
    }
    //endregion

    //region UPLOAD ASSIGNMENT
    override fun uploadAssignment(file: MultipartBody.Part): Observable<Int> {
        return remoteSource.uploadAssignment(file)
    }
    //endregion

    //region RECEIPT
    override fun uploadReceipt(file: MultipartBody.Part): Observable<Int> {
        return remoteSource.uploadReceipt(file)
    }
    //endregion

    //endregion

    //region VIDEO
    override fun getVideos(): Observable<List<FilesEntity>> {
        val videoObservable: Observable<List<FilesEntity>>
        val cacheVideo = VideoCache.getVideo()

        val local = localSource.getVideos()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getVideo()
            .map { t: List<FilesData> ->
                localSource.saveBill(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        videoObservable = if (cacheVideo != null) {
            println("Remote source NOT invoked")
            val cache = cacheVideo.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return videoObservable
            .map { t: List<FilesEntity> ->
                if (cacheVideo == null) {
                    VideoCache.addVideo(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getVideo(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getVideo(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }

    //endregion

    //region BILL
    override fun getBills(): Observable<List<FilesEntity>> {
        val billObservable: Observable<List<FilesEntity>>
        val cacheBill = BillCache.getBill()

        val local = localSource.getBills()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getBill()
            .map { t: List<FilesData> ->
                localSource.saveBill(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        billObservable = if (cacheBill != null) {
            println("Remote source NOT invoked")
            val cache = cacheBill.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return billObservable
            .map { t: List<FilesEntity> ->
                if (cacheBill == null) {
                    BillCache.addBill(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getBill(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getBill(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region TIMETABLE
    override fun getTimeTables(): Observable<List<FilesEntity>> {
        val timeTableObservable: Observable<List<FilesEntity>>
        val cacheTimeTable = TimeTableCache.getTimeTable()

        val local = localSource.getTimeTables()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getTimeTable()
            .map { t: List<FilesData> ->
                localSource.saveTimeTable(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        timeTableObservable = if (cacheTimeTable != null) {
            println("Remote source NOT invoked")
            val cache = cacheTimeTable.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return timeTableObservable
            .map { t: List<FilesEntity> ->
                if (cacheTimeTable == null) {
                    TimeTableCache.addTimeTable(
                        t.asDataList()
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getTimeTable(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getTimeTable(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region REPORT
    override fun getReports(): Observable<List<FilesEntity>> {
        val reportObservable: Observable<List<FilesEntity>>
        val cacheReport = ReportCache.getReport()

        val local = localSource.getReports()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getReport()
            .map { t: List<FilesData> ->
                localSource.saveReport(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        reportObservable = if (cacheReport != null) {
            println("Remote source NOT invoked")
            val cache = cacheReport.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return reportObservable
            .map { t: List<FilesEntity> ->
                if (cacheReport == null) {
                    ReportCache.addReport(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }


    override fun getReport(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getReport(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }

    //endregion


    //region ASSIGNMENT
    override fun getAssignments(): Observable<List<FilesEntity>> {
        val assignmentObservable: Observable<List<FilesEntity>>
        val cacheAssignment = AssignmentCache.getAssignment()

        val local = localSource.getAssignments()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getAssignment()
            .map { t: List<FilesData> ->
                localSource.saveAssignment(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        assignmentObservable = if (cacheAssignment != null) {
            println("Remote source NOT invoked")
            val cache = cacheAssignment.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return assignmentObservable
            .map { t: List<FilesEntity> ->
                if (cacheAssignment == null) {
                    AssignmentCache.addAssignment(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getAssignment(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getAssignment(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }
    //endregion

    //region CIRCULAR
    override fun getCirculars(): Observable<List<FilesEntity>> {
        val circularObservable: Observable<List<FilesEntity>>
        val cacheCircular = CircularCache.getCirculars()
        val local = localSource.getCirculars()
            .map { t: List<FilesData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getCircular()
            .map { t: List<FilesData> ->
                localSource.saveCircular(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        circularObservable = if (cacheCircular != null) {
            println("Remote source NOT invoked")
            val cache = cacheCircular.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return circularObservable
            .map { t: List<FilesEntity> ->
                if (cacheCircular == null) {
                    CircularCache.addCirculars(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getCircular(identifier: String): Observable<List<FilesEntity>> {
        return localSource.getCircular(identifier).toObservable()
            .map { t: FilesData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }
    //endregion
}