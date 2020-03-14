package com.cheise_proj.teacher_feature.ui.explorer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cheise_proj.common_module.ExplorersAction
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.files.IFiles
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import kotlinx.android.synthetic.main.list_file_explorer.view.*

class ExplorerAdapter :
    ListAdapter<IFiles, ExplorerAdapter.ExplorerVh>(ExplorerDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<ExplorersAction, IFiles>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerVh {
        return ExplorerVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_file_explorer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExplorerVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener)
    }

    internal fun setAdapterClickListener(callback: AdapterClickListener<Pair<ExplorersAction, IFiles>>) {
        adapterClickListener = callback
    }

    inner class ExplorerVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: IFiles?,
            adapterClickListener: AdapterClickListener<Pair<ExplorersAction, IFiles>>?
        ) {
            val sender = "uploaded by: ${item?.teacherName}"
            val date = "Date: ${item?.date}"
            itemView.apply {
                tv_header.text = sender
                tv_sub_header.text = date
                btn_delete.setOnClickListener {
                    adapterClickListener?.onClick(Pair(ExplorersAction.DELETE, item!!))
                }
                btn_download.setOnClickListener {
                    adapterClickListener?.onClick(Pair(ExplorersAction.DOWNLOAD, item!!))
                }
                setOnClickListener {
                    adapterClickListener?.onClick(
                        Pair(
                            ExplorersAction.VIEW,
                            item!!
                        )
                    )
                }

                GlideApp.with(context).load(item?.photo).centerCrop().into(avatar_image)
            }
        }
    }
}

class ExplorerDiffCallback : DiffUtil.ItemCallback<IFiles>() {
    override fun areItemsTheSame(oldItem: IFiles, newItem: IFiles): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IFiles, newItem: IFiles): Boolean {
        return oldItem.id == newItem.id && oldItem.date == newItem.date
    }
}