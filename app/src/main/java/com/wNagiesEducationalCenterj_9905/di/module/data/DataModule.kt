package com.wNagiesEducationalCenterj_9905.di.module.data

import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.mapper.files.FilesDataEntityMapper
import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.mapper.user.ProfileDataEntityMapper
import com.cheise_proj.data.mapper.user.UserDataEntityMapper
import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.repository.files.FilesRepositoryImpl
import com.cheise_proj.data.repository.message.MessageRepositoryImpl
import com.cheise_proj.data.repository.user.UserRepositoryImpl
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule.Binders::class])
class DataModule {
    @Module
    interface Binders {

        //region FILES
        @Binds
        fun bindFilesRepository(filesRepositoryImpl: FilesRepositoryImpl): FilesRepository

        @Binds
        fun bindFileDataEntityMapper(filesDataEntityMapper: FilesDataEntityMapper): DataMapper<FilesData, FilesEntity>

        //endregion

        //region MESSAGES
        @Binds
        fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

        //endregion
        @Binds
        fun bindMessageDataEntityMapper(messageDataEntityMapper: MessageDataEntityMapper): DataMapper<MessageData, MessageEntity>

        //region USERS
        @Binds
        fun bindUserDataEntityMapper(userDataEntityMapper: UserDataEntityMapper):
                DataMapper<UserData, UserEntity>

        @Binds
        fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

        @Binds
        fun bindProfileDataEntityMapper(profileDataEntityMapper: ProfileDataEntityMapper):
                DataMapper<ProfileData, ProfileEntity>
        //endregion
    }

}