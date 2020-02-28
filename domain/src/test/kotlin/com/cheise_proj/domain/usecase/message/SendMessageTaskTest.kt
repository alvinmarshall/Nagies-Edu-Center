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
import java.lang.IllegalArgumentException

@RunWith(JUnit4::class)
class SendMessageTaskTest {

    companion object {
        private const val CONTENT = "test content"
        private const val IDENTIFIER = "test identifier"
        private const val RECEIVER_NAME = "test receiver name"
        private const val IS_SUCCESS = true
    }

    private lateinit var sendMessageTask: SendMessageTask

    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sendMessageTask =
            SendMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Send message success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(messageRepository.sendMessage(CONTENT, RECEIVER_NAME, IDENTIFIER)).thenReturn(
            Observable.just(actual)
        )
        sendMessageTask.buildUseCase(sendMessageTask.Params(CONTENT, RECEIVER_NAME, IDENTIFIER))
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Send message with no params throws an exception`() {
        sendMessageTask.buildUseCase()
    }
}