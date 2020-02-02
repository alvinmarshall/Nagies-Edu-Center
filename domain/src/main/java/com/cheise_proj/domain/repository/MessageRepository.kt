package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.message.MessageEntity
import io.reactivex.Observable

interface MessageRepository {
    fun getMessages(): Observable<List<MessageEntity>>
    fun getMessage(identifier: Int): Observable<List<MessageEntity>>
}