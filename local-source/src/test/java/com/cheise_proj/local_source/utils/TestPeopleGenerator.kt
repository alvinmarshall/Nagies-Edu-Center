package com.cheise_proj.local_source.utils

import com.cheise_proj.local_source.model.people.PeopleLocal


object TestPeopleGenerator {
    fun getPeople(): List<PeopleLocal> {
        return arrayListOf(
            PeopleLocal(
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