package com.cheise_proj.data.repository.message

import com.cheise_proj.data.mapper.message.ComplaintDataEntityMapper
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
    private lateinit var complaintDataEntityMapper: ComplaintDataEntityMapper

    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        messageDataEntityMapper = MessageDataEntityMapper()
        complaintDataEntityMapper = ComplaintDataEntityMapper()
        messageRepositoryImpl =
            MessageRepositoryImpl(
                remoteSource,
                localSource,
                messageDataEntityMapper,
                complaintDataEntityMapper
            )
    }


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
            .assertValue {
                it == complaintDataEntityMapper.dataToEntityList(actual)
            }
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
                it == complaintDataEntityMapper.dataToEntityList(actual)
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
                it[0] == messageDataEntityMapper.dataToEntity(local)
            }
            .assertComplete()
        Mockito.verify(localSource, times(1)).getMessage(IDENTIFIER)
        Mockito.verify(remoteSource, times(0)).getMessages()
    }

    companion object {
        const val IDENTIFIER: Int = 1
    }
}