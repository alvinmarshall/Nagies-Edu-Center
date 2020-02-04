package com.cheise_proj.presentation.viewmodel.files

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.usecase.files.GetCircularTask
import com.cheise_proj.presentation.mapper.files.CircularEntityMapper
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
class CircularViewModelTest {
    private lateinit var getCircularTask: GetCircularTask
    private lateinit var circularViewModel: CircularViewModel
    private lateinit var circularEntityMapper: CircularEntityMapper


    @Mock
    lateinit var filesRepository: FilesRepository

    @Mock
    lateinit var path: IServerPath

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        circularEntityMapper = CircularEntityMapper()
        getCircularTask =
            GetCircularTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())

        circularViewModel = CircularViewModel(getCircularTask, circularEntityMapper, path)
    }

    @Test
    fun `Get all circular success`() {
        val actual = TestFilesGenerator.getCirculars()

        Mockito.`when`(filesRepository.getCirculars())
            .thenReturn(Observable.just(circularEntityMapper.presentationToEntityList(actual)))
        val circularLive = circularViewModel.getCirculars()
        circularLive.observeForever { }
        assertTrue(
            circularLive.value?.status == STATUS.SUCCESS && circularLive.value?.data?.size == actual.size
        )
        Mockito.verify(filesRepository, times(1)).getCirculars()
    }

    @Test
    fun `Get all circular with errors success`() {
        Mockito.`when`(filesRepository.getCirculars())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val circularLive = circularViewModel.getCirculars()
        circularLive.observeForever { }
        assertTrue(
            circularLive.value?.status == STATUS.ERROR && circularLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, times(1)).getCirculars()
    }

    @Test
    fun `Get circular success`() {
        val actual = TestFilesGenerator.getCircular()
        Mockito.`when`(filesRepository.getCircular(IDENTIFIER))
            .thenReturn(
                Observable.just(
                    circularEntityMapper.presentationToEntityList(
                        arrayListOf(
                            actual
                        )
                    )
                )
            )
        val circularLive = circularViewModel.getCircular(IDENTIFIER)
        circularLive.observeForever { }
        assertTrue(
            circularLive.value?.status == STATUS.SUCCESS && circularLive.value?.data == actual
        )
        Mockito.verify(filesRepository, times(1)).getCircular(IDENTIFIER)
    }

    @Test
    fun `Get circular with errors success`() {
        Mockito.`when`(filesRepository.getCircular(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val circularLive = circularViewModel.getCircular(IDENTIFIER)
        circularLive.observeForever { }
        assertTrue(
            circularLive.value?.status == STATUS.ERROR && circularLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, times(1)).getCircular(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
        private const val ERROR_MESSAGE = "An error occurred"
    }
}