package com.cheise_proj.domain.repository

import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.domain.entity.message.MessageEntity
import io.reactivex.Observable

interface MessageRepository {

    //region COMPLAINT
    fun getComplaints(): Observable<List<ComplaintEntity>>

    fun getComplaint(identifier: String): Observable<List<ComplaintEntity>>
    //endregion

    //region MESSAGE
    fun getMessages(): Observable<List<MessageEntity>>

    fun getMessage(identifier: Int): Observable<List<MessageEntity>>
    //endregion
}