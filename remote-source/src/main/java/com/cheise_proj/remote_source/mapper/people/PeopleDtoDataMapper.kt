package com.cheise_proj.remote_source.mapper.people

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.remote_source.mapper.RemoteListMapper
import com.cheise_proj.remote_source.model.dto.people.IPeopleDto
import com.cheise_proj.remote_source.model.dto.people.TeacherList

internal class PeopleDtoDataMapper : RemoteListMapper<IPeopleDto, PeopleData> {
    override fun dtoToData(t: IPeopleDto): PeopleData {
        return PeopleData(
            id = t.id,
            contact = t.contact,
            gender = t.gender,
            username = t.username ?: "",
            name = t.name,
            photo = t.photo,
            refNo = t.refNo
        )
    }

    override fun dataToDto(d: PeopleData): IPeopleDto {
        return TeacherList(
            id = d.id,
            refNo = d.refNo,
            photo = d.photo,
            name = d.name,
            username = d.username,
            gender = d.gender,
            contact = d.contact
        )
    }

    override fun dtoToDataList(tList: List<IPeopleDto>): List<PeopleData> {
        val list = arrayListOf<PeopleData>()
        tList.forEach {
            list.add(dtoToData(it))
        }
        return list
    }

    override fun dataToDtoList(dList: List<PeopleData>): List<IPeopleDto> {
        val list = arrayListOf<IPeopleDto>()
        dList.forEach {
            list.add(dataToDto(it))
        }
        return list
    }
}