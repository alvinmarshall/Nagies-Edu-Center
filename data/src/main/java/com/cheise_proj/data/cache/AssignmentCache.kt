package com.cheise_proj.data.cache

import com.cheise_proj.data.model.files.FilesData
import com.cheise_proj.domain.STALE_MS

class AssignmentCache {
    companion object {
        private val memCache = HashMap<String, List<FilesData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "AssignmentCache"

        fun addAssignment(filesDataList: List<FilesData>) {
            println("AssignmentCache.addAssignment")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getAssignment(): List<FilesData>? {
            println("AssignmentCache.getAssignment")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }

        fun removeItem(value: Int) {
            val mutableList = memCache[IDENTIFIER]?.toMutableList()
            mutableList?.let {
                mutableList.removeAll {
                    it.id == value
                }
                memCache[IDENTIFIER] = mutableList.toList()
                println("AssignmentCache.removeItem")
            }


        }
    }
}