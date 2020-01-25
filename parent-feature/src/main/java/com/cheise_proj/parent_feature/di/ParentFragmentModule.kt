package com.cheise_proj.parent_feature.di

import com.cheise_proj.parent_feature.ui.announcement.AnnouncementFragment
import com.cheise_proj.parent_feature.ui.bill.BillFragment
import com.cheise_proj.parent_feature.ui.circular.CircularFragment
import com.cheise_proj.parent_feature.ui.complaint.SendComplaintFragment
import com.cheise_proj.parent_feature.ui.message.MessageFragment
import com.cheise_proj.parent_feature.ui.password.PasswordFragment
import com.cheise_proj.parent_feature.ui.profile.ProfileFragment
import com.cheise_proj.parent_feature.ui.report.ReportFragment
import com.cheise_proj.parent_feature.ui.teacher.TeacherFragment
import com.cheise_proj.parent_feature.ui.timetable.TimeTableFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ParentFragmentModule {
    @ContributesAndroidInjector
    fun contributeAnnouncementFragment(): AnnouncementFragment

    @ContributesAndroidInjector
    fun contributeBillFragment(): BillFragment

    @ContributesAndroidInjector
    fun contributeCircularFragment(): CircularFragment

    @ContributesAndroidInjector
    fun contributeMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    fun contributePasswordtFragment(): PasswordFragment

    @ContributesAndroidInjector
    fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun contributeTeacherFragment(): TeacherFragment

    @ContributesAndroidInjector
    fun contributeReportFragment(): ReportFragment

    @ContributesAndroidInjector
    fun contributeTimeTableFragment(): TimeTableFragment

    @ContributesAndroidInjector
    fun contributeSendComplaintFragment(): SendComplaintFragment
}
