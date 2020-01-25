package com.cheise_proj.parentapp.di.module.local

import android.app.Application
import androidx.room.Room
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.local_source.LocalSourceImpl
import com.cheise_proj.local_source.db.LocalDatabase
import com.cheise_proj.local_source.db.LocalDatabase.Companion.DATABASE_NAME
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.mapper.LocalMapper
import com.cheise_proj.local_source.mapper.UserLocalDataMapper
import com.cheise_proj.local_source.model.UserLocal
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


}