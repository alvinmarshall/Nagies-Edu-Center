package com.cheise_proj.teacherapp.di.module.local

import android.app.Application
import androidx.room.Room
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.LocalSourceImpl
import com.cheise_proj.local_source.db.LocalDatabase
import com.cheise_proj.local_source.db.LocalDatabase.Companion.DATABASE_NAME
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.PeopleDao
import com.cheise_proj.local_source.db.dao.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalModule.Binders::class])
class LocalModule {
    @Module
    interface Binders {

        @Binds
        fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource

    }

    @Singleton
    @Provides
    fun provideRoomDb(application: Application): LocalDatabase {
        return Room
            .databaseBuilder(
                application.applicationContext,
                LocalDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(localDatabase: LocalDatabase): UserDao = localDatabase.userDao()

    @Singleton
    @Provides
    fun provideMessageDao(localDatabase: LocalDatabase): MessageDao = localDatabase.messageDao()

    @Singleton
    @Provides
    fun provideFilesDao(localDatabase: LocalDatabase): FilesDao = localDatabase.filesDao()

    @Singleton
    @Provides
    fun providePeopleDao(localDatabase: LocalDatabase): PeopleDao = localDatabase.peopleDao()

}