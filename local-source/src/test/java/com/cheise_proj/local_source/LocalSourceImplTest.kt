package com.cheise_proj.local_source

import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.utils.TestMessageGenerator
import com.cheise_proj.local_source.utils.TestUserGenerator
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

@RunWith(JUnit4::class)
class LocalSourceImplTest {
    private lateinit var localSourceImpl: LocalSourceImpl
    private lateinit var userLocalDataMapper: UserLocalDataMapper
    private lateinit var profileLocalDataMapper: ProfileLocalDataMapper
    private lateinit var messageLocalDataMapper: MessageLocalDataMapper

    @Mock
    lateinit var userDao: UserDao
    @Mock
    lateinit var messageDao: MessageDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userLocalDataMapper = UserLocalDataMapper()
        profileLocalDataMapper = ProfileLocalDataMapper()
        messageLocalDataMapper = MessageLocalDataMapper()

        localSourceImpl = LocalSourceImpl(
            userDao,
            userLocalDataMapper,
            profileLocalDataMapper,
            messageDao,
            messageLocalDataMapper
        )
    }

    //region USER
    @Test
    fun `Get user from local with credentials success`() {
        val actual = TestUserGenerator.user()
        with(actual) {
            Mockito.`when`(userDao.getUser(username, password)).thenReturn(Single.just(this))
            localSourceImpl.getUser(username, password)
                .test()
                .assertValueCount(1)
                .assertValue {
                    it == userLocalDataMapper.localToData(actual)
                }
                .assertSubscribed()
                .assertComplete()
            Mockito.verify(userDao, times(1)).getUser(username, password)
        }
    }

    @Test
    fun `Save user to local success`() {
        val actual = TestUserGenerator.user()
        Mockito.doNothing().`when`(userDao).saveUser(actual)
        localSourceImpl.saveUser(userLocalDataMapper.localToData(actual))
        Mockito.verify(userDao, times(1)).saveUser(actual)
    }

    @Test
    fun `Get user profile from local success`() {
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(userDao.getProfile(USER_IDENTIFIER)).thenReturn(Single.just(actual))
        localSourceImpl.getProfile(USER_IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileLocalDataMapper.localToData(actual)
            }
            .assertComplete()
        Mockito.verify(userDao, times(1)).getProfile(USER_IDENTIFIER)
    }

    @Test
    fun `Save user profile to local success`() {
        val actual = TestUserGenerator.getProfile()
        Mockito.doNothing().`when`(userDao).saveProfile(actual)
        localSourceImpl.saveProfile(profileLocalDataMapper.localToData(actual))
        Mockito.verify(userDao, times(1)).saveProfile(actual)
    }
    //endregion

    //region MESSAGE
    @Test
    fun `Get all messages from local success`() {
        val actual = TestMessageGenerator.getMessages()
        Mockito.`when`(messageDao.getMessages()).thenReturn(Observable.just(actual))
        localSourceImpl.getMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it == messageLocalDataMapper.localToDataList(actual)
            }
            .assertComplete()
        Mockito.verify(messageDao, times(1)).getMessages()
    }

    @Test
    fun `Get message from local success`() {
        val actual = TestMessageGenerator.getMessages()[0]
        Mockito.`when`(messageDao.getMessage(MESSAGE_IDENTIFIER)).thenReturn(Single.just(actual))
        localSourceImpl.getMessage(MESSAGE_IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it == messageLocalDataMapper.localToData(actual)
            }
            .assertComplete()
        Mockito.verify(messageDao, times(1)).getMessage(MESSAGE_IDENTIFIER)

    }

    @Test
    fun `Save messages to local success`() {
        val actual = TestMessageGenerator.getMessages()
        Mockito.doNothing().`when`(messageDao).saveMessages(actual)
        localSourceImpl.saveMessages(messageLocalDataMapper.localToDataList(actual))
        Mockito.verify(messageDao, times(1)).saveMessages(actual)
    }
    //endregion

    companion object {
        const val MESSAGE_IDENTIFIER: Int = 1
        const val USER_IDENTIFIER: String = "test identifier"
    }
}