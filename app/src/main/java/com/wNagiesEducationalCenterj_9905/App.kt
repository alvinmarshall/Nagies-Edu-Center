package com.wNagiesEducationalCenterj_9905

import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.wNagiesEducationalCenterj_9905.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    private val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
        initFCMService()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initWorkManager() {
        WorkManager.initialize(this, Configuration.Builder().run {
            setWorkerFactory(appComponent.workerFactory())
                .build()
        })
    }

    private fun initFCMService() {
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (!it.isSuccessful) {
                Timber.w("Task Failed")
                return@addOnCompleteListener
            }
            Timber.i("firebaseInstanceID ${it.result?.token}")
        }
        FirebaseMessaging.getInstance().subscribeToTopic(getGlobalTopic()).addOnCompleteListener {
            if (!it.isSuccessful) {
                Timber.w("Task Failed")
                return@addOnCompleteListener
            }
            Timber.i("subscribe global topic")
        }
    }

    private fun getGlobalTopic(): String {
        if (BuildConfig.DEBUG) return getString(R.string.fcm_topic_dev_global)
        return getString(R.string.fcm_topic_global)
    }

}