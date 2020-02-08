package com.cheise_proj.local_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.model.files.*
import com.cheise_proj.local_source.model.message.MessageLocal
import com.cheise_proj.local_source.model.user.ProfileLocal
import com.cheise_proj.local_source.model.user.UserLocal

@Database(
    entities = [
        UserLocal::class,
        ProfileLocal::class,
        MessageLocal::class,
        CircularLocal::class,
        AssignmentLocal::class,
        ReportLocal::class,
        TimeTableLocal::class,
        BillLocal::class
    ],
    version = 5,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun filesDao(): FilesDao

    companion object {
        val DATABASE_NAME = "local.db"
    }
}

