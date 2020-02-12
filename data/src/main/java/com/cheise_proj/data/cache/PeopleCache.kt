package com.cheise_proj.data.cache

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.domain.STALE_MS

class PeopleCache {
    companion object {
        private val memCache = HashMap<String, List<PeopleData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addPeople(identifier: String, filesDataList: List<PeopleData>) {
            println("PeopleCache.addPeople")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getPeople(identifier: String): List<PeopleData>? {
            println("PeopleCache.getPeople")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[identifier]
            memCache[identifier] = arrayListOf()
            return null
        }
    }
}