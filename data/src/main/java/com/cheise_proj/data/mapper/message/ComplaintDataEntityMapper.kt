package com.cheise_proj.data.mapper.message

import com.cheise_proj.data.mapper.base.DataListMapper
import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.domain.entity.message.ComplaintEntity

internal class ComplaintDataEntityMapper : DataMapper<ComplaintData, ComplaintEntity>,
    DataListMapper<ComplaintData, ComplaintEntity> {
    override fun dataToEntity(d: ComplaintData): ComplaintEntity {
        return ComplaintEntity(
            uid = d.id,
            level = d.level,
            content = d.content,
            sender = d.sender,
            date = d.date,
            receiver = d.receiver,
            studentName = d.studentName,
            refNo = d.refNo,
            contact = d.contact
        )
    }

    override fun entityToData(e: ComplaintEntity): ComplaintData {
        return ComplaintData(
            id = e.uid,
            level = e.level,
            content = e.content,
            sender = e.sender,
            date = e.date,
            receiver = e.receiver,
            studentName = e.studentName,
            refNo = e.refNo,
            contact = e.contact
        )
    }

    override fun dataToEntityList(dList: List<ComplaintData>): List<ComplaintEntity> {
        val list = arrayListOf<ComplaintEntity>()
        dList.forEach {
            list.add(dataToEntity(it))
        }
        return list
    }

    override fun entityToDataList(eList: List<ComplaintEntity>): List<ComplaintData> {
        val list = arrayListOf<ComplaintData>()
        eList.forEach {
            list.add(entityToData(it))
        }
        return list
    }
}