package com.cheise_proj.data.cache

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.domain.STALE_MS

class ComplaintCache {

    companion object {
        private val memCache = HashMap<String, List<ComplaintData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addComplaint(identifier: String, filesDataList: List<ComplaintData>) {
            println("ComplaintCache.addComplaint")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getComplaint(identifier: String): List<ComplaintData>? {
            println("ComplaintCache.getComplaint")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[identifier]
            memCache[identifier] = arrayListOf()
            return null
        }
    }


}