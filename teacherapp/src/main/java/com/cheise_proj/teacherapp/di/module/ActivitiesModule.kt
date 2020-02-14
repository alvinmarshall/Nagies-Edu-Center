package com.cheise_proj.teacherapp.di.module

import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.auth.AuthActivity
import com.cheise_proj.login_feature.ui.role.RoleActivity
import com.cheise_proj.login_feature.ui.splash.SplashActivity
import com.cheise_proj.teacher_feature.TeacherNavigationActivity
import com.cheise_proj.teacherapp.di.module.login.LoginFeatureModule
import com.cheise_proj.teacherapp.di.module.teacher.TeacherFeatureModule
import com.cheise_proj.teacherapp.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideSplashActivity():SplashActivity

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideRoleActivity(): RoleActivity

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [TeacherFeatureModule::class])
    @ActivityScope
    fun provideTeacherNavigationActivity(): TeacherNavigationActivity

}