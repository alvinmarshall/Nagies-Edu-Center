package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData

class CircularCache {
    companion object {
        private const val CACHE_EXPIRED_MILLIS: Long = 60 * 1000 // 30 seconds
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addCirculars(identifier: String, filesDataList: List<FilesData>) {
            println("CircularCache.addCirculars")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getCirculars(identifier: String): List<FilesData>? {
            println("CircularCache.getCirculars")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= CACHE_EXPIRED_MILLIS
            if (isCachedExpired) {
                memCache[identifier] = arrayListOf()
                return null
            }
            return memCache[identifier]
        }
    }

}

