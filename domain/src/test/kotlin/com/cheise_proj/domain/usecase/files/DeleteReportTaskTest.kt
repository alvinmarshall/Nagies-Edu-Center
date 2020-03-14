package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.repository.FilesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DeleteReportTaskTest {
    companion object {
        private const val IDENTIFIER = "1"
        private const val URL = "test url"
    }

    private lateinit var deleteReportTask: DeleteReportTask


    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteReportTask =
            DeleteReportTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Delete report success`() {
        val actual = true
        Mockito.`when`(filesRepository.deleteReport(IDENTIFIER, URL))
            .thenReturn(Observable.just(actual))
        deleteReportTask.buildUseCase(deleteReportTask.Params(IDENTIFIER, URL))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Delete report no params throws an exception`() {
        deleteReportTask.buildUseCase()
    }
}