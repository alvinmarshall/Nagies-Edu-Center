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
class UploadAssignmentTaskTest {
    private lateinit var uploadAssignmentTask: UploadAssignmentTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        uploadAssignmentTask =
            UploadAssignmentTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Upload assignment success`() {
        val file = TestFilesGenerator.getFilePart()
        val actual = 1
        Mockito.`when`(filesRepository.uploadAssignment(file)).thenReturn(
            Observable.just(1))
        uploadAssignmentTask.buildUseCase(uploadAssignmentTask.Params(file))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Upload assignment with no params throws an exception`() {
        uploadAssignmentTask.buildUseCase()
    }
}