package com.wNagiesEducationalCenterj_9905.di.module.parent

import com.cheise_proj.parent_feature.ParentNavigation
import com.cheise_proj.parent_feature.di.ParentFragmentModule
import com.wNagiesEducationalCenterj_9905.navigators.parent.ParentActivityNavigation
import dagger.Binds
import dagger.Module

@Module(includes = [ParentFragmentModule::class, ParentFeatureModule.Binders::class])
class ParentFeatureModule {
    @Module
    interface Binders {
        @Binds
        fun bindParentActivityNavigation(parentActivityNavigation: ParentActivityNavigation): ParentNavigation
    }
}