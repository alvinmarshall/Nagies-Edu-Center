package com.cheise_proj.remote_source.model.dto.people

import com.google.gson.annotations.SerializedName

data class PeopleDto(
    @SerializedName("studentTeachers")
    val teacher: List<TeacherList>?,
    @SerializedName("classStudent")
    val student: List<StudentList>
)

data class TeacherList(
    override val id: Int,
    @SerializedName("uid")
    override val refNo: String,
    @SerializedName("teacherName")
    override val name: String,
    @SerializedName("imageUrl")
    override val photo: String?,
    override var username: String?,
    override val gender: String,
    override val contact: String?
) : IPeopleDto

data class StudentList(
    override val id: Int,
    @SerializedName("studentNo")
    override val refNo: String,
    @SerializedName("studentName")
    override val name: String,
    @SerializedName("imageUrl")
    override val photo: String?,
    @SerializedName("indexNo")
    override var username: String?,
    override val gender: String,
    override val contact: String?
) : IPeopleDto