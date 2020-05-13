package com.cheise_proj.data.repository.user

import com.cheise_proj.data.extensions.asEntity
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
import utils.TestUserGenerator

@RunWith(JUnit4::class)
class UserRepositoryImplTest {
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepositoryImpl = UserRepositoryImpl(
            remoteSource,
            localSource
        )
    }

    @Test
    fun `Authenticate user success`() {
        val user = TestUserGenerator.user()
        Mockito.`when`(localSource.getUser(user.username, user.password))
            .thenReturn(Single.just(user))
        Mockito.`when`(remoteSource.authenticateUser(user.role, user.username, user.password))
            .thenReturn(
                Observable.just(user)
            )
        with(user) {
            userRepositoryImpl.authenticateUser(username, password, role)
                .test()
                .assertSubscribed()
                .assertValueCount(2)
                .assertValues(
                    this.asEntity(),
                   this.asEntity()
                )
                .assertComplete()
            Mockito.verify(remoteSource, times(1)).authenticateUser(role, username, password)
            Mockito.verify(localSource, times(1)).getUser(username, password)
            Mockito.verify(localSource, times(1)).saveUser(user)
        }
    }

    @Test
    fun `Get student profile success`() {
        val actual = TestUserGenerator.getProfile()
        val identifier = "test identifier"
        Mockito.`when`(localSource.getProfile(identifier))
            .thenReturn(Single.just(actual))
        Mockito.`when`(remoteSource.getProfile()).thenReturn(Observable.just(actual))
        userRepositoryImpl.getStudentProfile(identifier).test()
            .assertSubscribed()
            .assertValueCount(2)
            .assertValues(
                actual.asEntity(),
                actual.asEntity()
            )
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getProfile()
        Mockito.verify(localSource, times(1)).getProfile(identifier)
        Mockito.verify(localSource, times(1)).saveProfile(actual)
    }

    @Test
    fun `Get teacher profile success`() {
        val actual = TestUserGenerator.getProfile()
        val identifier = "test identifier2"
        Mockito.`when`(localSource.getProfile(identifier))
            .thenReturn(Single.just(actual))
        Mockito.`when`(remoteSource.getProfile()).thenReturn(Observable.just(actual))
        userRepositoryImpl.getTeacherProfile(identifier).test()
            .assertSubscribed()
            .assertValueCount(2)
            .assertValues(
                actual.asEntity(),
                actual.asEntity()
            )
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getProfile()
        Mockito.verify(localSource, times(1)).getProfile(identifier)
        Mockito.verify(localSource, times(1)).saveProfile(actual)
    }

    @Test
    fun `Update user password success`() {
        val identifier = "1"
        val oldPassword = "1234"
        val newPassword = "1234"
        val actual = true
        Mockito.`when`(remoteSource.changePassword(oldPassword, newPassword))
            .thenReturn(Observable.just(actual))
        userRepositoryImpl.changePassword(identifier, oldPassword, newPassword)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).changePassword(oldPassword, newPassword)
        Mockito.verify(localSource, times(1)).updatePassword(identifier, newPassword)
    }

    @Test
    fun `Update user password failed`() {
        val identifier = "1"
        val oldPassword = "1234"
        val newPassword = "1234"
        val errorMsg = "An error occurred"
        Mockito.`when`(remoteSource.changePassword(oldPassword, newPassword))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        userRepositoryImpl.changePassword(identifier, oldPassword, newPassword)
            .test()
            .assertSubscribed()
            .assertError { it.localizedMessage == errorMsg }
            .assertNotComplete()
        Mockito.verify(remoteSource, times(1)).changePassword(oldPassword, newPassword)
        Mockito.verify(localSource, times(0)).updatePassword(identifier, newPassword)
    }
}