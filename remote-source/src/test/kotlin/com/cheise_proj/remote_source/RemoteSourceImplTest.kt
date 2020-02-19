package com.cheise_proj.remote_source

import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.files.CircularDtoDataMapper
import com.cheise_proj.remote_source.mapper.files.FilesDtoDataMapper
import com.cheise_proj.remote_source.mapper.message.ComplaintDtoDataMapper
import com.cheise_proj.remote_source.mapper.message.MessageDtoDataMapper
import com.cheise_proj.remote_source.mapper.people.PeopleDtoDataMapper
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
import utils.TestPeopleGenerator
import utils.TestUserGenerator

@RunWith(JUnit4::class)
class RemoteSourceImplTest {

    private lateinit var remoteSourceImpl: RemoteSourceImpl
    private lateinit var userDtoDataMapper: UserDtoDataMapper
    private lateinit var profileDtoDataMapper: ProfileDtoDataMapper
    private lateinit var messageDtoDataMapper: MessageDtoDataMapper
    private lateinit var circularDtoDataMapper: CircularDtoDataMapper
    private lateinit var filesDtoDataMapper: FilesDtoDataMapper
    private lateinit var peopleDtoDataMapper: PeopleDtoDataMapper
    private lateinit var complaintDtoDataMapper: ComplaintDtoDataMapper

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
        peopleDtoDataMapper = PeopleDtoDataMapper()
        complaintDtoDataMapper = ComplaintDtoDataMapper()

        remoteSourceImpl = RemoteSourceImpl(
            apiService,
            userDtoDataMapper,
            profileDtoDataMapper,
            messageDtoDataMapper,
            circularDtoDataMapper,
            filesDtoDataMapper,
            peopleDtoDataMapper,
            complaintDtoDataMapper
        )
    }

    //region PEOPLE
    @Test
    fun `Get class teacher from remote success`() {
        val teacher = TestPeopleGenerator.getPeopleDto()
        Mockito.`when`(apiService.getClassTeacher()).thenReturn(Observable.just(teacher))
        remoteSourceImpl.getPeople(TYPE_TEACHER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == peopleDtoDataMapper.dtoToDataList(teacher.teacher!!)
            }
            .assertComplete()
    }

    @Test
    fun `Get class student from remote success`() {
        val student = TestPeopleGenerator.getPeopleDto()
        Mockito.`when`(apiService.getClassStudent()).thenReturn(Observable.just(student))
        remoteSourceImpl.getPeople(TYPE_STUDENT)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == peopleDtoDataMapper.dtoToDataList(student.student)
            }
            .assertComplete()
    }

    @Test
    fun `Get people from remote with no network`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getClassTeacher()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getPeople(TYPE_TEACHER)
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
    }

    //endregion

    //region FILES

    //region RECEIPT
    @Test
    fun `Upload receipt success`() {
        val filePart = TestFilesGenerator.getFilePart()
        val actual = TestFilesGenerator.getUploadDto()
        val status = 200
        Mockito.`when`(apiService.uploadReceipt(filePart)).thenReturn(Observable.just(actual))
        remoteSourceImpl.uploadReceipt(filePart)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == status
            }
            .assertComplete()
    }
    //endregion

    //region BILL
    @Test
    fun `Get all bills from remote success`() {
        val actual = TestFilesGenerator.getBillsDto()
        Mockito.`when`(apiService.getBilling()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getBill()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDtoDataMapper.dtoToDataList(actual.data)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getBilling()
    }

    @Test
    fun `Get all bill from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getBilling()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getBill()
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(apiService, times(1)).getBilling()
    }
    //endregion

    //region REPORT
    @Test
    fun `Get all timetables from remote success`() {
        val actual = TestFilesGenerator.getTimeTablesDto()
        Mockito.`when`(apiService.getTimeTable()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getTimeTable()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDtoDataMapper.dtoToDataList(actual.data)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getTimeTable()
    }

    @Test
    fun `Get all timetables from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getTimeTable()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getTimeTable()
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(apiService, times(1)).getTimeTable()
    }
    //endregion


    //region REPORT
    @Test
    fun `Get all reports from remote success`() {
        val actual = TestFilesGenerator.getReportsDto()
        Mockito.`when`(apiService.getReport()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getReport()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDtoDataMapper.dtoToDataList(actual.data)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).getReport()
    }

    @Test
    fun `Get all reports from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getReport()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getReport()
            .test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
        Mockito.verify(apiService, times(1)).getReport()
    }
    //endregion

    //region ASSIGNMENT
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
    //endregion

    //region CIRCULAR
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
    //endregion

    //region MESSAGES

    //complaint
    @Test
    fun `Get complaints from remote success`() {
        val actual = TestMessageGenerator.getComplaintDto()
        Mockito.`when`(apiService.getComplaint()).thenReturn(Observable.just(actual))
        remoteSourceImpl.getComplaint().test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == complaintDtoDataMapper.dtoToDataList(actual.complaint)
            }
            .assertComplete()
    }

    @Test
    fun `Get complaints from remote with no network success`() {
        val actual = "No internet connection"
        Mockito.`when`(apiService.getComplaint()).thenReturn(
            Observable.error(
                Throwable(
                    NO_NETWORK_ERROR
                )
            )
        )
        remoteSourceImpl.getComplaint().test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage == actual
            }
            .assertNotComplete()
    }

    //message
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

    @Test
    fun `Update user password success`() {
        val request = TestUserGenerator.getChangePasswordRequest()
        val passwordDto = TestUserGenerator.getChangePassword()
        val actual = true
        with(request) {
            Mockito.`when`(apiService.changeAccountPassword(this))
                .thenReturn(Observable.just(passwordDto))
            remoteSourceImpl.changePassword(oldPassword, newPassword)
                .test()
                .assertSubscribed()
                .assertValueCount(1)
                .assertValue {
                    it == actual
                }
                .assertComplete()
        }
    }

    @Test
    fun `Update user password with no network`() {
        val request = TestUserGenerator.getChangePasswordRequest()
        val actual = "No internet connection"
        with(request) {
            Mockito.`when`(apiService.changeAccountPassword(this))
                .thenReturn(Observable.error(Throwable(NO_NETWORK_ERROR)))
            remoteSourceImpl.changePassword(oldPassword, newPassword)
                .test()
                .assertError {
                    it.localizedMessage == actual
                }
                .assertNotComplete()
        }
    }

    //endregion

    companion object {
        private const val NO_NETWORK_ERROR = "Unable to resolve host"
    }
}