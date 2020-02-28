package com.cheise_proj.presentation.viewmodel.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.usecase.message.GetMessageTask
import com.cheise_proj.domain.usecase.message.GetSentMessageTask
import com.cheise_proj.domain.usecase.message.SendMessageTask
import com.cheise_proj.presentation.mapper.message.MessageEntityMapper
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val getMessageTask: GetMessageTask,
    private val messageEntityMapper: MessageEntityMapper,
    private val sendMessageTask: SendMessageTask,
    private val getSentMessageTask: GetSentMessageTask
) : BaseViewModel() {

    fun getSentMessages(): LiveData<Resource<List<Message>>> {
        return getSentMessageTask.buildUseCase()
            .map { t: List<MessageEntity> -> messageEntityMapper.entityToPresentationList(t) }
            .map { t: List<Message> -> Resource.onSuccess(t) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun sendMessage(
        content: String,
        receiverName: String?,
        identifier: String?
    ): LiveData<Resource<Boolean>> {
        return sendMessageTask.buildUseCase(
            sendMessageTask.Params(
                content,
                receiverName,
                identifier
            )
        )
            .map { t: Boolean ->
                Resource.onSuccess(t)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function {
                Observable.just(Resource.onError(it.localizedMessage))
            })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getMessages(): LiveData<Resource<List<Message>>> {
        return getMessageTask.buildUseCase()
            .map { t: List<MessageEntity> -> messageEntityMapper.entityToPresentationList(t) }
            .map { t: List<Message> -> Resource.onSuccess(t) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getMessage(identifier: Int): LiveData<Resource<Message>> {
        return getMessageTask.buildUseCase(identifier).map { t: List<MessageEntity> ->
            messageEntityMapper.entityToPresentationList(t)[0]
        }.map { t: Message ->
            Resource.onSuccess(t)
        }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function { Observable.just(Resource.onError(it.localizedMessage)) }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }
}