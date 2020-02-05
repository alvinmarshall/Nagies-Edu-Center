package com.cheise_proj.parent_feature.ui.circular

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.common_module.REQUEST_EXTERNAL_STORAGE
import com.cheise_proj.parent_feature.AdapterClickListener
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.di.GlideApp
import com.cheise_proj.parent_feature.ui.circular.adapter.CircularAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IDownloadFile
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionAskListener
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

    @Inject
    lateinit var downloadService: IDownloadFile

    @Inject
    lateinit var permission: IRuntimePermission

    private lateinit var viewModel: CircularViewModel
    private lateinit var adapter: CircularAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedViewModel: SharedViewModel
    private var downloadData: Pair<String?, Boolean>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.circular_fragment, container, false)
    }

    private val adapterClickListener = object : AdapterClickListener<Pair<String?, Boolean>> {
        override fun onClick(data: Pair<String?, Boolean>?) {
            when (data?.second) {
                // download event
                true -> {
                    downloadData = data
                    permission.checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        listener
                    )
                }
                // view event
                false -> {
                    setDialogPreview(data.first)
                }
            }
        }
    }

    private fun prepareToDownload(data: Pair<String?, Boolean>?) {
        val downloadId = downloadService.startDownload(data?.first)
        toast("download id $downloadId started")
    }

    private fun setDialogPreview(url: String?) {
        val lay = LayoutInflater.from(context)
        val root: ViewGroup? = null
        val view = lay.inflate(R.layout.prev_avatar, root)
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
        downloadService.registerDownloadBroadCast()
        this.activity?.let { permission.setActivity(it) }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = CircularAdapter()
        adapter.apply {
            setAdapterCallback(adapterClickListener)
        }
        viewModel = ViewModelProvider(this, factory).get(CircularViewModel::class.java)
        val handler = Handler(Looper.getMainLooper())
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }!!
        handler.postDelayed({ subscribeObserver() }, DELAY_HANDLER)

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

    //region permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareToDownload(downloadData)
                } else {
                    toast("Permission Denied")
                }
            }
        }
    }

    val listener = object : PermissionAskListener {
        override fun onPermissionPreviouslyDenied() {
            showStorageRational(
                getString(R.string.permission_denied),
                getString(R.string.permission_storage_explained)
            )
        }

        override fun onNeedPermission() {
            activity?.let { activity ->
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), REQUEST_EXTERNAL_STORAGE
                )
            }
        }

        override fun onPermissionDisabled() {
            dialogForSettings(
                getString(R.string.permission_denied),
                getString(R.string.permission_storage_message)
            )
        }

        override fun onPermissionGranted() {
            prepareToDownload(downloadData)
        }
    }
    //endregion
}
