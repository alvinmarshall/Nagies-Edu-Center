package utils

import com.cheise_proj.remote_source.model.dto.files.CircularDto
import com.cheise_proj.remote_source.model.dto.files.CircularsDto

object TestFilesGenerator {
    fun getCircularDto(): CircularsDto {
        return CircularsDto(
            circular = arrayListOf(
                CircularDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name"
                ),
                CircularDto(
                    id = 1,
                    date = "test date",
                    photo = "test photo",
                    teacherName = "test teacher name"
                )
            )

        )
    }
}