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
class UploadReceiptTaskTest {

    private lateinit var uploadReceiptTask: UploadReceiptTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        uploadReceiptTask =
            UploadReceiptTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Upload file success`() {
        val filePart = TestFilesGenerator.getFilePart()
        val actual = 200
        Mockito.`when`(filesRepository.uploadReceipt(filePart)).thenReturn(Observable.just(actual))
        uploadReceiptTask.buildUseCase(UploadReceiptTask.Params(filePart))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Upload file no params throws an exception`() {
        uploadReceiptTask.buildUseCase()
    }
}