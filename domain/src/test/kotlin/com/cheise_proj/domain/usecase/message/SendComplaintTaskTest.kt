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

@RunWith(JUnit4::class)
class SendComplaintTaskTest {
    companion object {
        private const val CONTENT = "test content"
        private const val IDENTIFIER = "test identifier"
        private const val IS_SUCCESS = true
    }

    private lateinit var sendComplaintTask: SendComplaintTask

    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sendComplaintTask =
            SendComplaintTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Send complaint success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(messageRepository.sendComplaint(CONTENT, IDENTIFIER))
            .thenReturn(Observable.just(actual))
        sendComplaintTask.buildUseCase(sendComplaintTask.Params(CONTENT, IDENTIFIER))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Send complaint with no params throws an exception`() {
        sendComplaintTask.buildUseCase()
    }
}