package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.domain.STALE_MS

class ReportCache {
    companion object {
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addReport(identifier: String, filesDataList: List<FilesData>) {
            println("ReportCache.addReport")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getReport(identifier: String): List<FilesData>? {
            println("ReportCache.getReport")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[identifier]
            memCache[identifier] = arrayListOf()
            return null
        }
    }
}