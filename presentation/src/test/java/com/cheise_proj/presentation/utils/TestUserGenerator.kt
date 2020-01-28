package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.user.Profile
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

    fun getProfile():Profile{
        return Profile(
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