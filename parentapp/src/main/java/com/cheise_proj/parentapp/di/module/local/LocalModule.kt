package com.cheise_proj.parentapp.di.module.local

import android.app.Application
import androidx.room.Room
import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.LocalSourceImpl
import com.cheise_proj.local_source.db.LocalDatabase
import com.cheise_proj.local_source.db.LocalDatabase.Companion.DATABASE_NAME
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.mapper.files.AssignmentLocalDataMapper
import com.cheise_proj.local_source.mapper.files.CircularLocalDataMapper
import com.cheise_proj.local_source.mapper.files.ReportLocalDataMapper
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.files.AssignmentLocal
import com.cheise_proj.local_source.model.files.CircularLocal
import com.cheise_proj.local_source.model.files.ReportLocal
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
        //region FILES
        @Binds
        fun bindReportLocalDataMapper(reportLocalDataMapper: ReportLocalDataMapper): LocalMapper<ReportLocal, FilesData>

        @Binds
        fun bindAssignmentLocalDataMapper(assignmentLocalDataMapper: AssignmentLocalDataMapper): LocalMapper<AssignmentLocal, FilesData>

        @Binds
        fun bindCircularLocalDataMapper(circularLocalDataMapper: CircularLocalDataMapper): LocalMapper<CircularLocal, FilesData>
        //endregion

        @Binds
        fun bindUserLocalDataMapper(userLocalDataMapper: UserLocalDataMapper): LocalMapper<UserLocal, UserData>

        //region MESSAGES
        @Binds
        fun bindMessageLocalDataMapper(messageLocalDataMapper: MessageLocalDataMapper): LocalMapper<MessageLocal, MessageData>
        //endregion

        //region USERS
        @Binds
        fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource

        @Binds
        fun bindProfileLocalDataMapper(profileLocalDataMapper: ProfileLocalDataMapper):
                LocalMapper<ProfileLocal, ProfileData>
        //endregion

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

}