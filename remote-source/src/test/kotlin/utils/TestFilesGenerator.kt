package utils

import com.cheise_proj.remote_source.model.dto.files.AssignmentDto
import com.cheise_proj.remote_source.model.dto.files.AssignmentsDto
import com.cheise_proj.remote_source.model.dto.files.CircularDto
import com.cheise_proj.remote_source.model.dto.files.CircularsDto

object TestFilesGenerator {
    fun getCircularDto(): CircularsDto {
        return CircularsDto(
            data = arrayListOf(
                CircularDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name"
                ),
                CircularDto(
                    id = 2,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name"
                )
            )

        )
    }

    fun getAssignmentDto(): AssignmentsDto {
        return AssignmentsDto(
            data = arrayListOf(
                AssignmentDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name",
                    path = "",
                    studentName = "",
                    refNo = ""
                ),
                AssignmentDto(
                    id = 2,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name",
                    path = "",
                    studentName = "",
                    refNo = ""
                )
            )

        )
    }
}