package com.cheise_proj.teacher_feature.di


import com.cheise_proj.teacher_feature.ui.complaint.ComplaintDetailFragment
import com.cheise_proj.teacher_feature.ui.complaint.ComplaintFragment
import com.cheise_proj.teacher_feature.ui.password.PasswordFragment
import com.cheise_proj.teacher_feature.ui.profile.ProfileFragment
import com.cheise_proj.teacher_feature.ui.student.StudentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TeacherFragmentModule {
    @ContributesAndroidInjector
    fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun contributeStudentFragment(): StudentFragment

    @ContributesAndroidInjector
    fun contributePasswordFragment(): PasswordFragment

    @ContributesAndroidInjector
    fun contributeComplaintFragment(): ComplaintFragment

    @ContributesAndroidInjector
    fun contributeComplaintDetailFragment(): ComplaintDetailFragment

}
