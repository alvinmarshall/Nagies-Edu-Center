package com.cheise_proj.parent_feature.ui.compose.adapter

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
import com.cheise_proj.presentation.model.people.People
import kotlinx.android.synthetic.main.list_teacher_basic.view.*

class TeacherBasicAdapter :
    ListAdapter<People, TeacherBasicAdapter.TeacherBasicVh>(TeacherBasicDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<People>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherBasicVh {
        return TeacherBasicVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_teacher_basic,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TeacherBasicVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener)
    }


    internal fun setAdapterClickListener(callback: AdapterClickListener<People>) {
        adapterClickListener = callback
    }

    inner class TeacherBasicVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: People?, adapterClickListener: AdapterClickListener<People>?) {
            val level = "Level: ${item?.username}"
            val ref = "Ref: ${item?.refNo}"
            val gender = "Gender: ${item?.gender}"
            itemView.apply {
                item_name.text = item?.name
                item_ref.text = ref
                item_index.text = level
                item_gender.text = gender
                GlideApp.with(context).load(item?.photo).centerCrop().diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).into(item_img)
                btn_select.setOnClickListener { adapterClickListener?.onClick(item) }
            }

        }
    }
}

class TeacherBasicDiffCallback : DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem == newItem
    }
}