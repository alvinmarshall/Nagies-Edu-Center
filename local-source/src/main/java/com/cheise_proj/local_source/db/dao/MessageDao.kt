package com.cheise_proj.local_source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cheise_proj.local_source.model.message.MessageLocal
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessages(messageList: List<MessageLocal>)

    @Query("SELECT * FROM messages")
    fun getMessages(): Observable<List<MessageLocal>>

    @Query("SELECT * FROM messages WHERE  id = :identifier")
    fun getMessage(identifier:Int): Single<MessageLocal>
}