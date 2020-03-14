package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.repository.FilesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
class GetReportTaskTest {
    private lateinit var getReportTask: GetReportTask
    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getReportTask =
            GetReportTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all reports success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getReports()).thenReturn(Observable.just(actual))
        getReportTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(1)).getReports()
        Mockito.verify(filesRepository, times(0)).getReport(IDENTIFIER)
    }

    @Test
    fun `Get report success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getReport(IDENTIFIER)).thenReturn(Observable.just(actual))
        getReportTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(0)).getReports()
        Mockito.verify(filesRepository, times(1)).getReport(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
    }
}