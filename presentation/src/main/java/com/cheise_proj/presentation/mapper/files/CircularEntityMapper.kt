package com.cheise_proj.presentation.mapper.files

import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.presentation.mapper.PresentationListMapper
import com.cheise_proj.presentation.model.files.Circular

internal class CircularEntityMapper  : PresentationListMapper<Circular, FilesEntity> {
    override fun presentationToEntity(p: Circular): FilesEntity {
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

    override fun entityToPresentation(e: FilesEntity): Circular {
        return Circular(
            id = e.id,
            teacherName = e.teacherName,
            photo = e.photo,
            date = e.date,
            path = e.path,
            refNo = e.refNo,
            studentName = e.studentName
        )
    }

    override fun presentationToEntityList(pList: List<Circular>): List<FilesEntity> {
        val list = arrayListOf<FilesEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<FilesEntity>): List<Circular> {
        val list = arrayListOf<Circular>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}