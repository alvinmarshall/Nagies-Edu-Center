package com.cheise_proj.parentapp.di.module.domain

import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Background
    fun provideBackgroundScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Foreground
    fun provideForegroundScheduler(): Scheduler = AndroidSchedulers.mainThread()


}
