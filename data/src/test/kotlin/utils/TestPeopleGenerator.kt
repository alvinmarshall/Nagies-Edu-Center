package utils

import com.cheise_proj.data.model.people.PeopleData


object TestPeopleGenerator {
    fun getPeople():List<PeopleData>{
        return arrayListOf(
            PeopleData(
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