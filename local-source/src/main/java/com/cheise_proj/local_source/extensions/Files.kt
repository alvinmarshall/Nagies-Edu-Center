package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.local_source.mapper.files.*
import com.cheise_proj.local_source.mapper.files.AssignmentLocalDataMapper
import com.cheise_proj.local_source.mapper.files.BillLocalDataMapper
import com.cheise_proj.local_source.mapper.files.CircularLocalDataMapper
import com.cheise_proj.local_source.mapper.files.ReportLocalDataMapper
import com.cheise_proj.local_source.model.files.*


//region Assignment
internal fun AssignmentLocal.asAssignmentData() = AssignmentLocalDataMapper().localToData(this)
internal fun FilesData.asAssignmentLocal() = AssignmentLocalDataMapper().dataToLocal(this)

internal fun List<AssignmentLocal>.asAssignmentLocalList(): List<FilesData> =
    AssignmentLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asAssignmentDataList(): List<AssignmentLocal> =
    AssignmentLocalDataMapper().dataToLocalList(this)

//endregion


//region Bill
internal fun BillLocal.asBillData() = BillLocalDataMapper().localToData(this)
internal fun FilesData.asBillLocal() = AssignmentLocalDataMapper().dataToLocal(this)

internal fun List<BillLocal>.asBillLocalList(): List<FilesData> =
    BillLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asBillDataList(): List<BillLocal> =
    BillLocalDataMapper().dataToLocalList(this)

//endregion

//region Circular
internal fun CircularLocal.asCircleData() = CircularLocalDataMapper().localToData(this)
internal fun FilesData.asCircleLocal() = CircularLocalDataMapper().dataToLocal(this)

internal fun List<CircularLocal>.asCircularLocalList(): List<FilesData> =
    CircularLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asCircleDataList(): List<CircularLocal> =
    CircularLocalDataMapper().dataToLocalList(this)

//endregion

//region Report
internal fun ReportLocal.asReportData() = ReportLocalDataMapper().localToData(this)
internal fun FilesData.asReportLocal() = ReportLocalDataMapper().dataToLocal(this)

internal fun List<ReportLocal>.asReportLocalList(): List<FilesData> =
    ReportLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asReportDataList(): List<ReportLocal> =
    ReportLocalDataMapper().dataToLocalList(this)

//endregion


//region Timetable
internal fun TimeTableLocal.asTimeTableData() = TimeTableLocalDataMapper().localToData(this)
internal fun FilesData.asTimeTableLocal() = TimeTableLocalDataMapper().dataToLocal(this)

internal fun List<TimeTableLocal>.asTimeTableLocalList(): List<FilesData> =
    TimeTableLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asTimeTableDataList(): List<TimeTableLocal> =
    TimeTableLocalDataMapper().dataToLocalList(this)

//endregion


//region Video
internal fun VideoLocal.asVideoData() = VideoLocalDataMapper().localToData(this)
internal fun FilesData.asVideoLocal() = VideoLocalDataMapper().dataToLocal(this)

internal fun List<VideoLocal>.asVideoLocalList(): List<FilesData> =
    VideoLocalDataMapper().localToDataList(this)

internal fun List<FilesData>.asVideoDataList(): List<VideoLocal> =
    VideoLocalDataMapper().dataToLocalList(this)

//endregion