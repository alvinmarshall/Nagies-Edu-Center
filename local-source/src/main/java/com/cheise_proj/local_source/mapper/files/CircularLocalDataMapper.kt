package com.cheise_proj.local_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.local_source.mapper.base.LocalListMapper
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.files.CircularLocal

internal class CircularLocalDataMapper : LocalMapper<CircularLocal, FilesData>,
    LocalListMapper<CircularLocal, FilesData> {
    override fun localToData(l: CircularLocal): FilesData {
        return FilesData(
            id = l.id,
            teacherName = l.teacherName,
            photo = l.photo,
            date = l.date,
            path = l.path,
            refNo = "",
            studentName = ""
        )
    }

    override fun dataToLocal(d: FilesData): CircularLocal {
        return CircularLocal(
            id = d.id,
            path = d.path,
            date = d.date,
            photo = d.photo,
            teacherName = d.teacherName
        )
    }

    override fun localToDataList(lList: List<CircularLocal>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<FilesData>): List<CircularLocal> {
        val list = arrayListOf<CircularLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }

}