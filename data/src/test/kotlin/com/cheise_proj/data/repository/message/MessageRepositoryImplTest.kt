package com.cheise_proj.data.repository.message

import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import io.reactivex.Observable
import io.reactivex.Single
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
class MessageRepositoryImplTest {
    private lateinit var messageRepositoryImpl: MessageRepositoryImpl
    private lateinit var messageDataEntityMapper: MessageDataEntityMapper
    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        messageDataEntityMapper = MessageDataEntityMapper()
        messageRepositoryImpl =
            MessageRepositoryImpl(remoteSource, localSource, messageDataEntityMapper)
    }

    @Test
    fun `Get all messages success`() {
        val remote = TestMessageGenerator.getRemoteMessage()
        val local = TestMessageGenerator.getLocalMessage()

        Mockito.`when`(remoteSource.getMessages()).thenReturn(Observable.just(remote))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(local))

        messageRepositoryImpl.getMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it.isNotEmpty() }
            .assertComplete()

        Mockito.verify(remoteSource,times(1)).getMessages()
        Mockito.verify(localSource,times(1)).getMessages()
    }

    @Test
    fun `Get local data when remote fail success`() {
        val errorMessage = "Unable to ping server address"

        Mockito.`when`(remoteSource.getMessages()).thenReturn(Observable.error(Throwable(errorMessage)))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(listOf()))

        messageRepositoryImpl.getMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource,times(1)).getMessages()
        Mockito.verify(localSource,times(1)).getMessages()
    }

    @Test
    fun `Get message with identifier success`() {
        val local = TestMessageGenerator.getLocalMessage()[0]
        Mockito.`when`(localSource.getMessage(IDENTIFIER)).thenReturn(Single.just(local))

        messageRepositoryImpl.getMessage(IDENTIFIER).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it[0]== messageDataEntityMapper.dataToEntity(local)
            }
            .assertComplete()
        Mockito.verify(localSource,times(1)).getMessage(IDENTIFIER)
        Mockito.verify(remoteSource,times(0)).getMessages()
    }
    companion object{
        const val IDENTIFIER:Int = 1
    }
}