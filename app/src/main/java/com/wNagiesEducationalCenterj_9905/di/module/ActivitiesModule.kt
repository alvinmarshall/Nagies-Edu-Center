package com.wNagiesEducationalCenterj_9905.di.module

import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.auth.AuthActivity
import com.cheise_proj.login_feature.ui.role.RoleActivity
import com.cheise_proj.login_feature.ui.splash.SplashActivity
import com.cheise_proj.parent_feature.ParentNavigationActivity
import com.cheise_proj.teacher_feature.TeacherNavigationActivity
import com.wNagiesEducationalCenterj_9905.di.module.login.LoginFeatureModule
import com.wNagiesEducationalCenterj_9905.di.module.parent.ParentFeatureModule
import com.wNagiesEducationalCenterj_9905.di.module.teacher.TeacherFeatureModule
import com.wNagiesEducationalCenterj_9905.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideRoleActivity(): RoleActivity

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [ParentFeatureModule::class])
    @ActivityScope
    fun provideParentNavigationActivity(): ParentNavigationActivity

    @ContributesAndroidInjector(modules = [TeacherFeatureModule::class])
    @ActivityScope
    fun provideTeacherNavigationActivity(): TeacherNavigationActivity


}