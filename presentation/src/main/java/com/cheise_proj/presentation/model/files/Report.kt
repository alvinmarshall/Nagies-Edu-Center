package com.cheise_proj.presentation.model.files

data class Report(
    override val id: Int,
    override val studentName: String,
    override val teacherName: String,
    override var photo: String?,
    override val date: String,
    override var path: String?,
    override val refNo: String
) : IFiles