package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.files.*

object TestFilesGenerator {

    //region BILL
    fun getBills(): List<Bill> {
        return arrayListOf(
            Bill(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name",
                refNo = "test refNo",
                studentName = "test studentName"
            ),
            Bill(
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

    fun getBill(): Bill {
        return Bill(
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


    //region TIMETABLE
    fun getTimeTables(): List<TimeTable> {
        return arrayListOf(
            TimeTable(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name",
                refNo = "test refNo",
                studentName = "test studentName"
            ),
            TimeTable(
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

    fun getTimeTable(): TimeTable {
        return TimeTable(
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

    //region REPORT
    fun getReports(): List<Report> {
        return arrayListOf(
            Report(
                id = 1,
                path = "test path",
                date = "test date",
                photo = "test photo",
                teacherName = "test teacher name",
                refNo = "test refNo",
                studentName = "test studentName"
            ),
            Report(
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

    fun getReport(): Report {
        return Report(
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
        return Assignment(
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