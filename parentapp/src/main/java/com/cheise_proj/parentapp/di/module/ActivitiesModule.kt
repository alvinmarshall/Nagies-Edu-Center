package com.cheise_proj.parentapp.di.module

import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.auth.AuthActivity
import com.cheise_proj.login_feature.ui.role.RoleActivity
import com.cheise_proj.parent_feature.ParentNavigationActivity
import com.cheise_proj.parentapp.di.module.login.LoginFeatureModule
import com.cheise_proj.parentapp.di.module.parent.ParentFeatureModule
import com.cheise_proj.parentapp.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideRoleActivity(): RoleActivity

    @ContributesAndroidInjector(modules = [LoginFeatureModule::class])
    @LoginScope
    fun provideAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [ParentFeatureModule::class])
    @ActivityScope
    fun provideParentNavigationActivity(): ParentNavigationActivity

}