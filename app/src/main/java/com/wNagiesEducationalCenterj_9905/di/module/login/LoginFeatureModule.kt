package com.wNagiesEducationalCenterj_9905.di.module.login

import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.RoleNavigation
import com.cheise_proj.login_feature.SplashNavigation
import com.cheise_proj.login_feature.utils.LoginInputValidation
import com.wNagiesEducationalCenterj_9905.navigators.login.AuthActivityNavigation
import com.wNagiesEducationalCenterj_9905.navigators.login.RoleActivityNavigation
import com.wNagiesEducationalCenterj_9905.navigators.login.SplashNavigationImpl
import com.wNagiesEducationalCenterj_9905.validation.LoginInputValidationImpl
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

        @Binds
        fun bindLoginInputValidation(loginInputValidationImpl: LoginInputValidationImpl): LoginInputValidation
    }


}