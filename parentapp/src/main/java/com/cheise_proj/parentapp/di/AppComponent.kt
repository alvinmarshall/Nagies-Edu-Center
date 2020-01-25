package com.cheise_proj.parentapp.di

import android.app.Application
import com.cheise_proj.parentapp.ParentApp
import com.cheise_proj.parentapp.di.module.ActivitiesModule
import com.cheise_proj.parentapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivitiesModule::class])
interface AppComponent : AndroidInjector<ParentApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: ParentApp?)
}