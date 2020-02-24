package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.domain.STALE_MS

class BillCache {
    companion object {
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "BillCache"

        fun addBill(filesDataList: List<FilesData>) {
            println("BillCache.addBill")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getBill(): List<FilesData>? {
            println("BillCache.getBill")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }
    }

}

