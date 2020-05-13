package com.wNagiesEducationalCenterj_9905.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.parent_feature.notification.CreateParentNotification
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.notification.IParentNotification
import com.cheise_proj.presentation.notification.ITeacherNotification
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.utils.InputValidation
import com.cheise_proj.presentation.viewmodel.files.*
import com.cheise_proj.presentation.viewmodel.message.ComplaintViewModel
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import com.cheise_proj.presentation.viewmodel.people.PeopleViewModel
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import com.cheise_proj.teacher_feature.notification.CreateTeacherTeacherNotification
import com.wNagiesEducationalCenterj_9905.di.key.ViewModelKey
import com.wNagiesEducationalCenterj_9905.preference.PreferenceImpl
import com.wNagiesEducationalCenterj_9905.utils.ServerPathUtils
import com.wNagiesEducationalCenterj_9905.validation.InputValidationImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [PresentationModule.Binders::class])
class PresentationModule {
    @Module
    interface Binders {

        @Binds
        fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


        @Binds
        @ViewModelKey(PeopleViewModel::class)
        @IntoMap
        fun bindPeopleViewModel(peopleViewModel: PeopleViewModel): ViewModel

        @Binds
        @ViewModelKey(VideoViewModel::class)
        @IntoMap
        fun bindVideoViewModel(videoViewModel: VideoViewModel): ViewModel


        @Binds
        @ViewModelKey(BillViewModel::class)
        @IntoMap
        fun bindBillViewModel(billViewModel: BillViewModel): ViewModel

        @Binds
        @ViewModelKey(TimeTableViewModel::class)
        @IntoMap
        fun bindTimeTableViewModel(timeTableViewModel: TimeTableViewModel): ViewModel

        @Binds
        @ViewModelKey(ReportViewModel::class)
        @IntoMap
        fun bindReportViewModel(reportViewModel: ReportViewModel): ViewModel

        @Binds
        @ViewModelKey(AssignmentViewModel::class)
        @IntoMap
        fun bindAssignmentViewModel(assignmentViewModel: AssignmentViewModel): ViewModel

        @Binds
        @ViewModelKey(CircularViewModel::class)
        @IntoMap
        fun bindCircularViewModel(circularViewModel: CircularViewModel): ViewModel
        //endregion

        //region MESSAGE

        @Binds
        @ViewModelKey(ComplaintViewModel::class)
        @IntoMap
        fun bindComplaintViewModel(complaintViewModel: ComplaintViewModel): ViewModel

        @Binds
        @ViewModelKey(MessageViewModel::class)
        @IntoMap
        fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel
        //endregion

        //region USER
        @Binds
        @ViewModelKey(UserViewModel::class)
        @IntoMap
        fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

        @Binds
        @ViewModelKey(ProfileViewModel::class)
        @IntoMap
        fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

        //endregion


        @Binds
        fun bindPreferenceImpl(preferenceImpl: PreferenceImpl): IPreference

        @Binds
        fun bindServerPathImpl(serverPathUtils: ServerPathUtils): IServerPath

        @Binds
        fun bindInputValidation(inputValidationImpl: InputValidationImpl): InputValidation

        //region NOTIFICATION
        @Binds
        fun createTeacherNotification(createTeacherNotification: CreateTeacherTeacherNotification): ITeacherNotification

        @Binds
        fun createParentNotification(createParentNotification: CreateParentNotification): IParentNotification
        //endregion
    }
}