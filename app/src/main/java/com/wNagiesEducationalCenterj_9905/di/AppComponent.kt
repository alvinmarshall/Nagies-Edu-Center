package com.wNagiesEducationalCenterj_9905.di

import android.app.Application
import com.wNagiesEducationalCenterj_9905.App
import com.wNagiesEducationalCenterj_9905.di.module.ActivitiesModule
import com.wNagiesEducationalCenterj_9905.di.module.AppModule
import com.wNagiesEducationalCenterj_9905.di.module.workmanager.WorkerSubComponent
import com.wNagiesEducationalCenterj_9905.job.WorkerFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivitiesModule::class])
interface AppComponent : AndroidInjector<App> {

    fun workerFactory(): WorkerFactory
    fun workerSubComponentBuilder(): WorkerSubComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: App?)
}