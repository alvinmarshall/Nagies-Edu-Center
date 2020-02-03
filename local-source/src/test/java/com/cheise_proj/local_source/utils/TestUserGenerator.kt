package com.cheise_proj.local_source.utils

import com.cheise_proj.local_source.model.user.ProfileLocal
import com.cheise_proj.local_source.model.user.UserLocal

object TestUserGenerator {
    fun user(): UserLocal {
        return UserLocal(
            username = "test username",
            name = "test name",
            password = "test password",
            level = "test level",
            role = "test role",
            photo = "test url",
            token = "test token"
        )
    }

    fun getProfile(): ProfileLocal {
        val local = ProfileLocal(
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
        local.id = 1
        return local

    }
}