package com.cheise_proj.domain.usecase.people

import com.cheise_proj.domain.entity.people.PeopleEntity
import com.cheise_proj.domain.repository.PeopleRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetPeopleTask @Inject constructor(
    private val peopleRepository: PeopleRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<List<PeopleEntity>, GetPeopleTask.Params>(
        backgroundScheduler,
        foregroundScheduler
    ) {
    override fun generateSingle(input: Params?): Observable<List<PeopleEntity>> {
        if (input == null) throw IllegalArgumentException("peoples params can't be null")
        if (input.identifier != null) return peopleRepository.getPeople(input.identifier!!)
        return peopleRepository.getPeopleList(input.type)
    }

    inner class Params(
        val type: String,
        var identifier: String? = null
    )
}