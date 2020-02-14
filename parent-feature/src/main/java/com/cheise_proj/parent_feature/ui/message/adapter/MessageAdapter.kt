package com.cheise_proj.parent_feature.ui.message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.utils.GetFirstLettersOfStringsImpl
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.utils.IColorGenerator
import kotlinx.android.synthetic.main.list_message.view.*


class MessageAdapter :
    ListAdapter<Message, MessageVh>(MessageDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<Int?,TextView>>? = null
    private var generatorColor: IColorGenerator? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVh {
        return MessageVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_message,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageVh, position: Int) {
        holder.bind(getItem(position), generatorColor, adapterClickListener)
    }

    fun setGeneratorColor(colorGenerator: IColorGenerator) {
        generatorColor = colorGenerator
    }

    fun setAdapterClickListener(callback: AdapterClickListener<Pair<Int?,TextView>>?) {
        adapterClickListener = callback
    }

}

class MessageVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: Message?,
        color: IColorGenerator?,
        callback: AdapterClickListener<Pair<Int?,TextView>>?
    ) {
        itemView.tv_title.text = item?.sender
        itemView.tv_date.text = item?.date
        itemView.tv_content.text = item?.content
        itemView.avatar_image.apply {
            GlideApp.with(context).load(color?.getColor()).into(this)
        }
        itemView.tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(item?.sender)
        itemView.setOnClickListener {
            ViewCompat.setTransitionName(it.tv_title,it.context.getString(R.string.message_title_transition))
            callback?.onClick(Pair(item?.uid,it.tv_title)) }
    }

}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}