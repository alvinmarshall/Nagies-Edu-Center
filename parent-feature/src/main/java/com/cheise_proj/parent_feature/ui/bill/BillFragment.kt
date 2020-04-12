package com.cheise_proj.parent_feature.ui.bill

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.common_module.REQUEST_EXTERNAL_STORAGE
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.ui.bill.adapter.BillAdapter
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IDownloadFile
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionDialogListener
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.presentation.viewmodel.files.BillViewModel
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.bill_fragment.*
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class BillFragment : BaseFragment() {

    companion object {
        fun newInstance() = BillFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var downloadService: IDownloadFile

    @Inject
    lateinit var permission: IRuntimePermission

    private lateinit var viewModel: BillViewModel
    private lateinit var adapter: BillAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedViewModel: SharedViewModel
    private var downloadData: Pair<String?, Boolean>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_fragment, container, false)
    }

    private val adapterClickListener = object : AdapterClickListener<Pair<String?, Boolean>> {
        override fun onClick(data: Pair<String?, Boolean>?) {
            when (data?.second) {
                // download event
                true -> {
                    Timber.i("download event")
                    downloadData = data
                    if (permission.askForPermissions()) {
                        prepareToDownload(downloadData)
                    }
                }
                // view event
                false -> {
                    Timber.i("view event")
                    setDialogPreview(data.first)
                }
            }
        }
    }

    private fun prepareToDownload(data: Pair<String?, Boolean>?) {
        val downloadId = downloadService.startDownload(data?.first)
        toast("download id $downloadId started")
        Timber.i("download id $downloadId started")
    }

    private fun setDialogPreview(url: String?) {
        val lay = LayoutInflater.from(context)
        val root: ViewGroup? = null
        val view = lay.inflate(R.layout.prev_avatar, root)
        val img = view.findViewById<TouchImageView>(R.id.avatar_image)
        val dialogBuilder = AlertDialog.Builder(context)
        GlideApp.with(context!!).load(url).centerCrop().into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                img.setImageDrawable(resource)
            }


        })
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
        Timber.i("registerDownloadBroadCast")
        downloadService.registerDownloadBroadCast()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        permission.initPermissionValues(
            context!!,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_EXTERNAL_STORAGE, permissionDialogListener
        )
        adapter = BillAdapter()
        adapter.apply {
            setAdapterClickListener(adapterClickListener)
        }
        viewModel = ViewModelProvider(this, factory).get(BillViewModel::class.java)
        val handler = Handler(Looper.getMainLooper())
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }!!
        Timber.i("postDelayed $DELAY_HANDLER")
        handler.postDelayed({ subscribeObserver() }, DELAY_HANDLER)

    }

    private fun subscribeObserver() {
        viewModel.getBills().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> Timber.i("loading...")
                STATUS.SUCCESS -> {
                    hideLoadingProgress()
                    it.data?.let { data ->
                        Timber.i("bill data $data")
                        if (data.isEmpty()) {
                            showNoDataAlert()
                            Timber.i("showNoDataAlert dialog")
                        } else {
                            showNoDataAlert(false)
                            Timber.i("hide showNoDataAlert dialog")
                        }
                    }
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                    Timber.i("setBadgeValue ${it?.data?.size}")
                    sharedViewModel.setBadgeValue(Pair(R.id.billFragment, it?.data?.size))
                }
                STATUS.ERROR -> {
                    hideLoadingProgress()
                    Timber.w("err ${it.message}")
                }
            }
        })
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    //region permission
    private val permissionDialogListener = object : PermissionDialogListener {
        override fun showStorageRationalDialog() {
            dialogForSettings(
                getString(R.string.permission_denied),
                getString(R.string.permission_storage_message)
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareToDownload(downloadData)
                    Timber.i("PERMISSION_GRANTED true")
                } else {
                    permission.askForPermissions()
                    Timber.i("PERMISSION_GRANTED false")
                }
                return
            }
        }
    }
    //endregion

}
