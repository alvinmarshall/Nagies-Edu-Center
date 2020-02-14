package com.cheise_proj.parent_feature.ui.message


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.utils.GetFirstLettersOfStringsImpl
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.message.Message
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import kotlinx.android.synthetic.main.fragment_message_detail.*
import javax.inject.Inject


class MessageDetailFragment : BaseFragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var colorGenerator: IColorGenerator
    private lateinit var messageViewModel: MessageViewModel
    private val args: MessageDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewModel(args.identifier)
    }

    private fun configureViewModel(identifier: Int) {
        messageViewModel = ViewModelProvider(this, factory)[MessageViewModel::class.java]
        messageViewModel.getMessage(identifier).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> setMessageViewDetail(it.data)
                STATUS.ERROR -> println(it.message)
            }
        })
    }

    private fun setMessageViewDetail(data: Message?) {
        tv_title.apply {
            transitionName = getString(R.string.message_title_transition)
            text = data?.sender
        }
        tv_date.text = data?.date
        tv_content.text = data?.content
        avatar_image.apply {
            GlideApp.with(context).load(colorGenerator.getColor()).into(this)
        }

        tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(data?.sender)
    }


}
