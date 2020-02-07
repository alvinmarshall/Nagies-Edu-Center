package com.cheise_proj.parent_feature.ui.timetable.adapter

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
import com.cheise_proj.presentation.model.files.TimeTable
import kotlinx.android.synthetic.main.list_files.view.*

class TimeTableAdapter :
    ListAdapter<TimeTable, TimeTableAdapter.TimeTableVh>(TimeTableDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableVh {
        return TimeTableVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_files,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimeTableVh, position: Int) {
        holder.bind(getItem(position),adapterClickListener)
    }

    fun setAdapterClickListener(callback: AdapterClickListener<Pair<String?, Boolean>>) {
        adapterClickListener = callback
    }


    inner class TimeTableVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: TimeTable?,
            adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>?
        ) {
            val header = "From: ${item?.teacherName}"
            val subHeader = "Date: ${item?.date}"
            itemView.tv_header.text = header
            itemView.tv_sub_header.text = subHeader
            itemView.avatar_image.apply {
                GlideApp.with(this.context).load(item?.photo).centerCrop().diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
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

class TimeTableDiffCallback : DiffUtil.ItemCallback<TimeTable>() {
    override fun areItemsTheSame(oldItem: TimeTable, newItem: TimeTable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeTable, newItem: TimeTable): Boolean {
        return oldItem == newItem
    }
}

