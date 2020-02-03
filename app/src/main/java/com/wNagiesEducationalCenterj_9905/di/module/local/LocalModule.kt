package com.wNagiesEducationalCenterj_9905.di.module.local

import android.app.Application
import androidx.room.Room
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.LocalSourceImpl
import com.cheise_proj.local_source.db.LocalDatabase
import com.cheise_proj.local_source.db.LocalDatabase.Companion.DATABASE_NAME
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.message.MessageLocal
import com.cheise_proj.local_source.model.user.ProfileLocal
import com.cheise_proj.local_source.model.user.UserLocal
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalModule.Binders::class])
class LocalModule {
    @Module
    interface Binders {
        @Binds
        fun bindUserLocalDataMapper(userLocalDataMapper: UserLocalDataMapper): LocalMapper<UserLocal, UserData>

        @Binds
        fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource

        @Binds
        fun bindProfileLocalDataMapper(profileLocalDataMapper: ProfileLocalDataMapper):
                LocalMapper<ProfileLocal, ProfileData>

        @Binds
        fun bindMessageLocalDataMapper(messageLocalDataMapper: MessageLocalDataMapper): LocalMapper<MessageLocal, MessageData>
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


}