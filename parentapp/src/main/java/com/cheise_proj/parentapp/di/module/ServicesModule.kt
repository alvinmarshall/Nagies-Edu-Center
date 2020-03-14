package com.cheise_proj.parentapp.di.module

import com.cheise_proj.parent_feature.notification.FirebaseParentMessageService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ServicesModule {
    @ContributesAndroidInjector
    fun contributeFirebaseParentService(): FirebaseParentMessageService
}