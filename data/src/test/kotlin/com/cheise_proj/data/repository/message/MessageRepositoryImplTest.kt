package com.cheise_proj.data.repository.message

import com.cheise_proj.data.extensions.asEntity
import com.cheise_proj.data.extensions.asEntityList
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
    companion object {
        private const val IDENTIFIER: Int = 1
        private const val CONTENT = "test content"
        private const val RECEIVER = "test receiver"
        private const val IS_SUCCESS = true
    }

    private lateinit var messageRepositoryImpl: MessageRepositoryImpl

    @Mock
    lateinit var localSource: LocalSource

    @Mock
    lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        messageRepositoryImpl =
            MessageRepositoryImpl(remoteSource, localSource)
    }

    //region SENT COMPLAINT
    @Test
    fun `Get local sent complaint when remote fail success`() {
        val errorMessage = "Unable to ping server address"

        Mockito.`when`(remoteSource.getSentComplaint())
            .thenReturn(Observable.error(Throwable(errorMessage)))
        Mockito.`when`(localSource.getComplaints()).thenReturn(Observable.just(listOf()))

        messageRepositoryImpl.getSentComplaints().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getSentComplaint()
        Mockito.verify(localSource, times(1)).getComplaints()
    }

    @Test
    fun `Get all sent complaint success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(remoteSource.getSentComplaint()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getComplaints()).thenReturn(Observable.just(actual))

        messageRepositoryImpl.getSentComplaints()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

        Mockito.verify(remoteSource, times(1)).getSentComplaint()
        Mockito.verify(localSource, times(1)).getComplaints()
    }
    //endregion

    //region SENT MESSAGES
    @Test
    fun `Get local sent data when remote fail success`() {
        val errorMessage = "Unable to ping server address"

        Mockito.`when`(remoteSource.getSentMessages())
            .thenReturn(Observable.error(Throwable(errorMessage)))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(listOf()))

        messageRepositoryImpl.getSentMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getSentMessages()
        Mockito.verify(localSource, times(1)).getMessages()
    }

    @Test
    fun `Get all sent messages success`() {
        val actual = TestMessageGenerator.getRemoteMessage()
        Mockito.`when`(remoteSource.getSentMessages()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(actual))

        messageRepositoryImpl.getSentMessages()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

        Mockito.verify(remoteSource, times(1)).getSentMessages()
        Mockito.verify(localSource, times(1)).getMessages()
    }
    //endregion

    //region SEND MESSAGE
    @Test
    fun `Send message success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(remoteSource.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString()))
            .thenReturn(
                Observable.just(actual)
            )
        messageRepositoryImpl.sendMessage(CONTENT, RECEIVER, IDENTIFIER.toString())
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }

    @Test
    fun `Send complaint success`() {
        val actual = IS_SUCCESS
        Mockito.`when`(remoteSource.sendComplaint(CONTENT, IDENTIFIER.toString()))
            .thenReturn(
                Observable.just(actual)
            )
        messageRepositoryImpl.sendComplaint(CONTENT, IDENTIFIER.toString())
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
    }
    //endregion


    //region COMPLAINT
    @Test
    fun `Get all complaint success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(remoteSource.getComplaint()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getComplaints()).thenReturn(Observable.just(actual))

        messageRepositoryImpl.getComplaints()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

        Mockito.verify(remoteSource, times(1)).getComplaint()
        Mockito.verify(localSource, times(1)).getComplaints()
    }

    @Test
    fun `Get local complaint when remote fail success`() {
        val errorMessage = "Unable to ping server address"

        Mockito.`when`(remoteSource.getComplaint())
            .thenReturn(Observable.error(Throwable(errorMessage)))
        Mockito.`when`(localSource.getComplaints()).thenReturn(Observable.just(listOf()))

        messageRepositoryImpl.getComplaints().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getComplaint()
        Mockito.verify(localSource, times(1)).getComplaints()
    }

    @Test
    fun `Get complaint with identifier success`() {
        val actual = TestMessageGenerator.getComplaint()
        Mockito.`when`(localSource.getComplaint(IDENTIFIER.toString()))
            .thenReturn(Single.just(actual[0]))
        messageRepositoryImpl.getComplaint(IDENTIFIER.toString())
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual.asEntityList()
            }
            .assertComplete()
    }
    //endregion

    @Test
    fun `Get local data when remote fail success`() {
        val errorMessage = "Unable to ping server address"

        Mockito.`when`(remoteSource.getMessages())
            .thenReturn(Observable.error(Throwable(errorMessage)))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(listOf()))

        messageRepositoryImpl.getMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getMessages()
        Mockito.verify(localSource, times(1)).getMessages()
    }

    @Test
    fun `Get all messages success`() {
        val actual = TestMessageGenerator.getRemoteMessage()
        Mockito.`when`(remoteSource.getMessages()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getMessages()).thenReturn(Observable.just(actual))

        messageRepositoryImpl.getMessages()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertComplete()

        Mockito.verify(remoteSource, times(1)).getMessages()
        Mockito.verify(localSource, times(1)).getMessages()
    }

    @Test
    fun `Get message with identifier success`() {
        val local = TestMessageGenerator.getLocalMessage()[0]
        Mockito.`when`(localSource.getMessage(IDENTIFIER)).thenReturn(Single.just(local))

        messageRepositoryImpl.getMessage(IDENTIFIER).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it[0] == local.asEntity()
            }
            .assertComplete()
        Mockito.verify(localSource, times(1)).getMessage(IDENTIFIER)
        Mockito.verify(remoteSource, times(0)).getMessages()
    }
}