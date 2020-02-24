package com.cheise_proj.data.cache

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.domain.STALE_MS

class ComplaintCache {

    companion object {
        private val memCache = HashMap<String, List<ComplaintData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "ComplaintCache"

        fun addComplaint(filesDataList: List<ComplaintData>) {
            println("ComplaintCache.addComplaint")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getComplaint(): List<ComplaintData>? {
            println("ComplaintCache.getComplaint")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }
    }


}