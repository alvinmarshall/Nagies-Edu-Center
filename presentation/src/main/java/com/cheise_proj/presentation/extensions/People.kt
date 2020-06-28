package com.cheise_proj.presentation.extensions

import com.cheise_proj.domain.entity.people.PeopleEntity
import com.cheise_proj.presentation.mapper.people.PeopleEntityMapper
import com.cheise_proj.presentation.model.people.People

internal fun People.asEntity() = PeopleEntityMapper().presentationToEntity(this)

internal fun PeopleEntity.asPresentation() = PeopleEntityMapper().entityToPresentation(this)


internal fun List<People>.asEntityList(): List<PeopleEntity> =
    PeopleEntityMapper().presentationToEntityList(this)

internal fun List<PeopleEntity>.asPresentationList(): List<People> =
    PeopleEntityMapper().entityToPresentationList(this)