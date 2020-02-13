package com.cheise_proj.presentation.viewmodel.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.PeopleRepository
import com.cheise_proj.domain.usecase.people.GetPeopleTask
import com.cheise_proj.presentation.mapper.people.PeopleEntityMapper
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.utils.TestPeopleGenerator
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
class PeopleViewModelTest {

    private lateinit var peopleViewModel: PeopleViewModel
    private lateinit var peopleEntityMapper: PeopleEntityMapper
    private lateinit var getPeopleTask: GetPeopleTask

    companion object {
        private const val IDENTIFIER = "1"
        private const val TYPE_PARENT = "parent"
        private const val TYPE_TEACHER = "teacher"
    }

    @Mock
    lateinit var peopleRepository: PeopleRepository

    @Mock
    lateinit var path: IServerPath

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPeopleTask =
            GetPeopleTask(peopleRepository, Schedulers.trampoline(), Schedulers.trampoline())
        peopleEntityMapper = PeopleEntityMapper()
        peopleViewModel = PeopleViewModel(getPeopleTask, peopleEntityMapper, path)

    }

    @Test
    fun `Get people list success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeopleList(TYPE_TEACHER))
            .thenReturn(Observable.just(peopleEntityMapper.presentationToEntityList(actual)))
        val peopleLive = peopleViewModel.getPeopleList(TYPE_TEACHER)
        peopleLive.observeForever { }

        assertTrue(
            peopleLive.value?.status == STATUS.SUCCESS && peopleLive.value?.data == actual
        )
    }

    @Test
    fun `Get people success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(peopleRepository.getPeople(IDENTIFIER))
            .thenReturn(Observable.just(peopleEntityMapper.presentationToEntityList(actual)))
        val peopleLive = peopleViewModel.getPeople(TYPE_PARENT, IDENTIFIER)
        peopleLive.observeForever { }

        assertTrue(
            peopleLive.value?.status == STATUS.SUCCESS && peopleLive.value?.data == actual[0]
        )
    }

    @Test
    fun `Get people list with error`() {
        val errorMsg = "An error occurred"
        Mockito.`when`(peopleRepository.getPeopleList(TYPE_TEACHER))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        val peopleLive = peopleViewModel.getPeopleList(TYPE_TEACHER)
        peopleLive.observeForever { }

        assertTrue(
            peopleLive.value?.status == STATUS.ERROR && peopleLive.value?.message == errorMsg
        )
    }

    @Test
    fun `Get people with error`() {
        val errorMsg = "An error occurred"
        Mockito.`when`(peopleRepository.getPeople(IDENTIFIER))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        val peopleLive = peopleViewModel.getPeople(TYPE_PARENT, IDENTIFIER)
        peopleLive.observeForever { }

        assertTrue(
            peopleLive.value?.status == STATUS.ERROR && peopleLive.value?.message == errorMsg
        )
    }
}