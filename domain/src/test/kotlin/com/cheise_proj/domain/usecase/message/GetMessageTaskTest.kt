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
class GetMessageTaskTest {
    private lateinit var getMessageTask: GetMessageTask
    @Mock
    lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMessageTask =
            GetMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get all messages success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getMessages()).thenReturn(Observable.just(actual))
        getMessageTask.buildUseCase().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, times(1)).getMessages()
        Mockito.verify(messageRepository, times(0)).getMessage(IDENTIFIER)
    }

    @Test
    fun `Get message with identifier success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getMessage(IDENTIFIER)).thenReturn(Observable.just(actual))
        getMessageTask.buildUseCase(IDENTIFIER).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
        Mockito.verify(messageRepository, times(0)).getMessages()
        Mockito.verify(messageRepository, times(1)).getMessage(IDENTIFIER)
    }

    companion object {
        const val IDENTIFIER = 1
    }
}