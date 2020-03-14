package com.cheise_proj.remote_source.mapper.files

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.remote_source.mapper.RemoteMapper
import com.cheise_proj.remote_source.model.dto.files.IFilesDto
import javax.inject.Inject

class FilesDtoDataMapper @Inject constructor() : RemoteMapper<IFilesDto, FilesData> {
    override fun dtoToData(t: IFilesDto): FilesData {
        return FilesData(
            id = t.id,
            refNo = t.refNo,
            studentName = t.studentName,
            path = t.path,
            teacherName = t.teacherName,
            photo = t.photo,
            date = t.date
        )
    }

    override fun dataToDto(d: FilesData): IFilesDto {
        return object : IFilesDto {
            override val id: Int = d.id
            override var studentName: String = d.studentName
            override var teacherName: String = d.teacherName
            override var photo: String? = d.photo
            override val date: String = d.date
            override var path: String? = d.path
            override val refNo: String = d.refNo
        }
    }

    override fun dtoToDataList(tList: List<IFilesDto>): List<FilesData> {
        val list = arrayListOf<FilesData>()
        tList.forEach {
            list.add(dtoToData(it))
        }
        return list
    }

    override fun dataToDtoList(dList: List<FilesData>): List<IFilesDto> {
        val list = arrayListOf<IFilesDto>()
        dList.forEach {
            list.add(dataToDto(it))
        }
        return list
    }
}