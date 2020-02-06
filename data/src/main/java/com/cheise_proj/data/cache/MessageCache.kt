package com.cheise_proj.data.cache

import com.cheise_proj.data.model.message.MessageData

class MessageCache {
    companion object {
        private const val CACHE_EXPIRED_MILLIS: Long = 60 * 1000 // 60 seconds
        private val memCache = HashMap<String, List<MessageData>>()
        private var cacheLastUpdateTime: Long = 0

        fun addMessages(identifier: String, messageDataList: List<MessageData>) {
            println("MessageCache.addMessages")
            if (memCache[identifier] == null) {
                memCache[identifier] = arrayListOf()
            }
            memCache[identifier] = messageDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getMessages(identifier: String): List<MessageData>? {
            println("MessageCache.getMessages")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= CACHE_EXPIRED_MILLIS
            if (!isCachedExpired) return memCache[identifier]
            memCache[identifier] = arrayListOf()
            return null
        }
    }

}