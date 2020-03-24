package com.cheise_proj.presentation.mapper.files

import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.model.files.Bill
import com.cheise_proj.presentation.model.files.Video
import javax.inject.Inject

class VideoEntityMapper @Inject constructor() : PresentationMapper<Video, FilesEntity> {
    override fun presentationToEntity(p: Video): FilesEntity {
        return FilesEntity(
            id = p.id,
            path = p.path,
            date = p.date,
            photo = p.photo,
            teacherName = p.teacherName,
            studentName = p.studentName,
            refNo = p.refNo
        )
    }

    override fun entityToPresentation(e: FilesEntity): Video {
        return Video(
            id = e.id,
            teacherName = e.teacherName,
            photo = e.photo,
            date = e.date,
            path = e.path,
            studentName = e.studentName,
            refNo = e.refNo
        )
    }

    override fun presentationToEntityList(pList: List<Video>): List<FilesEntity> {
        val list = arrayListOf<FilesEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<FilesEntity>): List<Video> {
        val list = arrayListOf<Video>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}