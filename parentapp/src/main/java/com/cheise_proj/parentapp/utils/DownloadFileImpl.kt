package com.cheise_proj.parentapp.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.cheise_proj.presentation.utils.IDownloadFile
import javax.inject.Inject

class DownloadFileImpl @Inject constructor(private val context: Context) : IDownloadFile {
    private var downloadId: Long = -1
    override fun getFileNameFromUri(fileUri: String?): String? {
        return fileUri?.also {
            return it.substring(it.lastIndexOf("/") + 1, it.length).trim()
        }
    }

    override fun startDownload(fileUri: String?): Long {
        val uri = Uri.parse(fileUri)
        val request = DownloadManager.Request(uri)
        val filename = getFileNameFromUri(fileUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(filename)
        request.setDescription("file downloading...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        val downloadManger = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManger.enqueue(request)
        return downloadId
    }

    override fun registerDownloadBroadCast() {
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context.registerReceiver(broadcastReceiver(), intentFilter)
    }



    private fun getDownloadStatus(): Int {
        val downloadQuery = DownloadManager.Query()
        downloadQuery.setFilterById(downloadId)
        val downloadManger = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val cursor = downloadManger.query(downloadQuery)
        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        if (cursor.moveToFirst()) {
            return cursor.getInt(columnIndex)
        }
        return DownloadManager.ERROR_UNKNOWN
    }

    private fun broadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    if (getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL) {
                        Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Download not complete", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}