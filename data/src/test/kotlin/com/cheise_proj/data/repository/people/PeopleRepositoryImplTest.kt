package com.cheise_proj.data.repository.people

import com.cheise_proj.data.mapper.people.PeopleDataEntityMapper
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
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
import utils.TestPeopleGenerator

@RunWith(JUnit4::class)
class PeopleRepositoryImplTest {

    private lateinit var peopleRepositoryImpl: PeopleRepositoryImpl
    private lateinit var peopleDataEntityMapper: PeopleDataEntityMapper

    companion object {
        private const val IDENTIFIER = "1"
        private const val TYPE_TEACHER = "teacher"
    }

    @Mock
    lateinit var remoteSource: RemoteSource
    @Mock
    lateinit var localSource: LocalSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        peopleDataEntityMapper = PeopleDataEntityMapper()
        peopleRepositoryImpl =
            PeopleRepositoryImpl(remoteSource, localSource, peopleDataEntityMapper)
    }

    @Test
    fun `Get people list success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(remoteSource.getPeople(TYPE_TEACHER)).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getPeopleList()).thenReturn(Observable.just(actual))
        peopleRepositoryImpl.getPeopleList(TYPE_TEACHER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it == peopleDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getPeople(TYPE_TEACHER)
        Mockito.verify(localSource, times(1)).getPeopleList()
        Mockito.verify(localSource, times(1)).savePeople(actual)
    }

    @Test
    fun `Get people success`() {
        val actual = TestPeopleGenerator.getPeople()
        Mockito.`when`(localSource.getPeople(IDENTIFIER)).thenReturn(Single.just(actual[0]))
        peopleRepositoryImpl.getPeople(IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it == peopleDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(1)).getPeople(IDENTIFIER)
    }
}