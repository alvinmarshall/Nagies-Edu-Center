package com.cheise_proj.data.mapper.people

import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.domain.entity.people.PeopleEntity
import javax.inject.Inject

class PeopleDataEntityMapper @Inject constructor() : DataMapper<PeopleData, PeopleEntity> {
    override fun dataToEntity(d: PeopleData): PeopleEntity {
        return PeopleEntity(
            id = d.id,
            username = d.username,
            name = d.name,
            photo = d.photo,
            refNo = d.refNo,
            gender = d.gender,
            contact = d.contact
        )
    }

    override fun entityToData(e: PeopleEntity): PeopleData {
        return PeopleData(
            id = e.id,
            refNo = e.refNo,
            photo = e.photo,
            name = e.name,
            username = e.username,
            gender = e.gender,
            contact = e.contact
        )
    }

    override fun dataToEntityList(dList: List<PeopleData>): List<PeopleEntity> {
        val list = arrayListOf<PeopleEntity>()
        dList.forEach {
            list.add(dataToEntity(it))
        }
        return list
    }

    override fun entityToDataList(eList: List<PeopleEntity>): List<PeopleData> {
        val list = arrayListOf<PeopleData>()
        eList.forEach {
            list.add(entityToData(it))
        }
        return list
    }
}