package com.cheise_proj.teacher_feature.ui.student


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.job.UploadReportWorker
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.people.PeopleViewModel
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.ui.student.adapter.StudentAdapter
import kotlinx.android.synthetic.main.fragment_student.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class StudentFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: PeopleViewModel
    private lateinit var adapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView

    private val args: StudentFragmentArgs by navArgs()

    private val adapterClickListener = object : AdapterClickListener<People> {
        override fun onClick(data: People?) {
            data?.let {
                showUploadDialog(data, args.filePath)
            }
        }
    }

    private fun showUploadDialog(
        data: People,
        path: String
    ) {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage("You're about to send a report to \n${data.name}, Do you wish to continue ?")
        builder.setPositiveButton("yes") { dialog, _ ->
            dialog.dismiss()
            UploadReportWorker.start(context!!, path, data.refNo, data.name)
                .observe(viewLifecycleOwner,
                    Observer {
                        if (it.state.isFinished) {
                            toast("upload complete")
                            activity?.onBackPressed()
                        }
                    })
        }
        builder.setNegativeButton("no", null)
        builder.setCancelable(false)
        builder.create().show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false)
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
        adapter = StudentAdapter()
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
        viewModel.getPeopleList("student").observe(viewLifecycleOwner, Observer {
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
