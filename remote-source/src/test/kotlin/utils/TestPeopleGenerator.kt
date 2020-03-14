package utils

import com.cheise_proj.remote_source.model.dto.people.PeopleDto
import com.cheise_proj.remote_source.model.dto.people.StudentList
import com.cheise_proj.remote_source.model.dto.people.TeacherList

object TestPeopleGenerator {
    fun getPeopleDto(): PeopleDto {
        return PeopleDto(
            teacher = arrayListOf(
                TeacherList(
                    id = 1,
                    contact = "test contact",
                    gender = "test gender",
                    username = "test username",
                    name = "test teacher name",
                    photo = "test photo",
                    refNo = "test refNo"
                )
            ),
            student = arrayListOf(
                StudentList(
                    id = 1,
                    contact = "test contact",
                    gender = "test gender",
                    username = "test username",
                    name = "test student name",
                    photo = "test photo",
                    refNo = "test refNo"
                )
            )
        )
    }
}