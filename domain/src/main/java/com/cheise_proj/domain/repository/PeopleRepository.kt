package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.people.PeopleEntity
import io.reactivex.Observable

interface PeopleRepository {
    fun getPeopleList(type:String): Observable<List<PeopleEntity>>
    fun getPeople(identifier: String): Observable<List<PeopleEntity>>
}