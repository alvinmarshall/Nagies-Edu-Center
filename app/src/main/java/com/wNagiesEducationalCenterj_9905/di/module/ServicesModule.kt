package com.wNagiesEducationalCenterj_9905.di.module

import com.cheise_proj.parent_feature.notification.FirebaseParentMessageService
import com.cheise_proj.teacher_feature.notification.FirebaseTeacherMessageService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ServicesModule {
    @ContributesAndroidInjector
    fun contributeFirebaseParentService(): FirebaseParentMessageService

    @ContributesAndroidInjector
    fun contributeFirebaseTeacherService(): FirebaseTeacherMessageService
}