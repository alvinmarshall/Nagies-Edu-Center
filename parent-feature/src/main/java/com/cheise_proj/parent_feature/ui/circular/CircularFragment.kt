package com.cheise_proj.parent_feature.ui.circular

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.di.GlideApp
import com.cheise_proj.parent_feature.ui.circular.adapter.CircularAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.files.Circular
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.presentation.viewmodel.files.CircularViewModel
import kotlinx.android.synthetic.main.circular_fragment.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class CircularFragment : BaseFragment() {

    companion object {
        fun newInstance() = CircularFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: CircularViewModel
    private lateinit var adapter: CircularAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.circular_fragment, container, false)
    }

    private val adapterClickListener = object : AdapterClickListener<String?> {
        override fun onClick(data: String?) {
            setDialogPreview(data)
        }
    }

    private fun setDialogPreview(url: String?) {
        val lay = LayoutInflater.from(context)
        val view = lay.inflate(R.layout.prev_avatar, null)
        val img = view.findViewById<ImageView>(R.id.avatar_image)
        val dialogBuilder = AlertDialog.Builder(context)
        GlideApp.with(context!!).load(url).centerCrop().into(img)
        dialogBuilder.setCancelable(true)
        dialogBuilder.setView(view)
        dialogBuilder.create().show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = recycler_view
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            hasFixedSize()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = CircularAdapter()
        adapter.apply {
            setAdapterCallback(adapterClickListener)
        }
        viewModel = ViewModelProvider(this, factory).get(CircularViewModel::class.java)
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }!!
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.getCirculars().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> {
                    hideLoadingProgress()
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                    sharedViewModel.setBadgeValue(Pair(R.id.circularFragment2, it?.data?.size))
                }
                STATUS.ERROR -> {
                    hideLoadingProgress()
                    println("err ${it.message}")
                }
            }
        })
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

}
