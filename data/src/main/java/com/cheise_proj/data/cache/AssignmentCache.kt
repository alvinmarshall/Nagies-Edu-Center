package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData

class AssignmentCache {
    companion object {
        private const val CACHE_EXPIRED_MILLIS: Long = 60 * 1000 // 30 seconds
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addAssignment(identifier: String, filesDataList: List<FilesData>) {
            println("AssignmentCache.addAssignment")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getAssignment(identifier: String): List<FilesData>? {
            println("AssignmentCache.getAssignment")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= CACHE_EXPIRED_MILLIS
            if (!isCachedExpired) return memCache[identifier]
            memCache[identifier] = arrayListOf()
            return null
        }
    }
}