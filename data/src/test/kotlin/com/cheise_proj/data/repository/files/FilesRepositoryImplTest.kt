package com.cheise_proj.data.repository.files

import com.cheise_proj.data.mapper.files.FilesDataEntityMapper
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import utils.TestFilesGenerator

@RunWith(JUnit4::class)
class FilesRepositoryImplTest {

    companion object {
        private const val IDENTIFIER = "1"
        private const val ERROR_MESSAGE = "Unable to ping server"
        private const val HTTP_OK = 200
        private const val IS_SUCCESS = true
        private const val URL = "test url"
    }

    private lateinit var filesRepositoryImpl: FilesRepositoryImpl
    private lateinit var filesDataEntityMapper: FilesDataEntityMapper
    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        filesDataEntityMapper = FilesDataEntityMapper()
        filesRepositoryImpl = FilesRepositoryImpl(remoteSource, localSource, filesDataEntityMapper)
    }

    //region DELETE FILES

    //region REPORT
    @Test
    fun `Delete report remote and local success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(remoteSource.deleteReport(IDENTIFIER, URL))
            .thenReturn(Observable.just(actual))
        filesRepositoryImpl.deleteReport(IDENTIFIER, URL)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println("isReport deleted ? $it")
                it == actual
            }
            .assertComplete()
        Mockito.verify(localSource, times(1)).deleteReport(IDENTIFIER)
    }

    @Test
    fun `Delete report remote with error`() {
        val actual = ERROR_MESSAGE
        Mockito.`when`(remoteSource.deleteReport(IDENTIFIER, URL))
            .thenReturn(Observable.error(Throwable(actual)))
        filesRepositoryImpl.deleteReport(IDENTIFIER, URL)
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(localSource, times(0)).deleteReport(IDENTIFIER)
    }

    //endregion

    //region ASSIGNMENT
    @Test
    fun `Delete assignment remote and local success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(remoteSource.deleteAssignment(IDENTIFIER, URL))
            .thenReturn(Observable.just(actual))
        filesRepositoryImpl.deleteAssignment(IDENTIFIER, URL)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println("isAssignment deleted ? $it")
                it == actual
            }
            .assertComplete()
        Mockito.verify(localSource, times(1)).deleteAssignment(IDENTIFIER)
    }

    @Test
    fun `Delete assignment remote with error`() {
        val actual = ERROR_MESSAGE
        Mockito.`when`(remoteSource.deleteAssignment(IDENTIFIER, URL))
            .thenReturn(Observable.error(Throwable(actual)))
        filesRepositoryImpl.deleteAssignment(IDENTIFIER, URL)
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(localSource, times(0)).deleteAssignment(IDENTIFIER)
    }

    //endregion
    //endregion

    //region REPORT
    @Test
    fun `Upload report to remote success`() {
        val part = TestFilesGenerator.getFilePart()
        val actual = HTTP_OK
        Mockito.`when`(remoteSource.uploadReport(part, part, part))
            .thenReturn(Observable.just(actual))
        filesRepositoryImpl.uploadReport(part, part, part)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }
    //endregion


    //region ASSIGNMENT
    @Test
    fun `Upload assignment to remote success`() {
        val filePart = TestFilesGenerator.getFilePart()
        val actual = HTTP_OK
        Mockito.`when`(remoteSource.uploadAssignment(filePart)).thenReturn(Observable.just(actual))
        filesRepositoryImpl.uploadAssignment(filePart)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }
    //endregion

    //region RECEIPT
    @Test
    fun `Upload receipt to remote success`() {
        val filePart = TestFilesGenerator.getFilePart()
        val actual = HTTP_OK
        Mockito.`when`(remoteSource.uploadReceipt(filePart)).thenReturn(Observable.just(actual))
        filesRepositoryImpl.uploadReceipt(filePart)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }
    //endregion

    //region VIDEO
    @Test
    fun `Upload video to remote success`() {
        val filePart = TestFilesGenerator.getFilePart()
        val actual = HTTP_OK
        Mockito.`when`(remoteSource.uploadVideo(filePart)).thenReturn(Observable.just(actual))
        filesRepositoryImpl.uploadVideos(filePart)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }
    //endregion


    //region VIDEO
    @Test
    fun `Get all videos success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getVideo()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getVideos()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getVideos()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getVideo()
        Mockito.verify(localSource, times(1)).getVideos()
        Mockito.verify(localSource, times(0)).getVideo(IDENTIFIER)

    }

    @Test
    fun `Get all videos from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getVideo()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getVideos()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getVideos()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getVideo()
        Mockito.verify(localSource, times(1)).getVideos()
        Mockito.verify(localSource, times(0)).getVideo(IDENTIFIER)

    }

    @Test
    fun `Get video with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getVideo(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getVideo(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getVideos()
        Mockito.verify(localSource, times(1)).getVideo(IDENTIFIER)
    }
    //endregion




    //region BILL
    @Test
    fun `Get all bills success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getBill()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getBills()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getBills()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getBill()
        Mockito.verify(localSource, times(1)).getBills()
        Mockito.verify(localSource, times(0)).getBill(IDENTIFIER)

    }

    @Test
    fun `Get all bills from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getBill()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getBills()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getBills()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getBill()
        Mockito.verify(localSource, times(1)).getBills()
        Mockito.verify(localSource, times(0)).getBill(IDENTIFIER)

    }

    @Test
    fun `Get bill with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getBill(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getBill(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getBills()
        Mockito.verify(localSource, times(1)).getBill(IDENTIFIER)
    }
    //endregion

    //region TIMETABLE
    @Test
    fun `Get all timetables success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getTimeTable()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getTimeTables()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getTimeTables()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getTimeTable()
        Mockito.verify(localSource, times(1)).getTimeTables()
        Mockito.verify(localSource, times(0)).getTimeTable(IDENTIFIER)

    }

    @Test
    fun `Get all timetable from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getTimeTable()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getTimeTables()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getTimeTables()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getTimeTable()
        Mockito.verify(localSource, times(1)).getTimeTables()
        Mockito.verify(localSource, times(0)).getTimeTable(IDENTIFIER)

    }

    @Test
    fun `Get timetable with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getTimeTable(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getTimeTable(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getTimeTables()
        Mockito.verify(localSource, times(1)).getTimeTable(IDENTIFIER)
    }
    //endregion


    //region REPORT
    @Test
    fun `Get all report success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getReport()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getReports()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getReports()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getReport()
        Mockito.verify(localSource, times(1)).getReports()
        Mockito.verify(localSource, times(0)).getReport(IDENTIFIER)

    }

    @Test
    fun `Get all report from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getReport()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getReports()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getReports()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getReport()
        Mockito.verify(localSource, times(1)).getReports()
        Mockito.verify(localSource, times(0)).getReport(IDENTIFIER)

    }

    @Test
    fun `Get report with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getReport(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getReport(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getReports()
        Mockito.verify(localSource, times(1)).getReport(IDENTIFIER)
    }
    //endregion


    //region ASSIGNMENT
    @Test
    fun `Get all assignments from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getAssignment()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getAssignments()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getAssignments()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getAssignment()
        Mockito.verify(localSource, times(1)).getAssignments()
        Mockito.verify(localSource, times(0)).getAssignment(IDENTIFIER)

    }

    @Test
    fun `Get assignment with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getAssignment(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getAssignment(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getAssignments()
        Mockito.verify(localSource, times(1)).getAssignment(IDENTIFIER)
    }

    @Test
    fun `Get all assignment success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getAssignment()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getAssignments()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getAssignments()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getAssignment()
        Mockito.verify(localSource, times(1)).getAssignments()
        Mockito.verify(localSource, times(0)).getAssignment(IDENTIFIER)

    }
    //endregion


    //region CIRCULAR
    @Test
    fun `Get all circulars success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getCircular()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getCirculars()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getCirculars()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getCircular()
        Mockito.verify(localSource, times(1)).getCirculars()
        Mockito.verify(localSource, times(0)).getCircular(IDENTIFIER)

    }

    @Test
    fun `Get all circulars from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getCircular()).thenReturn(
            Observable.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )
        Mockito.`when`(localSource.getCirculars()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getCirculars()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getCircular()
        Mockito.verify(localSource, times(1)).getCirculars()
        Mockito.verify(localSource, times(0)).getCircular(IDENTIFIER)

    }

    @Test
    fun `Get circular with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getCircular(IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getCircular(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getCirculars()
        Mockito.verify(localSource, times(1)).getCircular(IDENTIFIER)
    }
    //endregion
}