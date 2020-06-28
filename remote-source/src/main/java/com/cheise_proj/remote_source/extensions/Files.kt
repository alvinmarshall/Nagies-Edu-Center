package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.remote_source.mapper.files.FilesDtoDataMapper
import com.cheise_proj.remote_source.model.dto.files.IFilesDto

internal fun IFilesDto.asData() = FilesDtoDataMapper().dtoToData(this)

internal fun FilesData.asDTO() = FilesDtoDataMapper().dataToDto(this)


internal fun List<IFilesDto>.asDTOList(): List<FilesData> =
    FilesDtoDataMapper().dtoToDataList(this)

internal fun List<FilesData>.asDataList(): List<IFilesDto> = FilesDtoDataMapper().dataToDtoList(this)