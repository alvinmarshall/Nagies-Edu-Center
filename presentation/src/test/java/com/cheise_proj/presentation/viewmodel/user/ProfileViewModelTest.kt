package com.cheise_proj.presentation.viewmodel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.usecase.users.GetProfileTask
import com.cheise_proj.presentation.extensions.asEntity
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestUserGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
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
        profileViewModel = ProfileViewModel(getProfileTask)
    }

    @Test
    fun `Get student profile success`() {
        val identifier = "test identifier"
        val profile = TestUserGenerator.getProfile()
        Mockito.`when`(userRepository.getStudentProfile(identifier))
            .thenReturn(Observable.just(profile.asEntity()))
        val liveProfile = profileViewModel.getProfile(PARENT, identifier)
        liveProfile.observeForever { }
        assertTrue(
            liveProfile.value?.status == STATUS.SUCCESS
        )
        assertNotNull(liveProfile.value?.data)
    }

    @Test
    fun `Get teacher profile success`() {
        val identifier = "test identifier"
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(userRepository.getTeacherProfile(identifier))
            .thenReturn(Observable.just(actual.asEntity()))
        val liveProfile = profileViewModel.getProfile(TEACHER, identifier)
        liveProfile.observeForever { }
        assertTrue(
            liveProfile.value?.status == STATUS.SUCCESS
        )
        assertNotNull(liveProfile.value?.data)
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