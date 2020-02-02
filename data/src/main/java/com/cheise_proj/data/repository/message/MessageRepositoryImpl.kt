package com.cheise_proj.data.repository.message

import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.repository.MessageRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val messageDataEntityMapper: MessageDataEntityMapper
) : MessageRepository {
    override fun getMessages(): Observable<List<MessageEntity>> {
        val local = localSource.getMessages()
            .map { t: List<MessageData> ->
                messageDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getMessages()
            .map { t: List<MessageData> ->
                localSource.saveMessages(t)
                messageDataEntityMapper.dataToEntityList(t)
            }.onErrorResumeNext(Function {
                println(it.localizedMessage)
                Observable.just(listOf())
            })

        return Observable
            .mergeDelayError(local, remote)
            .filter {
                return@filter it.firstOrNull()?.isUpToDate() ?: false
            }
            .take(1) //pick local source

    }

    override fun getMessage(identifier: Int): Observable<List<MessageEntity>> {
        return localSource.getMessage(identifier).toObservable()
            .map {
                val data = messageDataEntityMapper.dataToEntity(it)
                return@map arrayListOf(data)
            }
    }
}