package com.cheise_proj.local_source.mapper.people

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.local_source.mapper.base.LocalListMapper
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.people.PeopleLocal

internal class PeopleLocalDataMapper : LocalMapper<PeopleLocal, PeopleData>,
    LocalListMapper<PeopleLocal, PeopleData> {
    override fun localToData(l: PeopleLocal): PeopleData {
        return PeopleData(
            id = l.id,
            contact = l.contact,
            refNo = l.refNo,
            photo = l.photo,
            name = l.name,
            username = l.username,
            gender = l.gender
        )
    }

    override fun dataToLocal(d: PeopleData): PeopleLocal {
        return PeopleLocal(
            id = d.id,
            gender = d.gender,
            username = d.username,
            name = d.name,
            photo = d.photo,
            refNo = d.refNo,
            contact = d.contact
        )
    }

    override fun localToDataList(lList: List<PeopleLocal>): List<PeopleData> {
        val list = arrayListOf<PeopleData>()
        lList.forEach {
            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<PeopleData>): List<PeopleLocal> {
        val list = arrayListOf<PeopleLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }
}