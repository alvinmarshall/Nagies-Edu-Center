package com.cheise_proj.local_source.model.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageLocal(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val uid: Int,
    val sender: String,
    val content: String,
    val date: String
)