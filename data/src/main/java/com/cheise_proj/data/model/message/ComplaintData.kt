package com.cheise_proj.data.model.message

data class ComplaintData(
    val refNo: String,
    val studentName: String,
    val level: String,
    val contact: String,
    val receiver: String,
    val id: Int,
    val sender: String,
    val content: String,
    val date: String
)