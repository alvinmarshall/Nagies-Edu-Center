package com.cheise_proj.remote_source.mapper.message

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.remote_source.mapper.RemoteListMapper
import com.cheise_proj.remote_source.mapper.RemoteMapper
import com.cheise_proj.remote_source.model.dto.message.ComplaintDto

internal class ComplaintDtoDataMapper : RemoteMapper<ComplaintDto, ComplaintData>,
    RemoteListMapper<ComplaintDto, ComplaintData> {
    override fun dtoToData(t: ComplaintDto): ComplaintData {
        return ComplaintData(
            id = t.id,
            contact = t.contact,
            refNo = t.refNo,
            studentName = t.studentName,
            receiver = t.receiver,
            date = t.date,
            sender = t.sender,
            content = t.content,
            level = t.level
        )
    }

    override fun dataToDto(d: ComplaintData): ComplaintDto {
        return ComplaintDto(
            id = d.id,
            contact = d.contact,
            refNo = d.refNo,
            studentName = d.studentName,
            receiver = d.receiver,
            date = d.date,
            sender = d.sender,
            content = d.content,
            level = d.level
        )
    }

    override fun dtoToDataList(tList: List<ComplaintDto>): List<ComplaintData> {
        val list = arrayListOf<ComplaintData>()
        tList.forEach {
            list.add(dtoToData(it))
        }
        return list
    }

    override fun dataToDtoList(dList: List<ComplaintData>): List<ComplaintDto> {
        val list = arrayListOf<ComplaintDto>()
        dList.forEach {
            list.add(dataToDto(it))
        }
        return list
    }
}