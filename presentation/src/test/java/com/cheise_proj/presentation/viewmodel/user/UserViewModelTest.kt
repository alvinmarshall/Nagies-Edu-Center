package com.cheise_proj.presentation.viewmodel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.usecase.users.GetUserTask
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

    @Mock
    private lateinit var userRepository: UserRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserTask = GetUserTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        userEntityMapper = UserEntityMapper()
        userViewModel = UserViewModel(getUserTask, userEntityMapper)
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
}