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
class GetSentMessageTaskTest {
    private lateinit var getSentMessageTask: GetSentMessageTask
    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getSentMessageTask =
            GetSentMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all sent messages success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getSentMessages()).thenReturn(Observable.just(actual))
        getSentMessageTask.buildUseCase().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, Mockito.times(1)).getSentMessages()
        Mockito.verify(messageRepository, Mockito.times(0)).getMessage(IDENTIFIER)
    }

    @Test
    fun `Get sent message with identifier success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getMessage(IDENTIFIER)).thenReturn(Observable.just(actual))
        getSentMessageTask.buildUseCase(IDENTIFIER).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, Mockito.times(0)).getSentMessages()
        Mockito.verify(messageRepository, Mockito.times(1)).getMessage(IDENTIFIER)
    }

    companion object {
        const val IDENTIFIER = 1
    }
}