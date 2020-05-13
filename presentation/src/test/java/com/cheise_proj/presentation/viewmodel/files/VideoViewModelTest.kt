package com.cheise_proj.presentation.viewmodel.files

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.usecase.files.GetVideoTask
import com.cheise_proj.presentation.extensions.asVideoEntityList
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.utils.TestFilesGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class VideoViewModelTest {

    companion object {
        private const val IDENTIFIER: String = "1"
        private const val ERROR_MESSAGE = "An error occurred"
    }


    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var videoViewModel: VideoViewModel
    private lateinit var getVideoTask: GetVideoTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Mock
    lateinit var serverPath: IServerPath

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getVideoTask =
            GetVideoTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
        videoViewModel = VideoViewModel(getVideoTask, serverPath)
    }


    @Test
    fun `Get all videos success`() {
        val actual = TestFilesGenerator.getVideos()

        Mockito.`when`(filesRepository.getVideos())
            .thenReturn(Observable.just(actual.asVideoEntityList()))
        val videoLive = videoViewModel.getVideos()
        videoLive.observeForever { }
        assertTrue(
            videoLive.value?.status == STATUS.SUCCESS && videoLive.value?.data?.size == actual.size
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getVideos()
    }

    @Test
    fun `Get all videos with errors success`() {
        Mockito.`when`(filesRepository.getVideos())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val videoLive = videoViewModel.getVideos()
        videoLive.observeForever { }
        assertTrue(
            videoLive.value?.status == STATUS.ERROR && videoLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getVideos()
    }

    @Test
    fun `Get video success`() {
        val actual = TestFilesGenerator.getVideo()
        Mockito.`when`(filesRepository.getVideo(IDENTIFIER))
            .thenReturn(
                Observable.just(
                    arrayListOf(actual).asVideoEntityList()
                )
            )
        val videoLive = videoViewModel.getVideo(IDENTIFIER)
        videoLive.observeForever { }
        assertTrue(
            videoLive.value?.status == STATUS.SUCCESS && videoLive.value?.data == actual
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getVideo(IDENTIFIER)
    }

    @Test
    fun `Get video with errors success`() {
        Mockito.`when`(filesRepository.getVideo(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val videoLive = videoViewModel.getVideo(IDENTIFIER)
        videoLive.observeForever { }
        assertTrue(
            videoLive.value?.status == STATUS.ERROR && videoLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getVideo(IDENTIFIER)
    }

}