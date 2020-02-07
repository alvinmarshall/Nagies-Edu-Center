package com.cheise_proj.local_source.utils

import com.cheise_proj.local_source.model.files.AssignmentLocal
import com.cheise_proj.local_source.model.files.CircularLocal

object TestFilesGenerator {

    fun getAssignments(): List<AssignmentLocal> {
        return arrayListOf(
            AssignmentLocal(
                id = 1,
                teacherName = "test teacher name local",
                photo = "test photo",
                date = "test date",
                path = "test path",
                studentName = "test studentName",
                refNo = "test refNo"
            ), AssignmentLocal(
                id = 2,
                teacherName = "test teacher name2 local",
                photo = "test photo",
                date = "test date",
                path = "test path",
                refNo = "test refNo",
                studentName = "test studentName"
            )
        )
    }

    fun getAssignment(): AssignmentLocal {
        return AssignmentLocal(
            id = 1,
            teacherName = "test teacher name local",
            photo = "test photo",
            date = "test date",
            path = "test path",
            studentName = "test studentName",
            refNo = "test refNo"
        )
    }


    fun getCirculars(): List<CircularLocal> {
        return arrayListOf(
            CircularLocal(
                id = 1,
                teacherName = "test teacher name local",
                photo = "test photo",
                date = "test date",
                path = "test path"
            ), CircularLocal(
                id = 2,
                teacherName = "test teacher name2 local",
                photo = "test photo",
                date = "test date",
                path = "test path"
            )
        )
    }

    fun getCircular(): CircularLocal {
        return CircularLocal(
            id = 1,
            teacherName = "test teacher name local",
            photo = "test photo",
            date = "test date",
            path = "test path"
        )
    }


}