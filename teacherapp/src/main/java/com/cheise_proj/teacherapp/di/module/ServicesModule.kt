package com.cheise_proj.teacherapp.di.module

import com.cheise_proj.teacher_feature.notification.FirebaseTeacherMessageService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ServicesModule {
    @ContributesAndroidInjector
    fun contributeFirebaseTeacherService(): FirebaseTeacherMessageService
}