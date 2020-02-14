package com.cheise_proj.teacher_feature.utils

interface IGetFirstLettersOfStrings {
    fun getLetters(value: String?): String
}

object GetFirstLettersOfStringsImpl: IGetFirstLettersOfStrings {
    override fun getLetters(value: String?): String {
        return value?.let {
            it.trim()
            val slice = it.split(" ")
            var initials = ""
            slice.forEach { s ->
                initials += s[0].toString()
            }
            initials
        } ?: ""
    }
}