package com.cheise_proj.teacherapp.di.module.teacher

import com.cheise_proj.presentation.utils.IDownloadFile
import com.cheise_proj.teacher_feature.TeacherNavigation
import com.cheise_proj.teacher_feature.di.TeacherFragmentModule
import com.cheise_proj.teacherapp.navigators.teacher.TeacherActivityNavigation
import com.cheise_proj.teacherapp.utils.DownloadFileImpl
import dagger.Binds
import dagger.Module

@Module(includes = [TeacherFragmentModule::class, TeacherFeatureModule.Binders::class])
class TeacherFeatureModule {
    @Module
    interface Binders {
        @Binds
        fun bindParentActivityNavigation(teacherActivityNavigation: TeacherActivityNavigation): TeacherNavigation

        @Binds
        fun bindDownloadFileImpl(downloadFileImpl: DownloadFileImpl): IDownloadFile
    }
}