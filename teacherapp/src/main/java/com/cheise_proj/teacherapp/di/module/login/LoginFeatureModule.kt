package com.cheise_proj.teacherapp.di.module.login

import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.RoleNavigation
import com.cheise_proj.login_feature.SplashNavigation
import com.cheise_proj.teacherapp.navigators.login.AuthActivityNavigation
import com.cheise_proj.teacherapp.navigators.login.RoleActivityNavigation
import com.cheise_proj.teacherapp.navigators.login.SplashNavigationImpl
import dagger.Binds
import dagger.Module

@Module(includes = [LoginFeatureModule.Binders::class])
class LoginFeatureModule {
    @Module
    interface Binders {
        @Binds
        fun bindSplashActivityNavigation(splashNavigation: SplashNavigationImpl): SplashNavigation

        @Binds
        fun bindRoleActivityNavigation(roleActivityNavigation: RoleActivityNavigation): RoleNavigation

        @Binds
        fun bindAuthActivitNavigation(authActivityNavigation: AuthActivityNavigation): AuthNavigation
    }


}