package com.cheise_proj.domain.usecase.message

import com.cheise_proj.domain.repository.MessageRepository
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
import utils.TestMessageGenerator

@RunWith(JUnit4::class)
class GetComplaintTaskTest {
    companion object {
        private const val IDENTIFIER = "1"
    }

    private lateinit var getComplaintTask: GetComplaintTask

    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getComplaintTask =
            GetComplaintTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get Complaints success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaints()).thenReturn(Observable.just(actual))
        getComplaintTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, times(1)).getComplaints()
        Mockito.verify(messageRepository, times(0)).getComplaint(IDENTIFIER)
    }

    @Test
    fun `Get Complaint success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaint(IDENTIFIER))
            .thenReturn(Observable.just(actual))
        getComplaintTask.buildUseCase(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, times(0)).getComplaints()
        Mockito.verify(messageRepository, times(1)).getComplaint(IDENTIFIER)
    }
}