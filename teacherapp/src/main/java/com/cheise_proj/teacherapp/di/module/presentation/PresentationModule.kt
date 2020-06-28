package com.cheise_proj.teacherapp.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.notification.ITeacherNotification
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.utils.InputValidation
import com.cheise_proj.presentation.viewmodel.files.AssignmentViewModel
import com.cheise_proj.presentation.viewmodel.files.ReportViewModel
import com.cheise_proj.presentation.viewmodel.message.ComplaintViewModel
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import com.cheise_proj.presentation.viewmodel.people.PeopleViewModel
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import com.cheise_proj.teacher_feature.notification.CreateTeacherTeacherNotification
import com.cheise_proj.teacherapp.di.key.ViewModelKey
import com.cheise_proj.teacherapp.preference.PreferenceImpl
import com.cheise_proj.teacherapp.utils.ServerPathUtils
import com.cheise_proj.teacherapp.validation.InputValidationImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [PresentationModule.Binders::class])
class PresentationModule {
    @Module
    interface Binders {

        //region FILES


        @Binds
        @ViewModelKey(ReportViewModel::class)
        @IntoMap
        fun bindReportViewModel(reportViewModel: ReportViewModel): ViewModel


        @Binds
        @ViewModelKey(AssignmentViewModel::class)
        @IntoMap
        fun bindAssignmentViewModel(assignmentViewModel: AssignmentViewModel): ViewModel
        //endregion


        @Binds
        fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


        //region MESSAGE

        @Binds
        @ViewModelKey(PeopleViewModel::class)
        @IntoMap
        fun bindPeopleViewModel(peopleViewModel: PeopleViewModel): ViewModel

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
        //endregion
    }

}