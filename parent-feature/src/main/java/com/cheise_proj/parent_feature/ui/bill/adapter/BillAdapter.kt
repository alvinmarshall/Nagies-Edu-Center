package com.cheise_proj.parent_feature.ui.bill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.files.Bill
import kotlinx.android.synthetic.main.list_files.view.*

class BillAdapter :
    ListAdapter<Bill, BillAdapter.BillVh>(BillDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillVh {
        return BillVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_files,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BillVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener)
    }

    fun setAdapterClickListener(callback: AdapterClickListener<Pair<String?, Boolean>>) {
        adapterClickListener = callback
    }

    inner class BillVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Bill?, adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>?) {
            val header = "From: ${item?.teacherName}"
            val subHeader = "Date: ${item?.date}"
            itemView.tv_header.text = header
            itemView.tv_sub_header.text = subHeader
            itemView.avatar_image.apply {
                GlideApp.with(this.context).load(item?.photo).thumbnail(0.5f).centerCrop()
                    .diskCacheStrategy(
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

class BillDiffCallback : DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem == newItem
    }
}