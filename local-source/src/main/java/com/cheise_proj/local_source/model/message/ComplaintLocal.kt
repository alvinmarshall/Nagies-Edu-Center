package com.cheise_proj.local_source.model.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complaint")
data class ComplaintLocal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val refNo: String,
    val studentName: String,
    val level: String,
    val contact: String,
    val receiver: String,
    val sender: String,
    val content: String,
    val date: String
)