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
class GetAssignmentTaskTest {
    private lateinit var getAssignmentTask: GetAssignmentTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAssignmentTask =
            GetAssignmentTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all assignments success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getAssignments()).thenReturn(Observable.just(actual))
        getAssignmentTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(1)).getAssignments()
        Mockito.verify(filesRepository, times(0)).getAssignment(IDENTIFIER)
    }

    @Test
    fun `Get assignment success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getAssignment(IDENTIFIER))
            .thenReturn(Observable.just(actual))
        getAssignmentTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(filesRepository, times(0)).getAssignments()
        Mockito.verify(filesRepository, times(1)).getAssignment(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
    }
}