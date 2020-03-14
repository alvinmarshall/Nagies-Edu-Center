package com.cheise_proj.local_source.model.files

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable")
data class TimeTableLocal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val teacherName: String,
    var photo: String?,
    val date: String,
    var path: String?,
    val refNo: String,
    val studentName: String
)