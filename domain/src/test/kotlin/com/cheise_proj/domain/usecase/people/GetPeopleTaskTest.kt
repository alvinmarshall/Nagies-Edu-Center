package com.cheise_proj.domain.usecase.people

import com.cheise_proj.domain.repository.PeopleRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.TestPeopleGenerator

@RunWith(JUnit4::class)
class GetPeopleTaskTest {

    private lateinit var getPeopleTask: GetPeopleTask

    @Mock
    lateinit var peopleRepository: PeopleRepository

    companion object {
        private const val IDENTIFIER = "1"
        private const val TYPE_STUDENT = "student"
        private const val TYPE_TEACHER = "teacher"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPeopleTask =
            GetPeopleTask(peopleRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get teacher list success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeopleList(TYPE_TEACHER))
            .thenReturn(Observable.just(actual))
        getPeopleTask.buildUseCase(getPeopleTask.Params(TYPE_TEACHER))
            .test()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get teacher success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeople(IDENTIFIER)).thenReturn(Observable.just(actual))
        getPeopleTask.buildUseCase(getPeopleTask.Params(TYPE_TEACHER, IDENTIFIER))
            .test()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get student list success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeopleList(TYPE_STUDENT))
            .thenReturn(Observable.just(actual))
        getPeopleTask.buildUseCase(getPeopleTask.Params(TYPE_STUDENT))
            .test()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test
    fun `Get student success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeople(IDENTIFIER)).thenReturn(Observable.just(actual))
        getPeopleTask.buildUseCase(getPeopleTask.Params(TYPE_STUDENT, IDENTIFIER))
            .test()
            .assertValueCount(1)
            .assertValue {
                it == actual
            }
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get people with no params throws an exception success`() {
        getPeopleTask.buildUseCase()

    }
}