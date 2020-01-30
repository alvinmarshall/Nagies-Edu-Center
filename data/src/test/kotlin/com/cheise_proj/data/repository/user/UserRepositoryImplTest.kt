package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.ProfileDataEntityMapper
import com.cheise_proj.data.mapper.user.UserDataEntityMapper
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
    private lateinit var userDataEntityMapper: UserDataEntityMapper
    private lateinit var profileDataEntityMapper: ProfileDataEntityMapper
    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userDataEntityMapper = UserDataEntityMapper()
        profileDataEntityMapper = ProfileDataEntityMapper()
        userRepositoryImpl = UserRepositoryImpl(remoteSource, localSource, userDataEntityMapper,profileDataEntityMapper)
    }

    @Test
    fun `Authenticate user from empty local db success`() {
        val user = TestUserGenerator.user()
        val errorMsg = "An exception occurred while getting user"
        Mockito.`when`(localSource.getUser(user.username, user.password))
            .thenReturn(Single.error(Throwable(errorMsg)))
        Mockito.`when`(remoteSource.authenticateUser(user.role, user.username, user.password))
            .thenReturn(
                Observable.just(user)
            )
        Mockito.doNothing().`when`(localSource).saveUser(user)
        with(user) {
            userRepositoryImpl.authenticateUser(username, password, role).test()
                .assertSubscribed()
                .assertValueCount(1)
                .assertValue {
                    println(it)
                    it == userDataEntityMapper.dataToEntity(this)
                }
                .assertComplete()
            Mockito.verify(remoteSource, times(1)).authenticateUser(role, username, password)
            Mockito.verify(localSource, times(1)).getUser(username, password)
            Mockito.verify(localSource, times(1)).saveUser(user)
        }

    }

    @Test
    fun `Get student profile success`(){
        val actual = TestUserGenerator.getProfile()
        val errorMsg = "An exception occurred from local"
        val identifier = "test identifier"
        Mockito.`when`(localSource.getProfile(identifier)).thenReturn(Single.error(Throwable(errorMsg)))
        Mockito.`when`(remoteSource.getProfile()).thenReturn(Observable.just(actual))
        Mockito.doNothing().`when`(localSource).saveProfile(actual)
        userRepositoryImpl.getStudentProfile(identifier).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getProfile()
        Mockito.verify(localSource, times(1)).getProfile(identifier)
        Mockito.verify(localSource, times(1)).saveProfile(actual)

    }

    @Test
    fun `Get teacher profile success`(){
        val actual = TestUserGenerator.getProfile()
        val errorMsg = "An exception occurred from local"
        val identifier = "test identifier2"
        Mockito.`when`(localSource.getProfile(identifier)).thenReturn(Single.error(Throwable(errorMsg)))
        Mockito.`when`(remoteSource.getProfile()).thenReturn(Observable.just(actual))
        Mockito.doNothing().`when`(localSource).saveProfile(actual)
        userRepositoryImpl.getTeacherProfile(identifier).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getProfile()
        Mockito.verify(localSource, times(1)).getProfile(identifier)
        Mockito.verify(localSource, times(1)).saveProfile(actual)
    }
}