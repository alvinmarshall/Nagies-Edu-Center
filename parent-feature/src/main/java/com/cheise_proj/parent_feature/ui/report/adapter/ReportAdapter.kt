package com.cheise_proj.parent_feature.ui.report.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.utils.GetFirstLettersOfStringsImpl
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.model.files.Report
import com.cheise_proj.presentation.utils.IColorGenerator
import kotlinx.android.synthetic.main.list_report.view.*

class ReportAdapter :
    ListAdapter<Report, ReportAdapter.ReportVh>(ReportDiffCallback()) {
    private var adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>? = null
    private var genColor: IColorGenerator? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportVh {
        return ReportVh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_report,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportVh, position: Int) {
        holder.bind(getItem(position), adapterClickListener, genColor)
    }

    fun setAdapterClickListener(callback: AdapterClickListener<Pair<String?, Boolean>>) {
        adapterClickListener = callback
    }

    fun setRandomColor(color: IColorGenerator) {
        genColor = color
    }

    inner class ReportVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Report?,
            adapterClickListener: AdapterClickListener<Pair<String?, Boolean>>?,
            genColor: IColorGenerator?
        ) {
            val subHeader = "Terminal Report"
            val date = "Issued: ${item?.date}"
            val teacher = "From: ${item?.teacherName}"

            itemView.tv_header.text = item?.studentName
            itemView.tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(item?.studentName)
            itemView.tv_sub_header.text = subHeader
            itemView.avatar_image.setImageDrawable(genColor?.getColor())
            itemView.supporting_text.text = teacher
            itemView.supporting_text2.text = date

            itemView.media_image.apply {
                GlideApp.with(this.context).load(item?.photo).centerCrop().into(this)
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

class ReportDiffCallback : DiffUtil.ItemCallback<Report>() {
    override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
        return oldItem == newItem
    }
}

