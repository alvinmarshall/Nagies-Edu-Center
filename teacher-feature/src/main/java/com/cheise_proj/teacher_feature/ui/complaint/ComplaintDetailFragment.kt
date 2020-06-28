package com.cheise_proj.teacher_feature.ui.complaint


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.viewmodel.message.ComplaintViewModel
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.utils.GetFirstLettersOfStringsImpl
import kotlinx.android.synthetic.main.fragment_complaint_detail.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ComplaintDetailFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var colorGenerator: IColorGenerator
    private var complaintData: Complaint? = null

    private lateinit var viewModel: ComplaintViewModel

    private val args: ComplaintDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaint_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.complaint_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reply -> {
                navigateToReplyMessage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToReplyMessage() {
        val action =
            ComplaintDetailFragmentDirections.actionComplaintDetailFragmentToSendMessageFragment(
                complaintData
            )
        findNavController().navigate(action)
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, factory)[ComplaintViewModel::class.java]
        subScribeObserver()
    }

    private fun subScribeObserver() {
        viewModel.getComplaint(args.complaint.toString()).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> setComplaintView(it.data)
                STATUS.ERROR -> println("err ${it.message}")
            }
        })
    }

    private fun setComplaintView(data: Complaint?) {
        tv_title.apply {
            transitionName = getString(R.string.complaint_tile_transition)
            text = data?.sender
        }
        tv_date.text = data?.date
        tv_content.text = data?.content
        avatar_image.apply {
            GlideApp.with(context).load(colorGenerator.getColor()).into(this)
        }

        tv_icon_text.text = GetFirstLettersOfStringsImpl.getLetters(data?.sender)
        complaintData = data
    }

}
