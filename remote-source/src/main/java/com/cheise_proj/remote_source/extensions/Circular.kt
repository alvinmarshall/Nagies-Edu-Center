package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.remote_source.mapper.files.CircularDtoDataMapper
import com.cheise_proj.remote_source.model.dto.files.CircularDto

internal fun CircularDto.asData() = CircularDtoDataMapper().dtoToData(this)

internal fun FilesData.asCircularDTO() = CircularDtoDataMapper().dataToDto(this)


internal fun List<CircularDto>.asDataList(): List<FilesData> =
    CircularDtoDataMapper().dtoToDataList(this)

internal fun List<FilesData>.asCircularDTOList(): List<CircularDto> =
    CircularDtoDataMapper().dataToDtoList(this)