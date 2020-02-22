package com.wNagiesEducationalCenterj_9905.di.module.teacher

import com.cheise_proj.presentation.utils.IDownloadFile
import com.cheise_proj.teacher_feature.TeacherNavigation
import com.cheise_proj.teacher_feature.di.TeacherFragmentModule
import com.wNagiesEducationalCenterj_9905.navigators.teacher.TeacherActivityNavigation
import com.wNagiesEducationalCenterj_9905.utils.DownloadFileImpl
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