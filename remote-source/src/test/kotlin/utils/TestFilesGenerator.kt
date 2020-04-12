package utils

import com.cheise_proj.remote_source.model.dto.files.*
import okhttp3.MultipartBody
import java.io.File

object TestFilesGenerator {


    //region RECEIPT
    fun getFilePart(): MultipartBody.Part {
        val file = File("test file path")
        return MultipartBody.Part.createFormData(
            "form field", file.name
        )
    }

    fun getUploadDto(): UploadDto {
        return UploadDto(message = "upload success", status = 200)
    }
    //endregion

    //region VIDEO
    fun getVideosDto(): VideossDto {
        return VideossDto(
            data = arrayListOf(
                VideoDtoDto(
                    id = 1,
                    date = "test date",
                    photo = "test video",
                    teacherName = "test sender",
                    path = "",
                    studentName = "test recipient",
                    refNo = "test refNo"
                ),
                VideoDtoDto(
                    id = 2,
                    date = "test date",
                    photo = "test video",
                    teacherName = "test sender",
                    path = "",
                    studentName = "test recipient",
                    refNo = "test refNo"
                )
            )

        )
    }
    //endregion


    //region BILL
    fun getBillsDto(): BillsDto {
        return BillsDto(
            data = arrayListOf(
                BillDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name",
                    path = "",
                    studentName = "",
                    refNo = ""
                ),
                BillDto(
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


    //region REPORT
    fun getTimeTablesDto(): TimeTablesDto {
        return TimeTablesDto(
            data = arrayListOf(
                TimeTableDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name",
                    path = "",
                    studentName = "",
                    refNo = ""
                ),
                TimeTableDto(
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
    fun getDeleteDto(): DeleteDto {
        return DeleteDto(
            message = "file deleted success", status = 200
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