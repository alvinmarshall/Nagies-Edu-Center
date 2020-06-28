package com.cheise_proj.data.repository.message

import com.cheise_proj.data.cache.ComplaintCache
import com.cheise_proj.data.cache.MessageCache
import com.cheise_proj.data.extensions.asDataList
import com.cheise_proj.data.extensions.asEntity
import com.cheise_proj.data.extensions.asEntityList
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
    private val localSource: LocalSource
) : MessageRepository {


    override fun getSentComplaints(): Observable<List<ComplaintEntity>> {
        val complaintObservable: Observable<List<ComplaintEntity>>
        val cacheComplaint = ComplaintCache.getComplaint()

        val local = localSource.getComplaints()
            .map { t: List<ComplaintData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getSentComplaint()
            .map { t: List<ComplaintData> ->
                localSource.saveComplaint(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        complaintObservable = if (cacheComplaint != null) {
            println("Remote source NOT invoked")
            val cache = cacheComplaint.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return complaintObservable
            .map { t: List<ComplaintEntity> ->
                if (cacheComplaint == null) {
                    ComplaintCache.addComplaint(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()

    }

    override fun getComplaints(): Observable<List<ComplaintEntity>> {
        val complaintObservable: Observable<List<ComplaintEntity>>
        val cacheComplaint = ComplaintCache.getComplaint()

        val local = localSource.getComplaints()
            .map { t: List<ComplaintData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getComplaint()
            .map { t: List<ComplaintData> ->
                localSource.saveComplaint(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        complaintObservable = if (cacheComplaint != null) {
            println("Remote source NOT invoked")
            val cache = cacheComplaint.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return complaintObservable
            .map { t: List<ComplaintEntity> ->
                if (cacheComplaint == null) {
                    ComplaintCache.addComplaint(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getComplaint(identifier: String): Observable<List<ComplaintEntity>> {
        return localSource.getComplaint(identifier).toObservable()
            .map {
                val data = it.asEntity()
                return@map arrayListOf(data)
            }
    }

    override fun sendComplaint(content: String, identifier: String?): Observable<Boolean> {
        return remoteSource.sendComplaint(content, identifier)
            .map {
                if (it) {
                    val id = System.currentTimeMillis().toInt()
                    val date =
                        SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date())
                    ComplaintCache.insertOneItem(
                        ComplaintData(
                            id = id,
                            content = content,
                            receiver = identifier!!,
                            contact = "",
                            date = date,
                            level = "",
                            refNo = "",
                            sender = "You",
                            studentName = ""
                        )
                    )
                    return@map it
                }
                return@map it
            }

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
                t.asEntityList()
            }

        val remote = remoteSource.getSentMessages()
            .map { t: List<MessageData> ->
                localSource.saveMessages(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        messagesObservable = if (cacheMessages != null) {
            println("Remote source NOT invoked")
            val cache = cacheMessages.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return messagesObservable
            .map { t: List<MessageEntity> ->
                if (cacheMessages == null) {
                    MessageCache.addMessage(t.asDataList())
                }
                return@map t
            }.mergeWith(local).take(1).distinct()
    }

    override fun getMessages(): Observable<List<MessageEntity>> {
        val messagesObservable: Observable<List<MessageEntity>>
        val cacheMessages = MessageCache.getMessage()

        val local = localSource.getMessages()
            .map { t: List<MessageData> ->
                t.asEntityList()
            }

        val remote = remoteSource.getMessages()
            .map { t: List<MessageData> ->
                localSource.saveMessages(t)
                t.asEntityList()
            }
            .onErrorResumeNext(Function {
                println(it.localizedMessage)
                local
            })

        messagesObservable = if (cacheMessages != null) {
            println("Remote source NOT invoked")
            val cache = cacheMessages.asEntityList()
            Observable.just(cache)
        } else {
            remote
        }

        return messagesObservable
            .map { t: List<MessageEntity> ->
                if (cacheMessages == null) {
                    MessageCache.addMessage(
                        t.asDataList()
                    )
                }
                return@map t
            }.mergeWith(local).take(1).distinct()

    }

    override fun getMessage(identifier: Int): Observable<List<MessageEntity>> {
        return localSource.getMessage(identifier).toObservable()
            .map {
                val data = it.asEntity()
                return@map arrayListOf(data)
            }
    }
}