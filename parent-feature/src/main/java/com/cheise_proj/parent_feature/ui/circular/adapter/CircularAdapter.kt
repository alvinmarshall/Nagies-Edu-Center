package com.cheise_proj.parent_feature.ui.circular.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.di.GlideApp
import com.cheise_proj.presentation.model.files.Circular
import kotlinx.android.synthetic.main.list_files.view.*

class CircularAdapter :
    ListAdapter<Circular, CircularAdapter.CircularVh>(CircularDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircularVh {
        return CircularVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_files,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CircularVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener)
    }

    fun setAdapterCallback(callback: AdapterClickListener<Pair<String?, Boolean>>) {
        adapterClickListener = callback
    }

    inner class CircularVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Circular?,
            adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>?
        ) {
            val header = "From: ${item?.teacherName}"
            val subHeader = "Date: ${item?.date}"
            itemView.tv_header.text = header
            itemView.tv_sub_header.text = subHeader
            itemView.avatar_image.apply {
                GlideApp.with(this.context).load(item?.photo).centerCrop().diskCacheStrategy(
                    DiskCacheStrategy.ALL
                ).into(this)
            }
            itemView.setOnClickListener {
                adapterClickListener?.onClick(Pair(item?.photo, false))
            }
            itemView.btn_download.setOnClickListener {
                adapterClickListener?.onClick(Pair(item?.photo, true))
            }
        }

    }
}

class CircularDiffCallback : DiffUtil.ItemCallback<Circular>() {
    override fun areItemsTheSame(oldItem: Circular, newItem: Circular): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Circular, newItem: Circular): Boolean {
        return oldItem == newItem
    }
}

