package com.cheise_proj.parentapp.di.module.parent

import com.cheise_proj.parent_feature.ParentNavigation
import com.cheise_proj.parent_feature.di.ParentFragmentModule
import com.cheise_proj.parent_feature.ui.profile.ProfileFragment
import com.cheise_proj.parentapp.navigators.parent.ParentActivityNavigation
import com.cheise_proj.parentapp.utils.DownloadFileImpl
import com.cheise_proj.presentation.utils.IDownloadFile
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ParentFragmentModule::class, ParentFeatureModule.Binders::class])
class ParentFeatureModule {
    @Module
    interface Binders {
        @Binds
        fun bindParentActivityNavigation(parentActivityNavigation: ParentActivityNavigation): ParentNavigation

        @Binds
        fun bindDownloadFileImpl(downloadFileImpl: DownloadFileImpl): IDownloadFile
    }
}