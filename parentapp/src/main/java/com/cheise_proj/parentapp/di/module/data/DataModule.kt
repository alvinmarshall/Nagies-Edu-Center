package com.cheise_proj.parentapp.di.module.data

import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.mapper.user.UserDataEntityMapper
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.repository.user.UserRepositoryImpl
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule.Binders::class])
class DataModule {
    @Module
    interface Binders {
        @Binds
        fun bindUserDataEntityMapper(userDataEntityMapper: UserDataEntityMapper):
                DataMapper<UserData, UserEntity>

        @Binds
        fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl):UserRepository
    }

}