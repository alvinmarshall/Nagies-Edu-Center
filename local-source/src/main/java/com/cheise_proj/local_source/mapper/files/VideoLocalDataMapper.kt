package com.cheise_proj.local_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.local_source.mapper.base.LocalListMapper
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.files.VideoLocal

internal class VideoLocalDataMapper : LocalMapper<VideoLocal, FilesData>,
    LocalListMapper<VideoLocal, FilesData> {
    override fun localToData(l: VideoLocal): FilesData {
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

    override fun dataToLocal(d: FilesData): VideoLocal {
        return VideoLocal(
            id = d.id,
            path = d.path,
            date = d.date,
            photo = d.photo,
            teacherName = d.teacherName,
            refNo = d.refNo,
            studentName = d.studentName
        )
    }

    override fun localToDataList(lList: List<VideoLocal>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<FilesData>): List<VideoLocal> {
        val list = arrayListOf<VideoLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }

}