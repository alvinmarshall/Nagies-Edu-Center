package com.cheise_proj.parentapp

import androidx.work.Configuration
import androidx.work.WorkManager
import com.cheise_proj.parentapp.di.DaggerAppComponent
import com.cheise_proj.presentation.factory.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class ParentApp : DaggerApplication() {
    private val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
    }

    private fun initWorkManager() {
        WorkManager.initialize(this, Configuration.Builder().run {
            setWorkerFactory(appComponent.workerFactory())
                .build()
        })
    }

}