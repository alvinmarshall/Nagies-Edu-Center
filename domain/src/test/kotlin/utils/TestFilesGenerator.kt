package utils

import com.cheise_proj.domain.entity.files.FilesEntity

object TestFilesGenerator {
    fun getFiles(): List<FilesEntity> {
        return arrayListOf(
            FilesEntity(
                studentName = "test student name",
                date = "test date",
                refNo = "test ref no",
                photo = "test photo",
                path = "test path",
                teacherName = "test teacher name",
                id = 1
            ),
            FilesEntity(
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