package com.cheise_proj.local_source.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PeopleLocal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val refNo: String,
    val name: String,
    val photo: String?,
    val username: String,
    val gender: String,
    val contact: String?
)