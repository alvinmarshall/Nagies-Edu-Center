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
class GetVideoTaskTest {

    private lateinit var getVideoTask: GetVideoTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getVideoTask =
            GetVideoTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }
    @Test
    fun `Get all video files success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getVideos()).thenReturn(Observable.just(actual))
        getVideoTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get video with identifier success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getVideo(IDENTIFIER)).thenReturn(Observable.just(actual))
        getVideoTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    companion object {
       private const val IDENTIFIER: String = "1"
    }

}