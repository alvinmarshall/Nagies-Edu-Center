package com.cheise_proj.local_source.utils

import com.cheise_proj.local_source.model.files.*

object TestFilesGenerator {

    //region BILL
    fun getVideos(): List<VideoLocal> {
        return arrayListOf(
            VideoLocal(
                id = 1,
                teacherName = "test sender local",
                photo = "test video",
                date = "test date",
                path = "test path",
                studentName = "test recipient",
                refNo = "test refNo"
            ), VideoLocal(
                id = 2,
                teacherName = "test sender2 local",
                photo = "test video",
                date = "test date",
                path = "test path",
                refNo = "test refNo",
                studentName = "test recipient"
            )
        )
    }

    fun getVideo(): VideoLocal {
        return VideoLocal(
            id = 1,
            teacherName = "test sender local",
            photo = "test video",
            date = "test date",
            path = "test path",
            studentName = "test recipient",
            refNo = "test refNo"
        )
    }
    //endregion


    //region BILL
    fun getBills(): List<BillLocal> {
        return arrayListOf(
            BillLocal(
                id = 1,
                teacherName = "test teacher name local",
                photo = "test photo",
                date = "test date",
                path = "test path",
                studentName = "test studentName",
                refNo = "test refNo"
            ), BillLocal(
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

    fun getBill(): BillLocal {
        return BillLocal(
            id = 1,
            teacherName = "test teacher name local",
            photo = "test photo",
            date = "test date",
            path = "test path",
            studentName = "test studentName",
            refNo = "test refNo"
        )
    }
    //endregion

    //region TIMETABLE
    fun getTimeTables(): List<TimeTableLocal> {
        return arrayListOf(
            TimeTableLocal(
                id = 1,
                teacherName = "test teacher name local",
                photo = "test photo",
                date = "test date",
                path = "test path",
                studentName = "test studentName",
                refNo = "test refNo"
            ), TimeTableLocal(
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

    fun getTimeTable(): TimeTableLocal {
        return TimeTableLocal(
            id = 1,
            teacherName = "test teacher name local",
            photo = "test photo",
            date = "test date",
            path = "test path",
            studentName = "test studentName",
            refNo = "test refNo"
        )
    }
    //endregion


    //region REPORT
    fun getReports(): List<ReportLocal> {
        return arrayListOf(
            ReportLocal(
                id = 1,
                teacherName = "test teacher name local",
                photo = "test photo",
                date = "test date",
                path = "test path",
                studentName = "test studentName",
                refNo = "test refNo"
            ), ReportLocal(
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

    fun getReport(): ReportLocal {
        return ReportLocal(
            id = 1,
            teacherName = "test teacher name local",
            photo = "test photo",
            date = "test date",
            path = "test path",
            studentName = "test studentName",
            refNo = "test refNo"
        )
    }
    //endregion

    //region ASSIGNMENT
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
    //endregion

    //region CIRCULAR
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
    //endregion


}