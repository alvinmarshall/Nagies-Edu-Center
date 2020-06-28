package com.cheise_proj.presentation.viewmodel.files

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.usecase.files.GetTimeTableTask
import com.cheise_proj.presentation.extensions.asTimeTableEntityList
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
class TimeTableViewModelTest {

    private lateinit var timeTableViewModel: TimeTableViewModel
    private lateinit var getTimeTableTask: GetTimeTableTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Mock
    lateinit var path: IServerPath

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getTimeTableTask =
            GetTimeTableTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
        timeTableViewModel = TimeTableViewModel(getTimeTableTask, path)
    }

    @Test
    fun `Get all timetables success`() {
        val actual = TestFilesGenerator.getTimeTables()

        Mockito.`when`(filesRepository.getTimeTables())
            .thenReturn(Observable.just(actual.asTimeTableEntityList()))
        val reportLive = timeTableViewModel.getTimeTables()
        reportLive.observeForever { }
        assertTrue(
            reportLive.value?.status == STATUS.SUCCESS && reportLive.value?.data?.size == actual.size
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getTimeTables()
    }

    @Test
    fun `Get all timetables with errors success`() {
        Mockito.`when`(filesRepository.getTimeTables())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val timeTableLive = timeTableViewModel.getTimeTables()
        timeTableLive.observeForever { }
        assertTrue(
            timeTableLive.value?.status == STATUS.ERROR && timeTableLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getTimeTables()
    }

    @Test
    fun `Get timetable success`() {
        val actual = TestFilesGenerator.getTimeTable()
        Mockito.`when`(filesRepository.getTimeTable(IDENTIFIER))
            .thenReturn(
                Observable.just(
                    arrayListOf(actual).asTimeTableEntityList()
                )
            )
        val timeTableLive = timeTableViewModel.getTimeTable(IDENTIFIER)
        timeTableLive.observeForever { }
        assertTrue(
            timeTableLive.value?.status == STATUS.SUCCESS && timeTableLive.value?.data == actual
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getTimeTable(IDENTIFIER)
    }

    @Test
    fun `Get timetable with errors success`() {
        Mockito.`when`(filesRepository.getTimeTable(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val timeTableLive = timeTableViewModel.getTimeTable(IDENTIFIER)
        timeTableLive.observeForever { }
        assertTrue(
            timeTableLive.value?.status == STATUS.ERROR && timeTableLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getTimeTable(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
        private const val ERROR_MESSAGE = "An error occurred"
    }
}