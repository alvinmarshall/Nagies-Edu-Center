package com.cheise_proj.presentation.utils

interface IDownloadFile {
    fun getFileNameFromUri(fileUri: String?): String?
    fun startDownload(fileUri: String?): Long
    fun registerDownloadBroadCast()
//    fun unRegisterDownloadBroadCast()
}