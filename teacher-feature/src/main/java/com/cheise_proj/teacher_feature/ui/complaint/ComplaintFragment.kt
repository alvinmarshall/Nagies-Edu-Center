package com.cheise_proj.teacher_feature.ui.complaint


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.presentation.viewmodel.message.ComplaintViewModel
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.ui.complaint.adapter.ComplaintAdapter
import kotlinx.android.synthetic.main.fragment_complaint.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ComplaintFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var generator: IColorGenerator

    private lateinit var viewModel: ComplaintViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ComplaintAdapter

    private val adapterClickListener = object : AdapterClickListener<Pair<Int?, TextView>> {
        override fun onClick(data: Pair<Int?, TextView>?) {
            data?.run {
                val textView: TextView = second
                val extra =
                    FragmentNavigatorExtras(textView to getString(R.string.complaint_tile_transition))
                val action =
                    ComplaintFragmentDirections.actionComplaintFragmentToComplaintDetailFragment(
                        first ?: 0
                    )
                findNavController().navigate(action,extra)
            }
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaint, container, false)
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
        initAdapter()
        viewModel = ViewModelProvider(this, factory)[ComplaintViewModel::class.java]
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }!!
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({subscribeObserver()}, DELAY_HANDLER)
//        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.getComplaintList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> {
                    hideProgress()
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                    sharedViewModel.setBadgeValue(Pair(R.id.complaintFragment, it.data?.size))
                }
                STATUS.ERROR -> {
                    hideProgress()
                    println("err ${it.message}")
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = ComplaintAdapter()
        adapter.apply {
            setAdapterClickListener(adapterClickListener)
            setGeneratorColor(generator)
        }
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
