package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.files.Circular

object TestFilesGenerator {
    //region CIRCULAR
    fun getCirculars(): List<Circular> {
        return arrayListOf(
            Circular(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name"
            ),
            Circular(
                id = 2,
                path = "test path2",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name2"
            )
        )
    }

    fun getCircular(): Circular {
        return Circular(
            id = 1,
            path = "test path",
            date = "test date",
            photo = "test photo",
            teacherName = "test teacher name"
        )
    }
    //endregion
}