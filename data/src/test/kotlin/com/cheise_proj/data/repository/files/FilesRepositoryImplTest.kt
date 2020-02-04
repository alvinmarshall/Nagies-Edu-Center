package com.cheise_proj.data.repository.files

import com.cheise_proj.data.mapper.files.FilesDataEntityMapper
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import utils.TestFilesGenerator

@RunWith(JUnit4::class)
class FilesRepositoryImplTest {

    private lateinit var filesRepositoryImpl: FilesRepositoryImpl
    private lateinit var filesDataEntityMapper: FilesDataEntityMapper
    @Mock
    lateinit var localSource: LocalSource
    @Mock
    lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        filesDataEntityMapper = FilesDataEntityMapper()
        filesRepositoryImpl = FilesRepositoryImpl(remoteSource, localSource, filesDataEntityMapper)
    }

    @Test
    fun `Get all circulars success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getCircular()).thenReturn(Observable.just(actual))
        Mockito.`when`(localSource.getCirculars()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getCirculars()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getCircular()
        Mockito.verify(localSource, times(1)).getCirculars()
        Mockito.verify(localSource, times(0)).getCircular(CIRCULAR_IDENTIFIER)

    }

    @Test
    fun `Get all circulars from local when remote fail success`() {
        val actual = TestFilesGenerator.getFiles()
        Mockito.`when`(remoteSource.getCircular()).thenReturn(
            Observable.error(
                Throwable(
                    CIRCULAR_ERROR
                )
            )
        )
        Mockito.`when`(localSource.getCirculars()).thenReturn(Observable.just(actual))
        filesRepositoryImpl.getCirculars()
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == filesDataEntityMapper.dataToEntityList(actual)
            }
            .assertComplete()
        Mockito.verify(remoteSource, times(1)).getCircular()
        Mockito.verify(localSource, times(1)).getCirculars()
        Mockito.verify(localSource, times(0)).getCircular(CIRCULAR_IDENTIFIER)

    }

    @Test
    fun `Get circular with identifier success`() {
        val actual = TestFilesGenerator.getFiles()[0]
        Mockito.`when`(localSource.getCircular(CIRCULAR_IDENTIFIER)).thenReturn(Single.just(actual))
        filesRepositoryImpl.getCircular(CIRCULAR_IDENTIFIER)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                println(it)
                it[0] == filesDataEntityMapper.dataToEntity(actual)
            }
            .assertComplete()
        Mockito.verify(localSource, times(0)).getCirculars()
        Mockito.verify(localSource, times(1)).getCircular(CIRCULAR_IDENTIFIER)
    }

    companion object {
        private const val CIRCULAR_IDENTIFIER = "1"
        private const val CIRCULAR_ERROR = "Unable to ping server"
    }
}