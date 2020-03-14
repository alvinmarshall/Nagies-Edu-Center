package com.cheise_proj.teacherapp.di

import android.app.Application
import com.cheise_proj.teacherapp.TeacherApp
import com.cheise_proj.teacherapp.di.module.ActivitiesModule
import com.cheise_proj.teacherapp.di.module.AppModule
import com.cheise_proj.teacherapp.di.module.ServicesModule
import com.cheise_proj.teacherapp.di.module.workmanager.WorkerSubComponent
import com.cheise_proj.teacherapp.job.WorkerFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivitiesModule::class, ServicesModule::class])
interface AppComponent : AndroidInjector<TeacherApp> {

    fun workerFactory(): WorkerFactory
    fun workerSubComponentBuilder(): WorkerSubComponent.Builder


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: TeacherApp?)
}