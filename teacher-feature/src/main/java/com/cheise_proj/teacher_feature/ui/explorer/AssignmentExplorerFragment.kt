package com.cheise_proj.teacher_feature.ui.explorer


import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
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
import com.cheise_proj.common_module.ExplorersAction
import com.cheise_proj.common_module.REQUEST_EXTERNAL_STORAGE
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.files.IFiles
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IDownloadFile
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionDialogListener
import com.cheise_proj.presentation.viewmodel.files.AssignmentViewModel
import com.cheise_proj.teacher_feature.AdapterClickListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.ui.explorer.adapter.ExplorerAdapter
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.fragment_assignment_explorer.*
import kotlinx.android.synthetic.main.nav_header_teacher_navigation.view.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AssignmentExplorerFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var downloadService: IDownloadFile

    @Inject
    lateinit var permission: IRuntimePermission

    private lateinit var viewModel: AssignmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExplorerAdapter
    private var downloadData = ""

    private val adapterClickListener =
        object : AdapterClickListener<Pair<ExplorersAction, IFiles>> {
            override fun onClick(data: Pair<ExplorersAction, IFiles>?) {
                data?.run {
                    when (first) {
                        ExplorersAction.VIEW -> setDialogPreview(second.photo)
                        ExplorersAction.DOWNLOAD -> {
                            downloadData = data.second.photo ?: ""
                            startDownload(downloadData)
                        }
                        ExplorersAction.DELETE -> toast("delete file")
                    }
                }
            }
        }

    private fun startDownload(downloadData: String) {
        if (permission.askForPermissions()) {
            prepareToDownload(downloadData)
        }
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = recycler_view
        recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        downloadService.registerDownloadBroadCast()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPermission()
        adapter = ExplorerAdapter()
        adapter.apply {
            setAdapterClickListener(adapterClickListener)
        }
        configViewModel()
    }

    private fun initPermission() {
        permission.initPermissionValues(
            context!!,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_EXTERNAL_STORAGE, permissionDialogListener
        )
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, factory)[AssignmentViewModel::class.java]
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ subscribeObserver() }, DELAY_HANDLER)
    }

    private fun subscribeObserver() {
        viewModel.getAssignments().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> {
                    hideProgress()
                    adapter.submitList(it.data)
                    recyclerView.adapter = adapter
                }
                STATUS.ERROR -> {
                    hideProgress()
                    println("error ${it.message}")
                }
            }
        })
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun prepareToDownload(url: String?) {
        val downloadId = downloadService.startDownload(url)
        toast("download id $downloadId started")
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
                } else {
                    permission.askForPermissions()
                }
                return
            }
        }
    }
    //endregion

}
