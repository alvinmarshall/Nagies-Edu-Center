package com.cheise_proj.parent_feature.ui.teacher

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.PeopleAction
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.ui.teacher.adapter.TeacherAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.people.PeopleViewModel
import kotlinx.android.synthetic.main.teacher_fragment.*
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class TeacherFragment : BaseFragment() {

    companion object {
        fun newInstance() = TeacherFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: PeopleViewModel
    private lateinit var adapter: TeacherAdapter
    private lateinit var recyclerView: RecyclerView

    private val adapterClickListener = object : AdapterClickListener<Pair<PeopleAction, People>> {
        override fun onClick(data: Pair<PeopleAction, People>?) {
            data?.apply {
                with(data.second) {
                    when (data.first) {
                        PeopleAction.CALL -> {
                            Timber.i("PeopleAction.CALL")
                            context?.startActivity(
                                Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.parse("tel:$contact")
                                )
                            )
                        }
                        PeopleAction.SMS -> {
                            Timber.i("PeopleAction.SMS")
                            context?.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.fromParts("sms", contact, null)
                                )
                            )
                        }
                        PeopleAction.COMPLAINT -> {
                            Timber.i("PeopleAction.COMPLAINT ")
                            AlertDialog.Builder(context).setMessage("Not Implemented").show()
                        }
                    }
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teacher_fragment, container, false)
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
        adapter = TeacherAdapter()
        adapter.setAdapterClickListener(adapterClickListener)
        viewModel = ViewModelProvider(this, factory)[PeopleViewModel::class.java]
        val handler = Handler(Looper.getMainLooper())
        Timber.i("postDelayed $DELAY_HANDLER")
        handler.postDelayed({ subscribeObserver() }, DELAY_HANDLER)
    }

    private fun subscribeObserver() {
        viewModel.getPeopleList("teacher").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> Timber.i("loading...")
                STATUS.SUCCESS -> {
                    hideLoadingProgress()
                    it.data?.let { data ->
                        if (data.isEmpty()) {
                            showNoDataAlert()
                        } else {
                            showNoDataAlert(false)
                        }
                    }
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                }
                STATUS.ERROR -> {
                    toast("${it.message}")
                    Timber.w("${it.message}")
                }
            }
        })
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

}
