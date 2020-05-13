package com.cheise_proj.data.mapper.files

import com.cheise_proj.data.mapper.base.DataListMapper
import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.domain.entity.files.FilesEntity

internal class FilesDataEntityMapper: DataListMapper<FilesData, FilesEntity> {
    override fun dataToEntity(d: FilesData): FilesEntity {
        return FilesEntity(
            id = d.id,
            teacherName = d.teacherName,
            path = d.path,
            photo = d.photo,
            refNo = d.refNo,
            date = d.date,
            studentName = d.studentName
        )
    }

    override fun entityToData(e: FilesEntity): FilesData {
        return FilesData(
            id = e.id,
            teacherName = e.teacherName,
            path = e.path,
            photo = e.photo,
            refNo = e.refNo,
            date = e.date,
            studentName = e.studentName
        )
    }

    override fun dataToEntityList(dList: List<FilesData>): List<FilesEntity> {
        val list = arrayListOf<FilesEntity>()
        dList.forEach {
            list.add(dataToEntity(it))
        }
        return list
    }

    override fun entityToDataList(eList: List<FilesEntity>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        eList.forEach {
            list.add(entityToData(it))
        }
        return list
    }


}