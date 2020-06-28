package com.cheise_proj.remote_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.remote_source.mapper.RemoteListMapper
import com.cheise_proj.remote_source.model.dto.files.CircularDto

internal class CircularDtoDataMapper : RemoteListMapper<CircularDto, FilesData> {
    override fun dtoToData(t: CircularDto): FilesData {
        return FilesData(
            id = t.id,
            studentName = "",
            date = t.date,
            refNo = "",
            photo = t.photo,
            path = "",
            teacherName = t.teacherName
        )
    }

    override fun dataToDto(d: FilesData): CircularDto {
        return CircularDto(
            id = d.id,
            teacherName = d.teacherName,
            photo = d.photo,
            date = d.date
        )
    }

    override fun dtoToDataList(tList: List<CircularDto>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        tList.forEach {
            list.add(dtoToData(it))
        }
        return list
    }

    override fun dataToDtoList(dList: List<FilesData>): List<CircularDto> {
        val list = arrayListOf<CircularDto>()
        dList.forEach {
            list.add(dataToDto(it))
        }
        return list
    }
}