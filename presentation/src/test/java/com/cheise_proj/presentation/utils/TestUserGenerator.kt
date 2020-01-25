package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.user.User

object TestUserGenerator {
    fun user(): User {
        return User(
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