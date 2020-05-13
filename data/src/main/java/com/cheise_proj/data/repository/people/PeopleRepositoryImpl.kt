package com.cheise_proj.data.repository.people

import com.cheise_proj.data.cache.PeopleCache
import com.cheise_proj.data.extensions.asDataList
import com.cheise_proj.data.extensions.asEntity
import com.cheise_proj.data.extensions.asEntityList
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
    private val localSource: LocalSource
) : PeopleRepository {

    override fun getPeopleList(type: String): Observable<List<PeopleEntity>> {
        val peopleObservable: Observable<List<PeopleEntity>>
        val cachePeople = PeopleCache.getPeople()

        val local = localSource.getPeopleList()
            .map { t: List<PeopleData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getPeople(type)
            .map { t: List<PeopleData> ->
                localSource.savePeople(t)
               t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        peopleObservable = if (cachePeople != null) {
            println("Remote source NOT invoked")
            val cache = cachePeople.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return peopleObservable
            .map { t: List<PeopleEntity> ->
                if (cachePeople == null) {
                    PeopleCache.addPeople(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()

    }

    override fun getPeople(identifier: String): Observable<List<PeopleEntity>> {
        return localSource.getPeople(identifier).toObservable()
            .map { t: PeopleData ->
                val data = t.asEntity()
                return@map arrayListOf(data)
            }
    }
}