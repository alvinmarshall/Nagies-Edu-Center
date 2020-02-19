package com.cheise_proj.local_source.mapper.message

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.message.ComplaintLocal
import javax.inject.Inject

class ComplaintLocalDataMapper @Inject constructor() : LocalMapper<ComplaintLocal, ComplaintData> {
    override fun localToData(l: ComplaintLocal): ComplaintData {
        return ComplaintData(
            id = l.id,
            contact = l.contact,
            refNo = l.refNo,
            studentName = l.studentName,
            date = l.date,
            sender = l.sender,
            content = l.content,
            level = l.level,
            receiver = l.receiver
        )
    }

    override fun dataToLocal(d: ComplaintData): ComplaintLocal {
        return ComplaintLocal(
            id = d.id,
            contact = d.contact,
            refNo = d.refNo,
            studentName = d.studentName,
            date = d.date,
            sender = d.sender,
            content = d.content,
            level = d.level,
            receiver = d.receiver
        )
    }

    override fun localToDataList(lList: List<ComplaintLocal>): List<ComplaintData> {
        val list = arrayListOf<ComplaintData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<ComplaintData>): List<ComplaintLocal> {
        val list = arrayListOf<ComplaintLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }
}