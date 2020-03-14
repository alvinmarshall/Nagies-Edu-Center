package com.cheise_proj.local_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.files.ReportLocal
import javax.inject.Inject

class ReportLocalDataMapper @Inject constructor() : LocalMapper<ReportLocal, FilesData> {
    override fun localToData(l: ReportLocal): FilesData {
        return FilesData(
            id = l.id,
            teacherName = l.teacherName,
            photo = l.photo,
            date = l.date,
            path = l.path,
            refNo = l.refNo,
            studentName = l.studentName
        )
    }

    override fun dataToLocal(d: FilesData): ReportLocal {
        return ReportLocal(
            id = d.id,
            path = d.path,
            date = d.date,
            photo = d.photo,
            teacherName = d.teacherName,
            studentName = d.studentName,
            refNo = d.refNo
        )
    }

    override fun localToDataList(lList: List<ReportLocal>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<FilesData>): List<ReportLocal> {
        val list = arrayListOf<ReportLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }

}