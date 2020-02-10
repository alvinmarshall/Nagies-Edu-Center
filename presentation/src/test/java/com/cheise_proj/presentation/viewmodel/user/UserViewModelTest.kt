package com.cheise_proj.presentation.viewmodel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.usecase.users.GetUserTask
import com.cheise_proj.domain.usecase.users.UpdatePasswordTask
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.TestUserGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
class UserViewModelTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userEntityMapper: UserEntityMapper
    private lateinit var getUserTask: GetUserTask
    private lateinit var updatePasswordTask: UpdatePasswordTask

    @Mock
    private lateinit var userRepository: UserRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserTask = GetUserTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        updatePasswordTask =
            UpdatePasswordTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        userEntityMapper = UserEntityMapper()
        userViewModel = UserViewModel(getUserTask, userEntityMapper, updatePasswordTask)
    }

    @Test
    fun `Authenticate user success`() {
        val user = TestUserGenerator.user()
        with(user) {
            Mockito.`when`(userRepository.authenticateUser(username, password, role)).thenReturn(
                Observable.just(userEntityMapper.presentationToEntity(user))
            )

            val liveUser = userViewModel.authenticateUser(username, password, role)
            liveUser.observeForever { }
            assertTrue(
                liveUser.value?.status == STATUS.SUCCESS && liveUser.value?.data == user
            )
        }
    }

    @Test
    fun `Authenticate user failed`() {
        val user = TestUserGenerator.user()
        val errorMsg = "An error occurred"
        with(user) {
            Mockito.`when`(userRepository.authenticateUser(username, password, role)).thenReturn(
                Observable.error(Throwable(errorMsg))
            )

            val liveUser = userViewModel.authenticateUser(username, password, role)
            liveUser.observeForever { }
            assertTrue(
                liveUser.value?.status == STATUS.ERROR && liveUser.value?.message == errorMsg
            )
        }
    }

    @Test
    fun `Update user password success`() {
        val actual = true
        val identifier = "1"
        val oldPassword = "1234"
        val newPassword = "12345"
        Mockito.`when`(userRepository.changePassword(identifier, oldPassword, newPassword))
            .thenReturn(
                Observable.just(actual)
            )
        val passwordLive = userViewModel.updateAccountPassword(identifier, oldPassword, newPassword)
        passwordLive.observeForever { }
        assertTrue(
            passwordLive.value?.status == STATUS.SUCCESS && passwordLive.value?.data == actual
        )
    }

    @Test
    fun `Update user password failed`() {
        val errorMsg = "An error occurred"
        val identifier = "1"
        val oldPassword = "1234"
        val newPassword = "12345"
        Mockito.`when`(userRepository.changePassword(identifier, oldPassword, newPassword))
            .thenReturn(
                Observable.error(Throwable(errorMsg))
            )
        val passwordLive = userViewModel.updateAccountPassword(identifier, oldPassword, newPassword)
        passwordLive.observeForever { }
        assertTrue(
            passwordLive.value?.status == STATUS.ERROR && passwordLive.value?.message == errorMsg
        )
    }
}