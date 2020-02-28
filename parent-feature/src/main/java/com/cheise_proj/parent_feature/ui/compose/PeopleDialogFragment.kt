package com.cheise_proj.parent_feature.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.ui.compose.adapter.TeacherBasicAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.people.PeopleViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_people_dialog.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PeopleDialogFragment : DaggerDialogFragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: PeopleViewModel
    private lateinit var adapter: TeacherBasicAdapter
    private lateinit var recyclerView: RecyclerView
    private var peopleClickListener: AdapterClickListener<People>? = null



    fun setDialogCallback(callback: AdapterClickListener<People>) {
        peopleClickListener = callback
    }

    private val adapterClickListener = object : AdapterClickListener<People> {
        override fun onClick(data: People?) {
            data?.let {
                peopleClickListener?.onClick(data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = recycler_view
        recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeacherBasicAdapter()
        adapter.apply {
            setAdapterClickListener(adapterClickListener)
        }
        configViewModel()
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, factory)[PeopleViewModel::class.java]
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.getPeopleList("teacher").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> {
                    hideProgress()
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                }
                STATUS.ERROR -> println("err ${it.message}")
            }
        })
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
