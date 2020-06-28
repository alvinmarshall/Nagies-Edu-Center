package com.cheise_proj.teacherapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.teacherapp.di.module.data.DataModule
import com.cheise_proj.teacherapp.di.module.domain.DomainModule
import com.cheise_proj.teacherapp.di.module.local.LocalModule
import com.cheise_proj.teacherapp.di.module.presentation.PresentationModule
import com.cheise_proj.teacherapp.di.module.remote.RemoteModule
import com.cheise_proj.teacherapp.utils.ColorGeneratorImpl
import com.cheise_proj.teacherapp.utils.RuntimePermissionImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        AppModule.Binders::class,
        DomainModule::class,
        PresentationModule::class,
        DataModule::class,
        RemoteModule::class,
        LocalModule::class
    ]
)
class AppModule {
    @Module
    interface Binders {
        @Binds
        fun bindColorGenerator(colorGeneratorImpl: ColorGeneratorImpl): IColorGenerator

        @Binds
        fun bindRuntimePermission(runtimePermissionImpl: RuntimePermissionImpl): IRuntimePermission
    }

    @Provides
    fun provideContext(application: Application): Context = application.baseContext

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}