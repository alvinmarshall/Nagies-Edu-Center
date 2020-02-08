package com.cheise_proj.presentation.mapper.files

import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.model.files.Bill
import javax.inject.Inject

class BillEntityMapper @Inject constructor() : PresentationMapper<Bill, FilesEntity> {
    override fun presentationToEntity(p: Bill): FilesEntity {
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

    override fun entityToPresentation(e: FilesEntity): Bill {
        return Bill(
            id = e.id,
            teacherName = e.teacherName,
            photo = e.photo,
            date = e.date,
            path = e.path,
            studentName = e.studentName,
            refNo = e.refNo
        )
    }

    override fun presentationToEntityList(pList: List<Bill>): List<FilesEntity> {
        val list = arrayListOf<FilesEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<FilesEntity>): List<Bill> {
        val list = arrayListOf<Bill>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}