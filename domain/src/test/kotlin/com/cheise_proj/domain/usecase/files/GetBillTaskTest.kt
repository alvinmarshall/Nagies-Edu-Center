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
class GetBillTaskTest {

    private lateinit var getBillTask: GetBillTask

    @Mock
    lateinit var filesRepository: FilesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getBillTask = GetBillTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all circular files success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getBills()).thenReturn(Observable.just(actual))
        getBillTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get circular with identifier success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(filesRepository.getBill(IDENTIFIER)).thenReturn(Observable.just(actual))
        getBillTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    companion object {
        const val IDENTIFIER: String = "1"
    }
}