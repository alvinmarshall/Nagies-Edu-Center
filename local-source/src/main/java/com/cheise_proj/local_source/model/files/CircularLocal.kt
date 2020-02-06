package com.cheise_proj.local_source.model.files

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "circular")
data class CircularLocal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "cid")
    val teacherName: String,
    var photo: String?,
    val date: String,
    var path: String?
)