package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException


@RunWith(JUnit4::class)
class UpdatePasswordTaskTest {

    private lateinit var updatePasswordTask: UpdatePasswordTask

    @Mock
    lateinit var userRepository: UserRepository

    companion object {
        private const val updatePasswordMessage: Boolean = true
        private const val oldPassword: String = "1234"
        private const val newPassword: String = "12345"
        private const val identifier: String = "1"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        updatePasswordTask =
            UpdatePasswordTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Update user password success`() {
        val actual = updatePasswordMessage
        Mockito.`when`(userRepository.changePassword(identifier, oldPassword, newPassword))
            .thenReturn(
                Observable.just(actual)
            )
        updatePasswordTask.buildUseCase(
            updatePasswordTask.Params(
                identifier,
                oldPassword,
                newPassword
            )
        )
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue { it == actual }
            .assertComplete()
        Mockito.verify(userRepository, times(1))
            .changePassword(identifier, oldPassword, newPassword)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Update user password with no params throws an exception`() {
        updatePasswordTask.buildUseCase()
    }

    @Test
    fun `Update user password failed`() {
        val actual = "An error occurred"
        Mockito.`when`(userRepository.changePassword(identifier, oldPassword, newPassword))
            .thenReturn(
                Observable.error(Throwable(actual))
            )
        updatePasswordTask.buildUseCase(
            updatePasswordTask.Params(
                identifier,
                oldPassword,
                newPassword
            )
        )
            .test()
            .assertSubscribed()
            .assertError { it.localizedMessage == actual }
            .assertNotComplete()
        Mockito.verify(userRepository, times(1))
            .changePassword(identifier, oldPassword, newPassword)
    }
}