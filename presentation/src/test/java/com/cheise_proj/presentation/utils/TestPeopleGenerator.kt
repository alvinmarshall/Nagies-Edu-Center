package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.people.People

object TestPeopleGenerator {
    fun getPeople(): List<People> {
        return arrayListOf(
            People(
                id = 1,
                refNo = "test refNo",
                photo = "test photo",
                name = "test name",
                username = "test username",
                gender = "test gender",
                contact = "test contact"
            )
        )

    }
}