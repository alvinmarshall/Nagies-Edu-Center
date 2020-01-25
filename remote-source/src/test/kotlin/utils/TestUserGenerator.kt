package utils

import com.cheise_proj.remote_source.model.dto.UserDto

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
}