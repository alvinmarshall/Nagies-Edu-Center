package com.cheise_proj.local_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheise_proj.local_source.db.dao.FilesDao
import com.cheise_proj.local_source.db.dao.MessageDao
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.model.files.AssignmentLocal
import com.cheise_proj.local_source.model.files.CircularLocal
import com.cheise_proj.local_source.model.files.ReportLocal
import com.cheise_proj.local_source.model.files.TimeTableLocal
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
        TimeTableLocal::class
    ],
    version = 4,
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

