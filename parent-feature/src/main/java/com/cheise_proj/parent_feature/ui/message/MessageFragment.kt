package com.cheise_proj.parent_feature.ui.message

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.ui.message.adapter.MessageAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import kotlinx.android.synthetic.main.message_fragment.*
import timber.log.Timber
import javax.inject.Inject

class MessageFragment : BaseFragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var colorGenerator: IColorGenerator

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }


    private val adapterClickListener = object : AdapterClickListener<Pair<Int?, TextView>> {
        override fun onClick(data: Pair<Int?, TextView>?) {
            data?.let {
                val imageView: TextView = it.second
                val extra =
                    FragmentNavigatorExtras(imageView to getString(R.string.message_title_transition))
                val action =
                    MessageFragmentDirections.actionMessageFragmentToMessageDetailFragment(
                        identifier = it.first ?: 0
                    )
                findNavController().navigate(action, extra)
            }
        }
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
        adapter = MessageAdapter()
        adapter.setAdapterClickListener(adapterClickListener)
        adapter.setGeneratorColor(colorGenerator)
        configureViewModel()
    }

    private fun configureViewModel() {
        messageViewModel = ViewModelProvider(this, factory)[MessageViewModel::class.java]
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }!!
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ subscribeObserver() }, DELAY_HANDLER)
//        subscribeObserver()
    }

    private fun subscribeObserver() {

        messageViewModel.getMessages().observe(viewLifecycleOwner, Observer {
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
                    sharedViewModel.setBadgeValue(Pair(R.id.messageFragment, it.data?.size))
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                }
                STATUS.ERROR -> {
                    hideLoadingProgress()
                    Timber.w("error ${it.message}")
                }
            }
        })
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }


}
