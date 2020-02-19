package com.cheise_proj.teacher_feature.ui.complaint.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.utils.GetFirstLettersOfStringsImpl
import kotlinx.android.synthetic.main.list_message.view.*

class ComplaintAdapter :
    ListAdapter<Complaint, ComplaintAdapter.ComplaintVh>(ComplaintDiffCallback()) {
    private var adapterClickListener:AdapterClickListener<Pair<Int?, TextView>>? = null
    private var generatorColor: IColorGenerator? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintVh {
        return ComplaintVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_message,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ComplaintVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener, generatorColor)
    }

    internal fun setAdapterClickListener(callback: AdapterClickListener<Pair<Int?, TextView>>) {
        adapterClickListener = callback
    }

    internal fun setGeneratorColor(colorGenerator: IColorGenerator) {
        generatorColor = colorGenerator
    }

    inner class ComplaintVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Complaint?,
            adapterClickListener: AdapterClickListener<Pair<Int?, TextView>>?,
            generatorColor: IColorGenerator?
        ) {
            item?.let { complaint ->
                with(complaint) {
                    itemView.tv_title.text = sender
                    itemView.tv_date.text = date
                    itemView.tv_content.text = content
                    itemView.tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(sender)
                    itemView.avatar_image.apply {
                        GlideApp.with(context).load(generatorColor?.getColor()).into(this)
                    }
                    itemView.setOnClickListener {
                        ViewCompat.setTransitionName(
                            it.tv_title,
                            it.context.getString(R.string.complaint_tile_transition)
                        )
                        adapterClickListener?.onClick(Pair(id,it.tv_title))
                    }
                }
            }
        }
    }
}

class ComplaintDiffCallback : DiffUtil.ItemCallback<Complaint>() {
    override fun areItemsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
        return oldItem == newItem
    }
}
