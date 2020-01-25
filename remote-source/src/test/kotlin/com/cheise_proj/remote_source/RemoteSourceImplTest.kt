package com.cheise_proj.remote_source

import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.UserDtoDataMapper
import com.cheise_proj.remote_source.model.request.LoginRequest
import io.reactivex.Observable
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
class RemoteSourceImplTest {

    private lateinit var remoteSourceImpl: RemoteSourceImpl
    private lateinit var userDtoDataMapper: UserDtoDataMapper
    private val username = "test username"
    private val password = "test password"
    private val parent = "parent role"
    @Mock
    private lateinit var apiService: ApiService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userDtoDataMapper = UserDtoDataMapper()
        remoteSourceImpl = RemoteSourceImpl(apiService, userDtoDataMapper)
    }

    @Test
    fun `Authenticate parent role success`() {
        val response = TestUserGenerator.user()
        Mockito.`when`(apiService.getAuthenticateUser(parent, LoginRequest(username,password))).thenReturn(
            Observable.just(response)
        )
        remoteSourceImpl.authenticateUser(parent, username, password).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it.token == userDtoDataMapper.dataToDto(it).token
            }
            .assertComplete()
        Mockito.verify(apiService, times(1))
            .getAuthenticateUser(parent,LoginRequest(username,password))
    }
}