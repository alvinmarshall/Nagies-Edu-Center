package utils

import com.cheise_proj.domain.entity.people.PeopleEntity

object TestPeopleGenerator {
    fun getPeople():List<PeopleEntity>{
        return arrayListOf(
            PeopleEntity(
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