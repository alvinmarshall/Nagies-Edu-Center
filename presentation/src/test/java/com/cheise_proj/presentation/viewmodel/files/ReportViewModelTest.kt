package com.cheise_proj.presentation.viewmodel.files

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.usecase.files.GetReportTask
import com.cheise_proj.presentation.mapper.files.ReportEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.utils.TestFilesGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ReportViewModelTest {

    private lateinit var getReportTask: GetReportTask
    private lateinit var reportViewModel: ReportViewModel
    private lateinit var reportEntityMapper: ReportEntityMapper

    @Mock
    lateinit var filesRepository: FilesRepository

    @Mock
    lateinit var path: IServerPath

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getReportTask = GetReportTask(filesRepository,Schedulers.trampoline(),Schedulers.trampoline())
        reportEntityMapper = ReportEntityMapper()
        reportViewModel = ReportViewModel(getReportTask,reportEntityMapper,path)
    }

    @Test
    fun `Get all reports success`() {
        val actual = TestFilesGenerator.getReports()

        Mockito.`when`(filesRepository.getReports())
            .thenReturn(Observable.just(reportEntityMapper.presentationToEntityList(actual)))
        val reportLive = reportViewModel.getReports()
        reportLive.observeForever { }
        assertTrue(
            reportLive.value?.status == STATUS.SUCCESS && reportLive.value?.data?.size == actual.size
        )
        Mockito.verify(filesRepository, times(1)).getReports()
    }

    @Test
    fun `Get all reports with errors success`() {
        Mockito.`when`(filesRepository.getReports())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val reportLive = reportViewModel.getReports()
        reportLive.observeForever { }
        assertTrue(
            reportLive.value?.status == STATUS.ERROR && reportLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, times(1)).getReports()
    }

    @Test
    fun `Get report success`() {
        val actual = TestFilesGenerator.getReport()
        Mockito.`when`(filesRepository.getReport(IDENTIFIER))
            .thenReturn(
                Observable.just(
                    reportEntityMapper.presentationToEntityList(
                        arrayListOf(
                            actual
                        )
                    )
                )
            )
        val reportLive = reportViewModel.getReport(IDENTIFIER)
        reportLive.observeForever { }
        assertTrue(
            reportLive.value?.status == STATUS.SUCCESS && reportLive.value?.data == actual
        )
        Mockito.verify(filesRepository, times(1)).getReport(IDENTIFIER)
    }

    @Test
    fun `Get report with errors success`() {
        Mockito.`when`(filesRepository.getReport(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val reportLive = reportViewModel.getReport(IDENTIFIER)
        reportLive.observeForever { }
        assertTrue(
            reportLive.value?.status == STATUS.ERROR && reportLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, times(1)).getReport(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
        private const val ERROR_MESSAGE = "An error occurred"
    }
}