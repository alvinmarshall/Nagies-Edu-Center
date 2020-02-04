package utils

import com.cheise_proj.data.model.files.FilesData


object TestFilesGenerator {
    fun getFiles(): List<FilesData> {
        return arrayListOf(
            FilesData(
                studentName = "test student name",
                date = "test date",
                refNo = "test ref no",
                photo = "test photo",
                path = "test path",
                teacherName = "test teacher name",
                id = 1
            ),
            FilesData(
                studentName = "test student name2",
                date = "test date",
                refNo = "test ref no",
                photo = "test photo2",
                path = "test path",
                teacherName = "test teacher name2",
                id = 2
            )
        )
    }
}