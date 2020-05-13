package com.cheise_proj.presentation.mapper.people

import com.cheise_proj.domain.entity.people.PeopleEntity
import com.cheise_proj.presentation.mapper.PresentationListMapper
import com.cheise_proj.presentation.model.people.People

internal class PeopleEntityMapper: PresentationListMapper<People, PeopleEntity> {
    override fun presentationToEntity(p: People): PeopleEntity {
        return PeopleEntity(
            id = p.id,
            contact = p.contact,
            refNo = p.refNo,
            photo = p.photo,
            name = p.name,
            username = p.username,
            gender = p.gender
        )
    }

    override fun entityToPresentation(e: PeopleEntity): People {
        return People(
            id = e.id,
            gender = e.gender,
            username = e.username,
            name = e.name,
            photo = e.photo,
            refNo = e.refNo,
            contact = e.contact
        )
    }

    override fun presentationToEntityList(pList: List<People>): List<PeopleEntity> {
        val list = arrayListOf<PeopleEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<PeopleEntity>): List<People> {
        val list = arrayListOf<People>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}