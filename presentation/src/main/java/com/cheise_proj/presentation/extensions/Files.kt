package com.cheise_proj.presentation.extensions


import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.presentation.mapper.files.*
import com.cheise_proj.presentation.mapper.files.AssignmentEntityMapper
import com.cheise_proj.presentation.mapper.files.BillEntityMapper
import com.cheise_proj.presentation.mapper.files.CircularEntityMapper
import com.cheise_proj.presentation.mapper.files.ReportEntityMapper
import com.cheise_proj.presentation.mapper.files.TimeTableEntityMapper
import com.cheise_proj.presentation.model.files.*


//region Assignment
internal fun Assignment.asAssignmentEntity() = AssignmentEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asAssignmentPresentation() =
    AssignmentEntityMapper().entityToPresentation(this)

internal fun List<Assignment>.asAssignmentEntityList(): List<FilesEntity> =
    AssignmentEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asAssignmentPresentationList(): List<Assignment> =
    AssignmentEntityMapper().entityToPresentationList(this)

//endregion

//region Bill
internal fun Bill.asBillEntity() = BillEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asBillPresentation() = BillEntityMapper().entityToPresentation(this)

internal fun List<Bill>.asBillEntityList(): List<FilesEntity> =
    BillEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asBillPresentationList(): List<Bill> =
    BillEntityMapper().entityToPresentationList(this)

//endregion

//region Circular
internal fun Circular.asCircularEntity() = CircularEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asCircularPresentation() =
    CircularEntityMapper().entityToPresentation(this)

internal fun List<Circular>.asCircularEntityList(): List<FilesEntity> =
    CircularEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asCircularPresentationList(): List<Circular> =
    CircularEntityMapper().entityToPresentationList(this)

//endregion

//region Report
internal fun Report.asReportEntity() = ReportEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asReportPresentation() = ReportEntityMapper().entityToPresentation(this)

internal fun List<Report>.asReportEntityList(): List<FilesEntity> =
    ReportEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asReportPresentationList(): List<Report> =
    ReportEntityMapper().entityToPresentationList(this)

//endregion


//region Timetable
internal fun TimeTable.asTimeTableEntity() = TimeTableEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asTimeTablePresentation() =
    TimeTableEntityMapper().entityToPresentation(this)

internal fun List<TimeTable>.asTimeTableEntityList(): List<FilesEntity> =
    TimeTableEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asTimeTablePresentationList(): List<TimeTable> =
    TimeTableEntityMapper().entityToPresentationList(this)

//endregion


//region Video
internal fun Video.asVideoEntity() = VideoEntityMapper().presentationToEntity(this)
internal fun FilesEntity.asVideoPresentation() = VideoEntityMapper().entityToPresentation(this)

internal fun List<Video>.asVideoEntityList(): List<FilesEntity> =
    VideoEntityMapper().presentationToEntityList(this)

internal fun List<FilesEntity>.asVideoPresentationList(): List<Video> =
    VideoEntityMapper().entityToPresentationList(this)

//endregion