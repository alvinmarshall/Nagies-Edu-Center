package com.cheise_proj.parentapp.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.parentapp.di.key.ViewModelKey
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.user.User
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
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
        @ViewModelKey(UserViewModel::class)
        @IntoMap
        fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

        @Binds
        fun bindUserEntityMapper(userEntityMapper: UserEntityMapper):PresentationMapper<User,UserEntity>
    }

}