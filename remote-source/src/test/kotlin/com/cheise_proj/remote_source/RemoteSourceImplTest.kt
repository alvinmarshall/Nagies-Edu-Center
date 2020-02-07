package com.cheise_proj.remote_source

import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.files.CircularDtoDataMapper
import com.cheise_proj.remote_source.mapper.files.FilesDtoDataMapper
import com.cheise_proj.remote_source.mapper.message.MessageDtoDataMapper
import com.cheise_proj.remote_source.mapper.user.ProfileDtoDataMapper
import com.cheise_proj.remote_source.mapper.user.UserDtoDataMapper
import com.cheise_proj.remote_source.model.request.LoginRequest
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
import utils.TestFilesGenerator
import utils.TestMessageGenerator
import utils.TestUserGenerator

@RunWith(JUnit4::class)
class RemoteSourceImplTest {

    private lateinit var remoteSourceImpl: RemoteSourceImpl
    private lateinit var userDtoDataMapper: UserDtoDataMapper
    private lateinit var profileDtoDataMapper: ProfileDtoDataMapper
    private lateinit var messageDtoDataMapper: MessageDtoDataMapper
    private lateinit var circularDtoDataMapper: CircularDtoDataMapper
    private lateinit var filesDtoDataMapper: FilesDtoDataMapper
    private val username = "test username"
    private val password = "test password"
    private val parent = "parent role"
    @Mock
    private lateinit var apiService: ApiService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userDtoDataMapper = UserDtoDataMapper()
        profileDtoDataMapper = ProfileDtoDataMapper()
        messageDtoDataMapper = MessageDtoDataMapper()
        circularDtoDataMapper = CircularDtoDataMapper()
        filesDtoDataMapper = FilesDtoDataMapper()

        remoteSourceImpl = RemoteSourceImpl(
            apiService,
            userDtoDataMapper,
            profileDtoDataMapper,
            messageDtoDataMapper,
            circularDtoDataMapper,
            filesDtoDataMapper
        )
    }

    //region FILES
    @Test
    fun `Get all assignment from remote success`() {
        val actual = TestFilesGenerator.getAssignmentDto()
        Mockito.`when`(apiService.getAssignment()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getAssignment()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDtoDataMapper.dtoToDataList(actual.data)
            }
            .assertComplete()
    }

    @Test
    fun `Get assignment from network with no network throws an exception`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getAssignment()).thenReturn(Observable.error(Throwable(actual)))
        remoteSourceImpl.getAssignment()
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
    }


    @Test
    fun `Get all circulars from remote success`() {
        val actual = TestFilesGenerator.getCircularDto()
        Mockito.`when`(apiService.getCircular()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getCircular()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == circularDtoDataMapper.dtoToDataList(actual.data)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getCircular()
    }

    @Test
    fun `Get all circulars from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getCircular()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getCircular()
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(apiService, times(1)).getCircular()
    }
    //endregion

    //region MESSAGES
    @Test
    fun `Get messages from remote success`() {
        val actual = TestMessageGenerator.getMessageDto()
        Mockito.`when`(apiService.getMessages()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getMessages().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == messageDtoDataMapper.dtoToDataList(actual.message)
            }
            .assertComplete()
    }

    @Test
    fun `Get messages from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getMessages()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getMessages().test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
    }
    //endregion

    //region USERS
    @Test
    fun `Authenticate parent role success`() {
        val response = TestUserGenerator.user()
        Mockito.`when`(apiService.getAuthenticateUser(parent, LoginRequest(username, password)))
            .thenReturn(
                Single.just(response)
            )
        remoteSourceImpl.authenticateUser(parent, username, password).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it.token == userDtoDataMapper.dataToDto(it).token
            }
            .assertComplete()
        Mockito.verify(apiService, times(1))
            .getAuthenticateUser(parent, LoginRequest(username, password))
    }

    @Test
    fun `Authenticate parent role on error`() {
        val errorMsg = "HTTP 401"
        val actual = "username or password invalid"
        Mockito.`when`(apiService.getAuthenticateUser(parent, LoginRequest(username, password)))
            .thenReturn(
                Single.error(Throwable(errorMsg))
            )
        remoteSourceImpl.authenticateUser(parent, username, password).test()
            .assertSubscribed()
            .assertErrorMessage(actual)
        Mockito.verify(apiService, times(1))
            .getAuthenticateUser(parent, LoginRequest(username, password))
    }

    @Test
    fun `Get student profile success`() {
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(apiService.getProfile()).thenReturn(Single.just(actual))
        remoteSourceImpl.getProfile().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileDtoDataMapper.dtoToData(actual.student!!)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getProfile()
    }

    @Test
    fun `Get teacher profile success`() {
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(apiService.getProfile()).thenReturn(Single.just(actual))
        remoteSourceImpl.getProfile().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileDtoDataMapper.dtoToData(actual.teacher)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getProfile()
    }
    //endregion

    companion object {
        private const val NO_NETWORK_ERROR = "Unable to resolve host"
    }
}