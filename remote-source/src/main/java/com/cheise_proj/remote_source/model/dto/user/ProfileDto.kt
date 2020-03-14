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
    override var refNo: String?,
    @SerializedName("studentName")
    override var name: String?,
    override var gender: String?,
    @SerializedName("admissionDate")
    override var adminDate: String?,
    override var faculty: String?,
    override var level: String?,
    @SerializedName("index")
    override var username: String?,
    override var contact: String?,
    override var imageUrl: String?,
    override var section: String?,
    override var semester: String?,
    override var guardian: String?,
    override var dob: String?

) : IProfileDto

data class Profile2(
    @SerializedName("ref")
    override var refNo: String?,
    override var name: String?,
    override var gender: String?,
    @SerializedName("admissionDate")
    override var adminDate: String?,
    @SerializedName("facultyName")
    override var faculty: String?,
    override var level: String?,
    override var username: String?,
    override var contact: String?,
    override var imageUrl: String?,
    override var section: String?,
    override var semester: String?,
    override var guardian: String?,
    override var dob: String?
) : IProfileDto

interface IProfileDto {
    var refNo: String?
    var name: String?
    var gender: String?
    var adminDate: String?
    var faculty: String?
    var level: String?
    var username: String?
    var contact: String?
    var imageUrl: String?
    var section: String?
    var semester: String?
    var guardian: String?
    var dob: String?
}