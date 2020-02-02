package com.cheise_proj.local_source.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileLocal(
    val refNo: String,
    val name: String,
    val gender: String,
    val adminDate: String,
    val faculty: String,
    val level: String,
    val username: String,
    val contact: String,
    var imageUrl: String?,
    val section: String,
    val semester: String,
    val guardian: String,
    val dob: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}