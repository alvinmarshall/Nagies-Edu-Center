package com.cheise_proj.parent_feature.ui.compose.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.utils.GetFirstLettersOfStringsImpl
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.utils.IColorGenerator
import kotlinx.android.synthetic.main.list_message_sent.view.*

class ComplaintListAdapter :
    ListAdapter<Complaint, ComplaintListAdapter.SentComplaintVh>(SentCallback()) {
    private var generatorColor: IColorGenerator? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentComplaintVh {
        return SentComplaintVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_message_sent,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SentComplaintVh, position: Int) {
        holder.bind(getItem(position), generatorColor)
    }

    internal fun setGeneratorColor(colorGenerator: IColorGenerator) {
        generatorColor = colorGenerator
    }


    inner class SentComplaintVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Complaint,
            generatorColor: IColorGenerator?
        ) {
            val sender = "You"
            itemView.apply {
                tv_sender.text = sender
                tv_sender_content.text = item.content
                tv_sender_time.text = item.date
                tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(sender)
                avatar_image.apply {
                    GlideApp.with(context).load(generatorColor?.getColor()).into(this)
                }
            }
        }
    }

}

class SentCallback : DiffUtil.ItemCallback<Complaint>() {
    override fun areItemsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
        return oldItem == newItem
    }
}
