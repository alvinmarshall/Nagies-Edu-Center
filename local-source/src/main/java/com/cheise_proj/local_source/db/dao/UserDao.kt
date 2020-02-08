package com.cheise_proj.local_source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.cheise_proj.local_source.model.user.ProfileLocal
import com.cheise_proj.local_source.model.user.UserLocal
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert
    fun saveUser(userLocal: UserLocal)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): Single<UserLocal>

    @Query("DELETE FROM users")
    fun deleteUser()

    @Transaction
    fun clearAndInsertUser(userLocal: UserLocal) {
        deleteUser()
        saveUser(userLocal)
    }

    @Insert
    fun saveProfile(profileLocal: ProfileLocal)

    @Query("SELECT * FROM profile WHERE name = :identifier")
    fun getProfile(identifier: String): Single<ProfileLocal>
}