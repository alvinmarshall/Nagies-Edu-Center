package com.cheise_proj.presentation.mapper.files

import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.presentation.mapper.PresentationListMapper
import com.cheise_proj.presentation.model.files.Assignment

internal class AssignmentEntityMapper : PresentationListMapper<Assignment, FilesEntity> {
    override fun presentationToEntity(p: Assignment): FilesEntity {
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

    override fun entityToPresentation(e: FilesEntity): Assignment {
        return Assignment(
            id = e.id,
            teacherName = e.teacherName,
            photo = e.photo,
            date = e.date,
            path = e.path,
            studentName = e.studentName,
            refNo = e.refNo
        )
    }

    override fun presentationToEntityList(pList: List<Assignment>): List<FilesEntity> {
        val list = arrayListOf<FilesEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<FilesEntity>): List<Assignment> {
        val list = arrayListOf<Assignment>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}