package com.cheise_proj.local_source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cheise_proj.local_source.model.UserLocal
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert
    fun saveUser(userLocal: UserLocal)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): Single<UserLocal>
}