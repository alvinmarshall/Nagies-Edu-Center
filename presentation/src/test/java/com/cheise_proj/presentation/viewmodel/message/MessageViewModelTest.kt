package com.cheise_proj.presentation.viewmodel.message

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.usecase.message.GetMessageTask
import com.cheise_proj.presentation.mapper.message.MessageEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestMessageGenerator
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
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MessageViewModelTest {
    private lateinit var getMessageTask: GetMessageTask
    private lateinit var messageEntityMapper: MessageEntityMapper
    private lateinit var messageViewModel: MessageViewModel

    @Mock
    lateinit var messageRepository: MessageRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        messageEntityMapper = MessageEntityMapper()
        getMessageTask =
            GetMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        messageViewModel = MessageViewModel(getMessageTask, messageEntityMapper)
    }

    @Test
    fun `Get all messages success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getMessages())
            .thenReturn(Observable.just(messageEntityMapper.presentationToEntityList(actual)))
        val messageLiveData = messageViewModel.getMessages()
        messageLiveData.observeForever { }
        assertTrue(
            messageLiveData.value?.status == STATUS.SUCCESS &&
                    messageLiveData.value?.data == actual
        )
        Mockito.verify(messageRepository, times(1)).getMessages()
    }

    @Test
    fun `Get message success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getMessage(IDENTIFIER))
            .thenReturn(Observable.just(messageEntityMapper.presentationToEntityList(actual)))
        val messageLiveData = messageViewModel.getMessage(IDENTIFIER)
        messageLiveData.observeForever { }
        assertTrue(
            messageLiveData.value?.status == STATUS.SUCCESS &&
                    messageLiveData.value?.data == actual[0]
        )
        Mockito.verify(messageRepository, times(1)).getMessage(IDENTIFIER)
    }

    @Test
    fun `Get messages not found success`() {
        Mockito.`when`(messageRepository.getMessages())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val messageLiveData = messageViewModel.getMessages()
        messageLiveData.observeForever { }
        assertTrue(
            messageLiveData.value?.status == STATUS.ERROR &&
                    messageLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getMessages()
    }

    @Test
    fun `Get message identifier not found success`() {
        Mockito.`when`(messageRepository.getMessage(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val messageLiveData = messageViewModel.getMessage(IDENTIFIER)
        messageLiveData.observeForever { }
        assertTrue(
            messageLiveData.value?.status == STATUS.ERROR &&
                    messageLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getMessage(IDENTIFIER)
    }

    companion object {
        const val IDENTIFIER: Int = 1
        const val ERROR_MESSAGE = "message not found"
    }
}