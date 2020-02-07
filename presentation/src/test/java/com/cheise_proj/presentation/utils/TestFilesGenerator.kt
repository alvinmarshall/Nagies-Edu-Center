package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.files.Assignment
import com.cheise_proj.presentation.model.files.Circular

object TestFilesGenerator {

    //region ASSIGNMENT
    fun getAssignments(): List<Assignment> {
        return arrayListOf(
            Assignment(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name",
                refNo = "test refNo",
                studentName = "test studentName"
            ),
            Assignment(
                id = 2,
                path = "test path2",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name2",
                studentName = "test studentName",
                refNo = "test refNo"
            )
        )
    }

    fun getAssignment(): Assignment {
        return  Assignment(
            id = 1,
            path = "test path",
            date = "test date",
            photo = "test photo",
            teacherName = "test teacher name",
            refNo = "test refNo",
            studentName = "test studentName"
        )
    }
    //endregion

    //region CIRCULAR
    fun getCirculars(): List<Circular> {
        return arrayListOf(
            Circular(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name",
                studentName = "",
                refNo = ""
            ),
            Circular(
                id = 2,
                path = "test path2",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name2",
                studentName = "",
                refNo = ""
            )
        )
    }

    fun getCircular(): Circular {
        return Circular(
            id = 1,
            path = "test path",
            date = "test date",
            photo = "test photo",
            teacherName = "test teacher name",
            refNo = "",
            studentName = ""
        )
    }
    //endregion
}