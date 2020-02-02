package com.cheise_proj.remote_source.model.dto.user

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("studentProfile")
    val student: Profile1?,
    @SerializedName("teacherProfile")
    val teacher: Profile2
)

data class Profile1(
    @SerializedName("studentNo")
    override val refNo: String,
    @SerializedName("studentName")
    override val name: String,
    override val gender: String,
    @SerializedName("admissionDate")
    override val adminDate: String,
    override val faculty: String,
    override val level: String,
    @SerializedName("index")
    override val username: String,
    override val contact: String,
    override var imageUrl: String?,
    override val section: String,
    override val semester: String,
    override val guardian: String,
    override val dob: String

) : IProfileDto

data class Profile2(
    @SerializedName("ref")
    override val refNo: String,
    override val name: String,
    override val gender: String,
    @SerializedName("admissionDate")
    override val adminDate: String,
    @SerializedName("facultyName")
    override val faculty: String,
    override val level: String,
    override val username: String,
    override val contact: String,
    override var imageUrl: String?,
    override val section: String,
    override val semester: String,
    override val guardian: String,
    override val dob: String
) : IProfileDto

interface IProfileDto {
    val refNo: String
    val name: String
    val gender: String
    val adminDate: String
    val faculty: String
    val level: String
    val username: String
    val contact: String
    var imageUrl: String?
    val section: String
    val semester: String
    val guardian: String
    val dob: String
}