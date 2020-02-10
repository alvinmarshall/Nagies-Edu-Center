package utils

import com.cheise_proj.remote_source.model.dto.user.*
import com.cheise_proj.remote_source.model.request.ChangePasswordRequest

object TestUserGenerator {
    fun user(): UserDto {
        return UserDto(
            uuid = 1,
            token = "test token",
            role = "test role",
            message = "test message",
            status = 200,
            level = "test level",
            imageUrl = null,
            name = "test name"
        )
    }

    fun getProfile(): ProfileDto {
        return ProfileDto(
            student = Profile1(
                username = "test username",
                name = "test name",
                level = "test level",
                imageUrl = "test image",
                dob = "test dob",
                adminDate = "test adminDate",
                contact = "test contact",
                faculty = "test faculty",
                gender = "test gender",
                guardian = "test guardian",
                refNo = "test refNo",
                section = "test section",
                semester = "test semester"
            ),
            teacher = Profile2(
                username = "test username",
                name = "test name",
                level = "test level",
                imageUrl = "test image",
                dob = "test dob",
                adminDate = "test adminDate",
                contact = "test contact",
                faculty = "test faculty",
                gender = "test gender",
                guardian = "test guardian",
                refNo = "test refNo",
                section = "test section",
                semester = "test semester"
            )
        )
    }

    fun getChangePasswordRequest(): ChangePasswordRequest {
        return ChangePasswordRequest(
            oldPassword = "1234",
            newPassword = "12345",
            confirmPassword = "12345"
        )
    }

    fun getChangePassword(): PasswordDto {
        return PasswordDto(
            message = "updated password successful",
            status = 200
        )
    }
}