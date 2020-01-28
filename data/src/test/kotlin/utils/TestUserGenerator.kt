package utils

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData

object TestUserGenerator {
    fun user(): UserData {
        return UserData(
            username = "test username",
            name = "test name",
            password = "test password",
            level = "test level",
            role = "test role",
            photo = "test url",
            token = "test token",
            uuid = 0
        )
    }

    fun getProfile():ProfileData{
        return ProfileData(
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