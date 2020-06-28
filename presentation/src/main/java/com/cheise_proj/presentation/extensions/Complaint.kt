package com.cheise_proj.presentation.extensions

import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.presentation.mapper.message.ComplaintEntityMapper
import com.cheise_proj.presentation.model.message.Complaint

internal fun Complaint.asEntity() = ComplaintEntityMapper().presentationToEntity(this)

internal fun ComplaintEntity.asPresentation() = ComplaintEntityMapper().entityToPresentation(this)


internal fun List<Complaint>.asEntityList(): List<ComplaintEntity> =
    ComplaintEntityMapper().presentationToEntityList(this)

internal fun List<ComplaintEntity>.asPresentationList(): List<Complaint> =
    ComplaintEntityMapper().entityToPresentationList(this)