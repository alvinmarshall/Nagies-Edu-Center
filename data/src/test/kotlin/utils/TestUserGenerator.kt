package utils

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
}