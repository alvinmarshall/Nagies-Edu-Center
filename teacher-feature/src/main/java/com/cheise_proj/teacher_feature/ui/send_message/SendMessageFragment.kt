package com.cheise_proj.teacher_feature.ui.send_message


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
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
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IColorGenerator
import com.cheise_proj.presentation.utils.InputValidation
import com.cheise_proj.presentation.viewmodel.message.MessageViewModel
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.ui.send_message.adapter.MessageListAdapter
import kotlinx.android.synthetic.main.fragment_send_message.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SendMessageFragment : BaseFragment() {
    private val args: SendMessageFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var generator: IColorGenerator

    @Inject
    lateinit var inputValidation: InputValidation

    private lateinit var viewModel: MessageViewModel
    private lateinit var adapter: MessageListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var peopleDialogFragment: PeopleDialogFragment
    private var receiver = ""
    private var identifier = ""


    private val peopleCallbackListener = object : AdapterClickListener<People> {
        override fun onClick(data: People?) {
            receiver = data?.name ?: ""
            identifier = data?.refNo ?: ""
            peopleDialogFragment.dismiss()
            toast("Recipient set")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MessageListAdapter()
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
        showRecipientAlert(content, receiver, identifier)

    }

    private fun showRecipientAlert(content: String, receiver: String, identifier: String) {
        AlertDialog.Builder(context!!)
            .setTitle("Recipient Alert")
            .setMessage("Your message recipient is set to \n${getCurrentRecipient()}. \nDo you want to continue ?")
            .setPositiveButton("ok") { dialog, _ ->
                subscribeSendMessage(content, receiver, identifier)
                dialog.dismiss()
            }
            .setNegativeButton("cancel", null)
            .create()
            .show()
    }

    private fun subscribeSendMessage(content: String, receiver: String, identifier: String) {
        viewModel.sendMessage(content, receiver, identifier)
            .observe(viewLifecycleOwner, Observer {
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
                                subscribeMessageObserver()
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
        if (TextUtils.isEmpty(receiver)) {
            return "Entire class"
        }
        return receiver
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        peopleDialogFragment = PeopleDialogFragment()
        receiver = args.complaint?.studentName ?: ""
        identifier = args.complaint?.refNo ?: ""
        adapter.apply {
            setGeneratorColor(generator)
        }
        configViewModel()
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, factory)[MessageViewModel::class.java]
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ subscribeMessageObserver() }, DELAY_HANDLER)
    }

    private fun subscribeMessageObserver() {
        viewModel.getSentMessages().observe(viewLifecycleOwner, Observer {
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
