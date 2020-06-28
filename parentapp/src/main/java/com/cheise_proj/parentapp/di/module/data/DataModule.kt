package com.cheise_proj.parentapp.di.module.data

import com.cheise_proj.data.repository.files.FilesRepositoryImpl
import com.cheise_proj.data.repository.message.MessageRepositoryImpl
import com.cheise_proj.data.repository.people.PeopleRepositoryImpl
import com.cheise_proj.data.repository.user.UserRepositoryImpl
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.repository.PeopleRepository
import com.cheise_proj.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule.Binders::class])
class DataModule {
    @Module
    interface Binders {

        //region PEOPLE
        @Binds
        fun bindPeopleRepository(peopleRepositoryImpl: PeopleRepositoryImpl): PeopleRepository

        //endregion

        //region FILES
        @Binds
        fun bindFilesRepository(filesRepositoryImpl: FilesRepositoryImpl): FilesRepository


        //endregion

        //region MESSAGES
        @Binds
        fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

        //endregion

        //region USERS

        @Binds
        fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

        //endregion
    }

}