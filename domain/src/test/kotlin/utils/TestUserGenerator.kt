package utils

import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity

object TestUserGenerator {
    fun user(): UserEntity {
        return UserEntity(
            username = "test username",
            name = "test name",
            password = "test password",
            level = "test level",
            role = "test role",
            photo = "test url",
            uuid = 1,
            token = "test token"
        )
    }

    fun getProfile(): ProfileEntity {
        return ProfileEntity(
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
    }
}