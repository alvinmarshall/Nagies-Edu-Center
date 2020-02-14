package com.cheise_proj.teacher_feature.di


import com.cheise_proj.teacher_feature.ui.profile.ProfileFragment
import com.cheise_proj.teacher_feature.ui.student.StudentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TeacherFragmentModule {
    @ContributesAndroidInjector
    fun contributeProfileFragment():ProfileFragment

    @ContributesAndroidInjector
    fun contributeStudentFragment():StudentFragment

}
