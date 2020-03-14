package com.cheise_proj.data.repository.people

import com.cheise_proj.data.cache.PeopleCache
import com.cheise_proj.data.mapper.people.PeopleDataEntityMapper
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.people.PeopleEntity
import com.cheise_proj.domain.repository.PeopleRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val peopleDataEntityMapper: PeopleDataEntityMapper
) : PeopleRepository {

    override fun getPeopleList(type: String): Observable<List<PeopleEntity>> {
        val peopleObservable: Observable<List<PeopleEntity>>
        val cachePeople = PeopleCache.getPeople()

        val local = localSource.getPeopleList()
            .map { t: List<PeopleData> ->
                peopleDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getPeople(type)
            .map { t: List<PeopleData> ->
                localSource.savePeople(t)
                peopleDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        peopleObservable = if (cachePeople != null) {
            println("Remote source NOT invoked")
            val cache = peopleDataEntityMapper.dataToEntityList(cachePeople)
            Observable.just(cache)
        } else {
            remote
        }

        return peopleObservable
            .map { t: List<PeopleEntity> ->
                if (cachePeople == null) {
                    PeopleCache.addPeople(peopleDataEntityMapper.entityToDataList(t))
                }
                return@map t
            }.mergeWith(local).take(1).distinct()

    }

    override fun getPeople(identifier: String): Observable<List<PeopleEntity>> {
        return localSource.getPeople(identifier).toObservable()
            .map { t: PeopleData ->
                val data = peopleDataEntityMapper.dataToEntity(t)
                return@map arrayListOf(data)
            }
    }
}