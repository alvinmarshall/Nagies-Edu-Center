package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Observable
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
class GetProfileTaskTest {
    companion object {
        const val PARENT = "Parent"
        const val TEACHER = "teacher"
    }

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var getProfileTask: GetProfileTask

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProfileTask =
            GetProfileTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get parent profile success`() {
        val actual = TestUserGenerator.getProfile()
        val params = getProfileTask.ProfileParams(PARENT,"test parent")
        Mockito.`when`(userRepository.getStudentProfile(params.identifier)).thenReturn(Observable.just(actual))
        getProfileTask.buildUseCase(params).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get teacher profile success`() {
        val params = getProfileTask.ProfileParams(TEACHER,"test teacher")
        val actual = TestUserGenerator.getProfile()
        Mockito.`when`(userRepository.getTeacherProfile(params.identifier)).thenReturn(Observable.just(actual))
        getProfileTask.buildUseCase(params).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get profile with unknown role throws an exception`() {
        val params = getProfileTask.ProfileParams("unknown","test")
        getProfileTask.buildUseCase(params).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get profile with null params throws an exception`() {
        getProfileTask.buildUseCase().test()
    }
}