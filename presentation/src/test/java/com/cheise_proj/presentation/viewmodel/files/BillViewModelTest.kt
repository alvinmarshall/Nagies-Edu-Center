package com.cheise_proj.presentation.viewmodel.files

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.usecase.files.GetBillTask
import com.cheise_proj.presentation.extensions.asBillEntityList
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
class BillViewModelTest {

    private lateinit var getBillTask: GetBillTask
    private lateinit var billViewModel: BillViewModel

    @Mock
    lateinit var filesRepository: FilesRepository

    @Mock
    lateinit var path: IServerPath

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getBillTask = GetBillTask(filesRepository, Schedulers.trampoline(), Schedulers.trampoline())
        billViewModel = BillViewModel(getBillTask, path)
    }

    @Test
    fun `Get all bills success`() {
        val actual = TestFilesGenerator.getBills()

        Mockito.`when`(filesRepository.getBills())
            .thenReturn(Observable.just(actual.asBillEntityList()))
        val billLive = billViewModel.getBills()
        billLive.observeForever { }
        assertTrue(
            billLive.value?.status == STATUS.SUCCESS && billLive.value?.data?.size == actual.size
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getBills()
    }

    @Test
    fun `Get all bills with errors success`() {
        Mockito.`when`(filesRepository.getBills())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val billLive = billViewModel.getBills()
        billLive.observeForever { }
        assertTrue(
            billLive.value?.status == STATUS.ERROR && billLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getBills()
    }

    @Test
    fun `Get bill success`() {
        val actual = TestFilesGenerator.getBill()
        Mockito.`when`(filesRepository.getBill(IDENTIFIER))
            .thenReturn(
                Observable.just(
                    arrayListOf(actual).asBillEntityList()
                )
            )
        val billLive = billViewModel.getBill(IDENTIFIER)
        billLive.observeForever { }
        assertTrue(
            billLive.value?.status == STATUS.SUCCESS && billLive.value?.data == actual
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getBill(IDENTIFIER)
    }

    @Test
    fun `Get bill with errors success`() {
        Mockito.`when`(filesRepository.getBill(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val billLive = billViewModel.getBill(IDENTIFIER)
        billLive.observeForever { }
        assertTrue(
            billLive.value?.status == STATUS.ERROR && billLive.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(filesRepository, Mockito.times(1)).getBill(IDENTIFIER)
    }

    companion object {
        private const val IDENTIFIER: String = "1"
        private const val ERROR_MESSAGE = "An error occurred"
    }
}