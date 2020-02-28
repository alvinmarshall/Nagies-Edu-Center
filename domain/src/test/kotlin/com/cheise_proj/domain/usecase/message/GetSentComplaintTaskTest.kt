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
import org.mockito.MockitoAnnotations
import utils.TestMessageGenerator

@RunWith(JUnit4::class)
class GetSentComplaintTaskTest {

    companion object {
        private const val IDENTIFIER = "1"
    }

    private lateinit var getSentComplaintTask: GetSentComplaintTask

    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getSentComplaintTask =
            GetSentComplaintTask(
                messageRepository,
                Schedulers.trampoline(),
                Schedulers.trampoline()
            )
    }

    @Test
    fun `Get all sent complaints success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getSentComplaints()).thenReturn(Observable.just(actual))
        getSentComplaintTask.buildUseCase()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
    }

    @Test
    fun `Get sent complaint with identifier success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(messageRepository.getComplaint(IDENTIFIER))
            .thenReturn(Observable.just(actual))
    }
}