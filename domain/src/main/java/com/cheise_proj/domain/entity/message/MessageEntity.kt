package com.cheise_proj.domain.entity.message

import com.cheise_proj.domain.STALE_MS

data class MessageEntity(
    override val uid: Int,
    override val sender: String,
    override val content: String,
    override val date: String,
    override val timestamp: Long
) : IMessage {
    fun isUpToDate(): Boolean = System.currentTimeMillis() - timestamp < STALE_MS
}