package com.cheise_proj.presentation.viewmodel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.usecase.users.GetProfileTask
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestUserGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProfileViewModelTest {
    companion object {
        const val PARENT = "Parent"
        const val TEACHER = "Teacher"
    }

    private lateinit var getProfileTask: GetProfileTask
    private lateinit var profileEntityMapper: ProfileEntityMapper
    private lateinit var profileViewModel: ProfileViewModel
    @Mock
    lateinit var userRepository: UserRepository

    @get:Rule
    val role = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProfileTask =
            GetProfileTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        profileEntityMapper = ProfileEntityMapper()
        profileViewModel = ProfileViewModel(getProfileTask, profileEntityMapper)
    }

    @Test
    fun `Get student profile success`() {
        val identifier = "test identifier"
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(userRepository.getStudentProfile(identifier))
            .thenReturn(Observable.just(profileEntityMapper.presentationToEntity(actual)))
        val liveProfile = profileViewModel.getProfile(PARENT, identifier)
        liveProfile.observeForever { }
        assertTrue(
            liveProfile.value?.status == STATUS.SUCCESS && liveProfile.value?.data == actual
        )
    }

    @Test
    fun `Get teacher profile success`() {
        val identifier = "test identifier"
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(userRepository.getTeacherProfile(identifier))
            .thenReturn(Observable.just(profileEntityMapper.presentationToEntity(actual)))
        val liveProfile = profileViewModel.getProfile(TEACHER, identifier)
        liveProfile.observeForever { }
        assertTrue(
            liveProfile.value?.status == STATUS.SUCCESS && liveProfile.value?.data == actual
        )
    }

    @Test
    fun `Get profile failed`() {
        val identifier = "test identifier"
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.getTeacherProfile(identifier))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        val liveProfile = profileViewModel.getProfile(TEACHER, identifier)
        liveProfile.observeForever { }
        assertTrue(
            liveProfile.value?.status == STATUS.ERROR && liveProfile.value?.message == errorMsg
        )
    }
}