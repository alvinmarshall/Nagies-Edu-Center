package utils

import com.cheise_proj.remote_source.model.dto.files.*

object TestFilesGenerator {

    //region REPORT
    fun getReportsDto(): ReportsDto {
        return ReportsDto(
            data = arrayListOf(
                ReportDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name",
                    path = "",
                    studentName = "",
                    refNo = ""
                ),
                ReportDto(
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
    //endregion


    //region ASSIGNMENT
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
    //endregion

    //region CIRCULAR
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
    //endregion

}