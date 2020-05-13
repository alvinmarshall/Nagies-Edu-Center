package com.cheise_proj.local_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.local_source.mapper.base.LocalListMapper
import com.cheise_proj.local_source.model.files.BillLocal

internal class BillLocalDataMapper : LocalListMapper<BillLocal, FilesData> {
    override fun localToData(l: BillLocal): FilesData {
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

    override fun dataToLocal(d: FilesData): BillLocal {
        return BillLocal(
            id = d.id,
            path = d.path,
            date = d.date,
            photo = d.photo,
            teacherName = d.teacherName,
            refNo = d.refNo,
            studentName = d.studentName
        )
    }

    override fun localToDataList(lList: List<BillLocal>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<FilesData>): List<BillLocal> {
        val list = arrayListOf<BillLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }

}