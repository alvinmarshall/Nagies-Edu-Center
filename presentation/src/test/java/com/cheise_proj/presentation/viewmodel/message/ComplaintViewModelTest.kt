package com.cheise_proj.presentation.viewmodel.message

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.usecase.message.GetComplaintTask
import com.cheise_proj.presentation.mapper.message.ComplaintEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestMessageGenerator
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
class ComplaintViewModelTest {

    companion object {
        private const val IDENTIFIER = "1"
        private const val ERROR_MESSAGE = "complaint not found"
    }

    private lateinit var getComplaintTask: GetComplaintTask
    private lateinit var complaintViewModel: ComplaintViewModel
    private lateinit var complaintEntityMapper: ComplaintEntityMapper

    @Mock
    lateinit var messageRepository: MessageRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getComplaintTask =
            GetComplaintTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        complaintEntityMapper = ComplaintEntityMapper()
        complaintViewModel = ComplaintViewModel(getComplaintTask, complaintEntityMapper)
    }

    @Test
    fun `Get complaints success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaints())
            .thenReturn(Observable.just(complaintEntityMapper.presentationToEntityList(actual)))
        val complaintLive = complaintViewModel.getComplaintList()
        complaintLive.observeForever { }
        assertTrue(
            complaintLive.value?.status == STATUS.SUCCESS && complaintLive.value?.data == actual
        )
    }

    @Test
    fun `Get complaint success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaint(IDENTIFIER))
            .thenReturn(Observable.just(complaintEntityMapper.presentationToEntityList(actual)))
        val complaintLive = complaintViewModel.getComplaint(IDENTIFIER)
        complaintLive.observeForever { }
        assertTrue(
            complaintLive.value?.status == STATUS.SUCCESS && complaintLive.value?.data == actual[0]
        )
    }

    @Test
    fun `Get complaints not found success`() {
        Mockito.`when`(messageRepository.getComplaints())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val complaintLiveData = complaintViewModel.getComplaintList()
        complaintLiveData.observeForever { }
        assertTrue(
            complaintLiveData.value?.status == STATUS.ERROR &&
                    complaintLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getComplaints()
    }

    @Test
    fun `Get complaint identifier not found success`() {
        Mockito.`when`(messageRepository.getComplaint(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val complaintLiveData = complaintViewModel.getComplaint(IDENTIFIER)
        complaintLiveData.observeForever { }
        assertTrue(
            complaintLiveData.value?.status == STATUS.ERROR &&
                    complaintLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getComplaint(IDENTIFIER)
    }
}