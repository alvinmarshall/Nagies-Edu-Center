package com.cheise_proj.presentation.mapper.message

import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.model.message.Complaint
import javax.inject.Inject

class ComplaintEntityMapper @Inject constructor() : PresentationMapper<Complaint, ComplaintEntity> {
    override fun presentationToEntity(p: Complaint): ComplaintEntity {
        return ComplaintEntity(
            uid = p.id,
            receiver = p.receiver,
            level = p.level,
            content = p.content,
            sender = p.sender,
            date = p.date,
            studentName = p.studentName,
            refNo = p.refNo,
            contact = p.contact
        )
    }

    override fun entityToPresentation(e: ComplaintEntity): Complaint {
        return Complaint(
            id = e.uid,
            receiver = e.receiver,
            level = e.level,
            content = e.content,
            sender = e.sender,
            date = e.date,
            studentName = e.studentName,
            refNo = e.refNo,
            contact = e.contact
        )
    }

    override fun presentationToEntityList(pList: List<Complaint>): List<ComplaintEntity> {
        val list = arrayListOf<ComplaintEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<ComplaintEntity>): List<Complaint> {
        val list = arrayListOf<Complaint>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}