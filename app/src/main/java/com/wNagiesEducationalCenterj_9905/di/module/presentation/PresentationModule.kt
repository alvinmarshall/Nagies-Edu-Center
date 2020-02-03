package com.wNagiesEducationalCenterj_9905.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity
import com.wNagiesEducationalCenterj_9905.di.key.ViewModelKey
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.mapper.message.MessageEntityMapper
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.model.user.Profile
import com.cheise_proj.presentation.model.user.User
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import com.wNagiesEducationalCenterj_9905.preference.PreferenceImpl
import com.wNagiesEducationalCenterj_9905.utils.ServerPathUtils
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [PresentationModule.Binders::class])
class PresentationModule {
    @Module
    interface Binders {
        @Binds
        fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

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

        //region MESSAGE
        @Binds
        fun bindMessageEntity(messageEntityMapper: MessageEntityMapper): PresentationMapper<Message, MessageEntity>

        @Binds
        @ViewModelKey(MessageViewModel::class)
        @IntoMap
        fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel
        //endregion

        @Binds
        fun bindPreferenceImpl(preferenceImpl: PreferenceImpl): IPreference

        @Binds
        fun bindServerPathImpl(serverPathUtils: ServerPathUtils): IServerPath
    }

}