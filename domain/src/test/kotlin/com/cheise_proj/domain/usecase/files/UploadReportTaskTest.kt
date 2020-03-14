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
import org.mockito.MockitoAnnotations
import utils.TestFilesGenerator

@RunWith(JUnit4::class)
class UploadReportTaskTest {

    companion object {
        private const val FILE_PATH = "test file path"
        private const val REF_NO = "test refNo"
        private const val FULL_NAME = "test fullName"
    }

    private lateinit var uploadReportTask: UploadReportTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        uploadReportTask =
            UploadReportTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Upload report success`() {
        val actual = 200
        val part = TestFilesGenerator.getFilePart()
        Mockito.`when`(filesRepository.uploadReport(part,part,part)).thenReturn(
            Observable.just(actual)
        )

        uploadReportTask.buildUseCase(uploadReportTask.Params(part,part,part))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

    }

    @Test(expected = IllegalArgumentException::class)
    fun `Upload report with no params throws an exception`() {
        uploadReportTask.buildUseCase()
    }
}