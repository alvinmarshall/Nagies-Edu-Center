package com.cheise_proj.domain.entity.message

data class ComplaintEntity(
    override val refNo: String,
    override val studentName: String,
    override val level: String,
    override val contact: String,
    override val receiver: String,
    override val uid: Int,
    override val sender: String,
    override val content: String,
    override val date: String
) : IComplaint