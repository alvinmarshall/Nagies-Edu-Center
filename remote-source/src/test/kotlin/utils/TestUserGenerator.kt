package utils

import com.cheise_proj.remote_source.model.dto.user.Profile1
import com.cheise_proj.remote_source.model.dto.user.Profile2
import com.cheise_proj.remote_source.model.dto.user.ProfileDto
import com.cheise_proj.remote_source.model.dto.user.UserDto

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
}