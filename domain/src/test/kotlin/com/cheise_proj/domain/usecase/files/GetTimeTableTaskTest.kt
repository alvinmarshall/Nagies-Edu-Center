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
class GetTimeTableTaskTest {

    private lateinit var getTimeTableTask: GetTimeTableTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getTimeTableTask =
            GetTimeTableTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all timetables success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getTimeTables()).thenReturn(Observable.just(actual))
        getTimeTableTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(1)).getTimeTables()
        Mockito.verify(filesRepository, times(0)).getTimeTable(IDENTIFIER)
    }

    @Test
    fun `Get timetable success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getTimeTable(IDENTIFIER)).thenReturn(Observable.just(actual))
        getTimeTableTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(0)).getTimeTables()
        Mockito.verify(filesRepository, times(1)).getTimeTable(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
    }
}