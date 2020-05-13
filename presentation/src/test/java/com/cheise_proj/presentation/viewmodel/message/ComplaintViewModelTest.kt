package com.cheise_proj.presentation.viewmodel.message

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.usecase.message.GetComplaintTask
import com.cheise_proj.domain.usecase.message.GetSentComplaintTask
import com.cheise_proj.domain.usecase.message.SendComplaintTask
import com.cheise_proj.presentation.extensions.asEntityList
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestMessageGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertTrue
import org.junit.Before
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
        private const val CONTENT = "test content"
        private const val IS_SUCCESS = true
    }

    private lateinit var getComplaintTask: GetComplaintTask
    private lateinit var complaintViewModel: ComplaintViewModel
    private lateinit var getSentComplaintTask: GetSentComplaintTask
    private lateinit var sendComplaintTask: SendComplaintTask

    @Mock
    lateinit var messageRepository: MessageRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getComplaintTask =
            GetComplaintTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        getSentComplaintTask = GetSentComplaintTask(
            messageRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        sendComplaintTask =
            SendComplaintTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        complaintViewModel =
            ComplaintViewModel(
                getComplaintTask,
                getSentComplaintTask,
                sendComplaintTask
            )
    }

    //region SEND COMPLAINT

    @Test
    fun `Send complaint success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(messageRepository.sendComplaint(CONTENT, IDENTIFIER))
            .thenReturn(
                Observable.just(actual)
            )
        val sendLive = complaintViewModel.sendComplaint(CONTENT, IDENTIFIER)
        sendLive.observeForever { }
        assertTrue(
            sendLive.value?.status == STATUS.SUCCESS && sendLive.value?.data == actual
        )
    }

    @Test
    fun `Send complaint failed`() {
        val actual = ERROR_MESSAGE
        Mockito.`when`(messageRepository.sendComplaint(CONTENT, IDENTIFIER))
            .thenReturn(
                Observable.error(Throwable(actual))
            )
        val sendLive = complaintViewModel.sendComplaint(CONTENT, IDENTIFIER)
        sendLive.observeForever { }
        assertTrue(
            sendLive.value?.status == STATUS.ERROR && sendLive.value?.message == actual
        )
    }
    //endregion


    //region SENT COMPLAINT

    @Test
    fun `Get sent all complaint success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getSentComplaints())
            .thenReturn(Observable.just(actual.asEntityList()))
        val sentMessageLiveData = complaintViewModel.getSentComplaint()
        sentMessageLiveData.observeForever { }
        assertTrue(
            sentMessageLiveData.value?.status == STATUS.SUCCESS &&
                    sentMessageLiveData.value?.data == actual
        )
        Mockito.verify(messageRepository, times(1)).getSentComplaints()
    }


    @Test
    fun `Get sent complaint not found success`() {
        Mockito.`when`(messageRepository.getSentComplaints())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val sentMessageLiveData = complaintViewModel.getSentComplaint()
        sentMessageLiveData.observeForever { }
        assertTrue(
            sentMessageLiveData.value?.status == STATUS.ERROR &&
                    sentMessageLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getSentComplaints()
    }


    //endregion

    @Test
    fun `Get complaints success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaints())
            .thenReturn(Observable.just(actual.asEntityList()))
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
            .thenReturn(Observable.just(actual.asEntityList()))
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