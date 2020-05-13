package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.local_source.mapper.message.MessageLocalDataMapper
import com.cheise_proj.local_source.model.message.MessageLocal

internal fun MessageLocal.asData() = MessageLocalDataMapper().localToData(this)

internal fun MessageData.asLocal() = MessageLocalDataMapper().dataToLocal(this)


internal fun List<MessageLocal>.asDataList(): List<MessageData> =
    MessageLocalDataMapper().localToDataList(this)

internal fun List<MessageData>.asLocalList(): List<MessageLocal> =
    MessageLocalDataMapper().dataToLocalList(this)
