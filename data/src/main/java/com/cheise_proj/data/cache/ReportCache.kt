package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.domain.STALE_MS

class ReportCache {
    companion object {
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "ReportCache"

        fun addReport(filesDataList: List<FilesData>) {
            println("ReportCache.addReport")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getReport(): List<FilesData>? {
            println("ReportCache.getReport")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }

        fun removeItem(value: Int) {
            val mutableList = memCache[IDENTIFIER]?.toMutableList()
            mutableList?.let { data ->
                mutableList.removeAll { filesData ->
                    filesData.id == value
                }
                memCache[IDENTIFIER] = data.toList()
            }

        }
    }
}