package com.cheise_proj.data.cache

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.domain.STALE_MS

class MessageCache {
    companion object {
        private val memCache = HashMap<String, List<MessageData>>()
        private var cacheLastUpdateTime: Long = 0
        private const val IDENTIFIER = "MessageCache"

        fun addMessage(filesDataList: List<MessageData>) {
            println("MessageCache.addMessage")
            if (memCache[IDENTIFIER] == null) {
                memCache[IDENTIFIER] = arrayListOf()
            }
            memCache[IDENTIFIER] = filesDataList
            cacheLastUpdateTime = System.currentTimeMillis()
        }

        fun getMessage(): List<MessageData>? {
            println("MessageCache.getMessage")
            val isCachedExpired =
                (System.currentTimeMillis() - cacheLastUpdateTime) >= STALE_MS
            if (!isCachedExpired) return memCache[IDENTIFIER]
            memCache[IDENTIFIER] = arrayListOf()
            return null
        }
    }

}

