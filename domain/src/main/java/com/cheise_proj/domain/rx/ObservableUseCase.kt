package com.cheise_proj.domain.rx

import com.cheise_proj.domain.usecase.base.BaseUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

abstract class ObservableUseCase<T, in Params>(
    backgroundScheduler: Scheduler,
    foregroundScheduler: Scheduler
) :
    BaseUseCase(backgroundScheduler, foregroundScheduler) {
    protected abstract fun generateSingle(input: Params?): Observable<T>

    fun buildUseCase(input: Params? = null): Observable<T> = generateSingle(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)

}