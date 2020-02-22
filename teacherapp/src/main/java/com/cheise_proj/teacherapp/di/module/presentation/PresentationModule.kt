package com.cheise_proj.teacherapp.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.entity.people.PeopleEntity
import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.mapper.files.AssignmentEntityMapper
import com.cheise_proj.presentation.mapper.files.ReportEntityMapper
import com.cheise_proj.presentation.mapper.message.ComplaintEntityMapper
import com.cheise_proj.presentation.mapper.message.MessageEntityMapper
import com.cheise_proj.presentation.mapper.people.PeopleEntityMapper
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.files.Assignment
import com.cheise_proj.presentation.model.files.Report
import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.user.Profile
import com.cheise_proj.presentation.model.user.User
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
        fun bindReportEntity(reportEntityMapper: ReportEntityMapper): PresentationMapper<Report, FilesEntity>

        @Binds
        @ViewModelKey(ReportViewModel::class)
        @IntoMap
        fun bindReportViewModel(reportViewModel: ReportViewModel): ViewModel

        @Binds
        fun bindAssignmentEntity(assignmentEntityMapper: AssignmentEntityMapper): PresentationMapper<Assignment, FilesEntity>

        @Binds
        @ViewModelKey(AssignmentViewModel::class)
        @IntoMap
        fun bindAssignmentViewModel(assignmentViewModel: AssignmentViewModel): ViewModel
        //endregion

        //region PEOPLE
        @Binds
        fun bindPeopleEntityMapper(peopleEntityMapper: PeopleEntityMapper): PresentationMapper<People, PeopleEntity>
        //endregion

        @Binds
        fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


        //region MESSAGE
        @Binds
        fun bindComplaintEntity(complaintEntityMapper: ComplaintEntityMapper): PresentationMapper<Complaint, ComplaintEntity>

        @Binds
        fun bindMessageEntity(messageEntityMapper: MessageEntityMapper): PresentationMapper<Message, MessageEntity>

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

        @Binds
        fun bindUserEntityMapper(userEntityMapper: UserEntityMapper): PresentationMapper<User, UserEntity>

        @Binds
        fun bindProfileEntityMapper(profileEntityMapper: ProfileEntityMapper): PresentationMapper<Profile, ProfileEntity>
        //endregion


        @Binds
        fun bindPreferenceImpl(preferenceImpl: PreferenceImpl): IPreference

        @Binds
        fun bindServerPathImpl(serverPathUtils: ServerPathUtils): IServerPath

        @Binds
        fun bindInputValidation(inputValidationImpl: InputValidationImpl): InputValidation
    }

}