package utils

import com.cheise_proj.data.model.files.FilesData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


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

    fun getFilePart(): MultipartBody.Part {
        val file = File("test file path")
        return MultipartBody.Part.Companion.createFormData(
            "form field", file.name, RequestBody.create(
                "image/jpeg".toMediaTypeOrNull(), file
            )
        )
    }
}