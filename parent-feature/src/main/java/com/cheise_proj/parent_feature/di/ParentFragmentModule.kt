package com.cheise_proj.parent_feature.di

import com.cheise_proj.parent_feature.ui.assignment.AssignmentFragment
import com.cheise_proj.parent_feature.ui.bill.BillFragment
import com.cheise_proj.parent_feature.ui.circular.CircularFragment
import com.cheise_proj.parent_feature.ui.compose.ComposeComplaintFragment
import com.cheise_proj.parent_feature.ui.compose.PeopleDialogFragment
import com.cheise_proj.parent_feature.ui.message.MessageDetailFragment
import com.cheise_proj.parent_feature.ui.message.MessageFragment
import com.cheise_proj.parent_feature.ui.password.PasswordFragment
import com.cheise_proj.parent_feature.ui.profile.ProfileFragment
import com.cheise_proj.parent_feature.ui.receipt.ReceiptFragment
import com.cheise_proj.parent_feature.ui.report.ReportFragment
import com.cheise_proj.parent_feature.ui.teacher.TeacherFragment
import com.cheise_proj.parent_feature.ui.timetable.TimeTableFragment
import com.cheise_proj.parent_feature.ui.video.VideoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ParentFragmentModule {

    @ContributesAndroidInjector
    fun contributeBillFragment(): BillFragment

    @ContributesAndroidInjector
    fun contributeCircularFragment(): CircularFragment

    @ContributesAndroidInjector
    fun contributeMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    fun contributePasswordFragment(): PasswordFragment

    @ContributesAndroidInjector
    fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun contributeTeacherFragment(): TeacherFragment

    @ContributesAndroidInjector
    fun contributeReportFragment(): ReportFragment

    @ContributesAndroidInjector
    fun contributeTimeTableFragment(): TimeTableFragment

    @ContributesAndroidInjector
    fun contributeMessageDetailFragment(): MessageDetailFragment

    @ContributesAndroidInjector
    fun contributeAssignmentFragment(): AssignmentFragment

    @ContributesAndroidInjector
    fun contributeReceiptFragment(): ReceiptFragment

    @ContributesAndroidInjector
    fun contributeComposeComplaintFragment(): ComposeComplaintFragment

    @ContributesAndroidInjector
    fun contributePeopleDialogFragment(): PeopleDialogFragment

    @ContributesAndroidInjector
    fun contributeVideoFragment(): VideoFragment
}
