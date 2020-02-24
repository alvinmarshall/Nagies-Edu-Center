package com.cheise_proj.data.cache

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.domain.STALE_MS

class PeopleCache {
    companion object {
        private val memCache = HashMap<String, List<PeopleData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "PeopleCache"

        fun addPeople(filesDataList: List<PeopleData>) {
            println("PeopleCache.addPeople")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getPeople(): List<PeopleData>? {
            println("PeopleCache.getPeople")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }
    }
}