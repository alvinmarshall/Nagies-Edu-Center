package com.cheise_proj.teacher_feature.ui.send_message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.utils.GetFirstLettersOfStringsImpl
import kotlinx.android.synthetic.main.list_message_sent.view.*

class MessageListAdapter :
    ListAdapter<Message, MessageListAdapter.SentMessageVh>(SentCallback()) {
    private var generatorColor: IColorGenerator? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentMessageVh {
        return SentMessageVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_message_sent,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SentMessageVh, position: Int) {
        holder.bind(getItem(position),generatorColor)
    }

    internal fun setGeneratorColor(colorGenerator: IColorGenerator) {
        generatorColor = colorGenerator
    }

    inner class SentMessageVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Message?,
            generatorColor: IColorGenerator?
        ) {
            val sender = "You"
            itemView.apply {
                tv_sender.text = sender
                tv_sender_content.text = item?.content
                tv_sender_time.text = item?.date
                tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(sender)
                avatar_image.apply {
                    GlideApp.with(context).load(generatorColor?.getColor()).into(this)
                }
            }
        }
    }
}

class SentCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}
