package com.cheise_proj.parentapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.cheise_proj.parentapp.di.module.data.DataModule
import com.cheise_proj.parentapp.di.module.domain.DomainModule
import com.cheise_proj.parentapp.di.module.local.LocalModule
import com.cheise_proj.parentapp.di.module.presentation.PresentationModule
import com.cheise_proj.parentapp.di.module.remote.RemoteModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DomainModule::class, PresentationModule::class, DataModule::class, RemoteModule::class, LocalModule::class])
class AppModule {
    @Provides
    fun provideContext(application: Application): Context = application.baseContext

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}