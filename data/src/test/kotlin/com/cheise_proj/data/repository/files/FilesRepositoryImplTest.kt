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
            .assertValue {
                println(it)
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
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

    companion object {
        private const val IDENTIFIER = "1"
        private const val ERROR_MESSAGE = "Unable to ping server"
    }
}