package com.cheise_proj.parent_feature.ui.teacher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.PeopleAction
import com.cheise_proj.parent_feature.R
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.people.People
import kotlinx.android.synthetic.main.list_teacher.view.*

class TeacherAdapter :
    ListAdapter<People, TeacherAdapter.TeacherVh>(TeacherDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<PeopleAction, People>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherVh {
        return TeacherVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_teacher,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: TeacherVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener)
    }

    fun setAdapterClickListener(callback: AdapterClickListener<Pair<PeopleAction, People>>) {
        adapterClickListener = callback
    }

    inner class TeacherVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: People?,
            adapterClickListener: AdapterClickListener<Pair<PeopleAction, People>>?
        ) {
            itemView.tv_header.text = item?.name
            itemView.tv_sub_header.text = item?.gender
            itemView.avatar_image.apply {
                GlideApp.with(this.context)
                    .load(item?.photo)
                    .centerCrop()
                    .into(this)
            }
            itemView.btn_item_call.setOnClickListener {
                adapterClickListener?.onClick(Pair(PeopleAction.CALL, item!!))
            }
            itemView.btn_item_message.setOnClickListener {
                adapterClickListener?.onClick(Pair(PeopleAction.SMS, item!!))
            }
            itemView.setOnClickListener {
                adapterClickListener?.onClick(Pair(PeopleAction.COMPLAINT, item!!))
            }
        }
    }
}

class TeacherDiffCallback : DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem == newItem
    }
}