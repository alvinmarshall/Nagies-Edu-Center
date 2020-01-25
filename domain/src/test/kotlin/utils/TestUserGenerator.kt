package utils

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
}