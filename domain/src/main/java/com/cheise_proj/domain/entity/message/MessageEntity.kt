package com.cheise_proj.domain.entity.message

data class MessageEntity(
    override val uid: Int,
    override val sender: String,
    override val content: String,
    override val date: String
) : IMessage