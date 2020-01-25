package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.TestUserGenerator

@RunWith(JUnit4::class)
class GetUserTaskTest {
    private lateinit var getUserTask: GetUserTask
    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserTask = GetUserTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Authenticate user success`() {
        val user = TestUserGenerator.user()
        Mockito.`when`(userRepository.authenticateUser(user.username, user.password,user.role)).thenReturn(
            Observable.just(user)
        )
        with(user) {
            getUserTask.buildUseCase(getUserTask.UserParams(username, password,role)).test()
                .assertSubscribed()
                .assertValueCount(1)
                .assertValue { t -> t == user }
                .assertComplete()
        }
    }

    @Test
    fun `Authenticate user failed`() {
        val user = TestUserGenerator.user()
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.authenticateUser(user.username, user.password,user.role)).thenReturn(
            Observable.error(Throwable(errorMsg))
        )

        with(user) {
            getUserTask.buildUseCase(
                getUserTask.UserParams(username, password,role)
            ).test()
                .assertSubscribed()
                .assertError {
                    it.localizedMessage?.equals(errorMsg) ?: false
                }
                .assertNotComplete()
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Authenticate user with null params throws exception`() {
        getUserTask.buildUseCase().test()
    }

}