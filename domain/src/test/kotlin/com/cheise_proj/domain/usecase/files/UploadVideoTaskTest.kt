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
import utils.TestFilesGenerator

@RunWith(JUnit4::class)
class UploadVideoTaskTest {

    private lateinit var uploadVideoTask: UploadVideoTask

    @Mock
    lateinit var filesRepository: FilesRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        uploadVideoTask =
            UploadVideoTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Upload video success`() {
        val actual = 200
        val part = TestFilesGenerator.getFilePart()
        Mockito.`when`(filesRepository.uploadVideos(part)).thenReturn(
            Observable.just(actual)
        )

        uploadVideoTask.buildUseCase(uploadVideoTask.Params(part))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

    }

    @Test(expected = IllegalArgumentException::class)
    fun `Upload video with no params throws an exception`() {
        uploadVideoTask.buildUseCase()
    }
}