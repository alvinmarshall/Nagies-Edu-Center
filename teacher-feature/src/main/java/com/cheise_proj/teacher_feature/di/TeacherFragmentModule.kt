package com.cheise_proj.teacher_feature.di


import com.cheise_proj.teacher_feature.ui.attachment.AttachmentFragment
import com.cheise_proj.teacher_feature.ui.complaint.ComplaintDetailFragment
import com.cheise_proj.teacher_feature.ui.complaint.ComplaintFragment
import com.cheise_proj.teacher_feature.ui.explorer.AssignmentExplorerFragment
import com.cheise_proj.teacher_feature.ui.explorer.ReportExplorerFragment
import com.cheise_proj.teacher_feature.ui.password.PasswordFragment
import com.cheise_proj.teacher_feature.ui.profile.ProfileFragment
import com.cheise_proj.teacher_feature.ui.send_message.PeopleDialogFragment
import com.cheise_proj.teacher_feature.ui.send_message.SendMessageFragment
import com.cheise_proj.teacher_feature.ui.student.StudentFragment
import com.cheise_proj.teacher_feature.ui.video.UploadVideoFragment
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

    @ContributesAndroidInjector
    fun contributeAttachmentFragment(): AttachmentFragment

    @ContributesAndroidInjector
    fun contributeAssignmentExplorerFragment(): AssignmentExplorerFragment

    @ContributesAndroidInjector
    fun contributionReportExplorerFragment(): ReportExplorerFragment

    @ContributesAndroidInjector
    fun contributeSendMessageFragment(): SendMessageFragment

    @ContributesAndroidInjector
    fun contributePeopleDialogFragment(): PeopleDialogFragment

    @ContributesAndroidInjector
    fun contributeUploadVideoFragment():UploadVideoFragment

}
