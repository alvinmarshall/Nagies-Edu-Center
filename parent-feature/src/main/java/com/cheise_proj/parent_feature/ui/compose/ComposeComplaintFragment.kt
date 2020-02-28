package com.cheise_proj.parent_feature.ui.compose

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.ui.compose.adapter.ComplaintListAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.utils.InputValidation
import com.cheise_proj.presentation.viewmodel.message.ComplaintViewModel
import kotlinx.android.synthetic.main.fragment_compose_complaint.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ComposeComplaintFragment : BaseFragment() {
    private val args: ComposeComplaintFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var generator: IColorGenerator

    @Inject
    lateinit var inputValidation: InputValidation

    private lateinit var viewModel: ComplaintViewModel
    private lateinit var adapter: ComplaintListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var peopleDialogFragment: PeopleDialogFragment
    private var identifier = ""


    private val peopleCallbackListener = object : AdapterClickListener<People> {
        override fun onClick(data: People?) {
            identifier = data?.name ?: ""
            peopleDialogFragment.dismiss()
            toast("Recipient set")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose_complaint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ComplaintListAdapter()
        recyclerView = recycler_view
        val layoutChangeListener =
            View.OnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                if (bottom < oldBottom) {
                    if (adapter.itemCount == 0) return@OnLayoutChangeListener
                    recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
                }
            }
        recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addOnLayoutChangeListener(layoutChangeListener)
        }
        btn_send_message.setOnClickListener {
            sendingMessage()
        }
        btn_message_receiver.setOnClickListener {
            showPeopleDialog()
        }
    }

    private fun showPeopleDialog() {
        peopleDialogFragment.setDialogCallback(peopleCallbackListener)
        peopleDialogFragment.show(parentFragmentManager, "people_dialog")
    }

    private fun sendingMessage() {
        val content = et_content.text.toString()

        if (TextUtils.isEmpty(content)) {
            toast("provide a content")
            return
        }
        if (TextUtils.isEmpty(identifier)) {
            toast("Add a recipient")
            return
        }
        showRecipientAlert(content, identifier)

    }

    private fun showRecipientAlert(content: String, identifier: String) {
        AlertDialog.Builder(context!!)
            .setTitle("Recipient Alert")
            .setMessage("Your message recipient is set to \n${getCurrentRecipient()}. \nDo you want to continue ?")
            .setPositiveButton("ok") { dialog, _ ->
                subscribeSendComplaint(content, identifier)
                dialog.dismiss()
            }
            .setNegativeButton("cancel", null)
            .create()
            .show()
    }

    private fun subscribeSendComplaint(content: String, identifier: String) {
        viewModel.sendComplaint(content, identifier).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> {
                    showLoadingProgress()
                    println("sending message...")
                }
                STATUS.SUCCESS -> {
                    it.data?.let { isSuccess ->
                        if (isSuccess) {
                            showLoadingProgress(false)
                            et_content.text.clear()
                            subscribeComplaintObserver()
                        }
                    }
                }
                STATUS.ERROR -> {
                    showLoadingProgress(false)
                    toast("error ${it.message}")
                }
            }
        })
    }

    private fun getCurrentRecipient(): String {
        return identifier
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        peopleDialogFragment = PeopleDialogFragment()
        identifier = args.message?.sender ?: ""
        adapter.apply {
            setGeneratorColor(generator)
        }
        configViewModel()
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, factory)[ComplaintViewModel::class.java]
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ subscribeComplaintObserver() }, DELAY_HANDLER)
    }

    private fun subscribeComplaintObserver() {
        viewModel.getSentComplaint().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> showLoadingProgress()
                STATUS.SUCCESS -> {
                    showLoadingProgress(false)
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                }
                STATUS.ERROR -> {
                    showLoadingProgress(false)
                    println("err ${it.message}")
                }
            }
        })
    }

    private fun showLoadingProgress(show: Boolean = true) {
        progressBar.isIndeterminate = show
    }

    override fun onPause() {
        inputValidation.hideKeyboard(root)
        super.onPause()
    }


}
