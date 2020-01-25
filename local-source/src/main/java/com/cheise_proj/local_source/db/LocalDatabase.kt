package com.cheise_proj.local_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheise_proj.local_source.db.dao.UserDao
import com.cheise_proj.local_source.model.UserLocal

@Database(entities = [UserLocal::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        val DATABASE_NAME = "local.db"
    }
}

