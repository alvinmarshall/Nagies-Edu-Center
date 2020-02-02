package com.cheise_proj.parent_feature.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import javax.inject.Inject

class MessageFragment : BaseFragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    @Inject lateinit var factory: ViewModelFactory
   private lateinit var messageViewModel: MessageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        messageViewModel = ViewModelProvider(this,factory)[MessageViewModel::class.java]
        messageViewModel.getMessages().observe(viewLifecycleOwner, Observer {
            when(it.status){
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> println("messages ${it.data}")
                STATUS.ERROR -> println("error ${it.message}")
            }
        })
    }


}
