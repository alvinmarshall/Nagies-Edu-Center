package com.cheise_proj.presentation.viewmodel.message

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.usecase.message.GetMessageTask
import com.cheise_proj.domain.usecase.message.GetSentMessageTask
import com.cheise_proj.domain.usecase.message.SendMessageTask
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

    companion object {
        private const val IDENTIFIER: Int = 1
        private const val ERROR_MESSAGE = "message not found"
        private const val CONTENT = "test content"
        private const val RECEIVER = "test receiver"
        private const val IS_SUCCESS = true
    }

    private lateinit var getMessageTask: GetMessageTask
    private lateinit var messageEntityMapper: MessageEntityMapper
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var sendMessageTask: SendMessageTask
    private lateinit var getSentMessageTask: GetSentMessageTask

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
        sendMessageTask =
            SendMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        getSentMessageTask =
            GetSentMessageTask(messageRepository, Schedulers.trampoline(), Schedulers.trampoline())
        messageViewModel = MessageViewModel(
            getMessageTask,
            messageEntityMapper,
            sendMessageTask,
            getSentMessageTask
        )
    }

    //region SENT MESSAGE

    @Test
    fun `Get sent all messages success`() {
        val actual = TestMessageGenerator.getMessage()
        Mockito.`when`(messageRepository.getSentMessages())
            .thenReturn(Observable.just(messageEntityMapper.presentationToEntityList(actual)))
        val sentMessageLiveData = messageViewModel.getSentMessages()
        sentMessageLiveData.observeForever { }
        assertTrue(
            sentMessageLiveData.value?.status == STATUS.SUCCESS &&
                    sentMessageLiveData.value?.data == actual
        )
        Mockito.verify(messageRepository, times(1)).getSentMessages()
    }


    @Test
    fun `Get sent messages not found success`() {
        Mockito.`when`(messageRepository.getSentMessages())
            .thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))
        val sentMessageLiveData = messageViewModel.getSentMessages()
        sentMessageLiveData.observeForever { }
        assertTrue(
            sentMessageLiveData.value?.status == STATUS.ERROR &&
                    sentMessageLiveData.value?.message == ERROR_MESSAGE
        )
        Mockito.verify(messageRepository, times(1)).getSentMessages()
    }


    //endregion

    @Test
    fun `Send message success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(messageRepository.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString()))
            .thenReturn(
                Observable.just(actual)
            )
        val sendLive = messageViewModel.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString())
        sendLive.observeForever { }
        assertTrue(
            sendLive.value?.status == STATUS.SUCCESS && sendLive.value?.data == actual
        )
    }

    @Test
    fun `Send message failed`() {
        val actual = ERROR_MESSAGE
        Mockito.`when`(messageRepository.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString()))
            .thenReturn(
                Observable.error(Throwable(actual))
            )
        val sendLive = messageViewModel.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString())
        sendLive.observeForever { }
        assertTrue(
            sendLive.value?.status == STATUS.ERROR && sendLive.value?.message == actual
        )
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
}