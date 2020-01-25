package com.cheise_proj.parentapp.di.module.login

import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.RoleNavigation
import com.cheise_proj.parentapp.navigators.login.AuthActivityNavigation
import com.cheise_proj.parentapp.navigators.login.RoleActivityNavigation
import dagger.Binds
import dagger.Module

@Module
interface LoginFeatureModule {
    @Binds
    fun bindRoleActivityNavigation(roleActivityNavigation: RoleActivityNavigation): RoleNavigation

    @Binds
    fun bindAuthActivitNavigation(authActivityNavigation: AuthActivityNavigation): AuthNavigation
}