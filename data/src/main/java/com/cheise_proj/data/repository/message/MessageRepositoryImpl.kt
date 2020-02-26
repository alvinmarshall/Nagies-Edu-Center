package com.cheise_proj.data.repository.message

import com.cheise_proj.data.cache.ComplaintCache
import com.cheise_proj.data.cache.MessageCache
import com.cheise_proj.data.mapper.message.ComplaintDataEntityMapper
import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.repository.MessageRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val messageDataEntityMapper: MessageDataEntityMapper,
    private val complaintDataEntityMapper: ComplaintDataEntityMapper
) : MessageRepository {

    override fun getComplaints(): Observable<List<ComplaintEntity>> {
        val complaintObservable: Observable<List<ComplaintEntity>>
        val cacheComplaint = ComplaintCache.getComplaint()

        val local = localSource.getComplaints()
            .map { t: List<ComplaintData> ->
                complaintDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getComplaint()
            .map { t: List<ComplaintData> ->
                localSource.saveComplaint(t)
                complaintDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        complaintObservable = if (cacheComplaint != null) {
            println("Remote source NOT invoked")
            val cache = complaintDataEntityMapper.dataToEntityList(cacheComplaint)
            Observable.just(cache)
        } else {
            remote
        }

        return complaintObservable
            .map { t: List<ComplaintEntity> ->
                if (cacheComplaint == null) {
                    ComplaintCache.addComplaint(
                        complaintDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getComplaint(identifier: String): Observable<List<ComplaintEntity>> {
        return localSource.getComplaint(identifier).toObservable()
            .map {
                val data = complaintDataEntityMapper.dataToEntity(it)
                return@map arrayListOf(data)
            }
    }

    override fun sendComplaint(content: String, identifier: String?): Observable<Boolean> {
        return remoteSource.sendComplaint(content, identifier)
    }

    override fun sendMessage(
        content: String,
        receiverName: String?,
        identifier: String?
    ): Observable<Boolean> {
        return remoteSource.sendMessage(content, receiverName, identifier).map {
            if (it) {
                val id = System.currentTimeMillis().toInt()
                val date = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date())
                MessageCache.insertOneItem(
                    MessageData(
                        uid = id,
                        content = content,
                        date = date,
                        sender = ""
                    )
                )
                return@map it
            }
            return@map it
        }
    }

    override fun getSentMessages(): Observable<List<MessageEntity>> {
        val messagesObservable: Observable<List<MessageEntity>>
        val cacheMessages = MessageCache.getMessage()

        val local = localSource.getMessages()
            .map { t: List<MessageData> ->
                messageDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getSentMessages()
            .map { t: List<MessageData> ->
                localSource.saveMessages(t)
                messageDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        messagesObservable = if (cacheMessages != null) {
            println("Remote source NOT invoked")
            val cache = messageDataEntityMapper.dataToEntityList(cacheMessages)
            Observable.just(cache)
        } else {
            remote
        }

        return messagesObservable
            .map { t: List<MessageEntity> ->
                if (cacheMessages == null) {
                    MessageCache.addMessage(
                        messageDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getMessages(): Observable<List<MessageEntity>> {
        val messagesObservable: Observable<List<MessageEntity>>
        val cacheMessages = MessageCache.getMessage()

        val local = localSource.getMessages()
            .map { t: List<MessageData> ->
                messageDataEntityMapper.dataToEntityList(t)
            }

        val remote = remoteSource.getMessages()
            .map { t: List<MessageData> ->
                localSource.saveMessages(t)
                messageDataEntityMapper.dataToEntityList(t)
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        messagesObservable = if (cacheMessages != null) {
            println("Remote source NOT invoked")
            val cache = messageDataEntityMapper.dataToEntityList(cacheMessages)
            Observable.just(cache)
        } else {
            remote
        }

        return messagesObservable
            .map { t: List<MessageEntity> ->
                if (cacheMessages == null) {
                    MessageCache.addMessage(
                        messageDataEntityMapper.entityToDataList(t)
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()

    }

    override fun getMessage(identifier: Int): Observable<List<MessageEntity>> {
        return localSource.getMessage(identifier).toObservable()
            .map {
                val data = messageDataEntityMapper.dataToEntity(it)
                return@map arrayListOf(data)
            }
    }
}