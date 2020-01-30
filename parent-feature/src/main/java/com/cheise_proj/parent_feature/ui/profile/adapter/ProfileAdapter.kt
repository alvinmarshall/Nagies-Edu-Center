package com.cheise_proj.parent_feature.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.R
import com.cheise_proj.presentation.model.vo.ProfileLabel
import kotlinx.android.synthetic.main.list_profile.view.*

class ProfileAdapter :
    ListAdapter<Pair<ProfileLabel, String?>, ProfileVh>(ProfileDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVh {
        return ProfileVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_profile,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileVh, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}

class ProfileVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: Pair<ProfileLabel, String?>?) {
        itemView.item_label.text = data?.first?.title
        itemView.item_info.text = data?.second
        data?.first?.icon?.let { itemView.item_drawable.setImageResource(it) }
    }
}

class ProfileDiffCallback : DiffUtil.ItemCallback<Pair<ProfileLabel, String?>>() {
    override fun areItemsTheSame(
        oldItem: Pair<ProfileLabel, String?>,
        newItem: Pair<ProfileLabel, String?>
    ): Boolean {
        return oldItem.second == newItem.second
    }

    override fun areContentsTheSame(
        oldItem: Pair<ProfileLabel, String?>,
        newItem: Pair<ProfileLabel, String?>
    ): Boolean {
        return oldItem == newItem
    }

}