package com.cheise_proj.teacher_feature.ui.video

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cheise_proj.presentation.job.UploadVideoWorker
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionDialogListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.utils.RealPathUtil
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_upload_video.*
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UploadVideoFragment : BaseFragment() {
    companion object {
        private const val REQUEST_VIDEO = 430
    }

    @Inject
    lateinit var permission: IRuntimePermission
    private var captureVideoPath = ""
    private var simpleExoPlayer: SimpleExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isBusy = false

        btn_upload_file.setOnClickListener {
            if (TextUtils.isEmpty(captureVideoPath)) {
                toast("no file selected")
                return@setOnClickListener
            }

            if (!isBusy) {
                toast("upload started")
                isBusy = true
                UploadVideoWorker.start(requireContext(), captureVideoPath, R.id.uploadVideoFragment)
                    .observe(viewLifecycleOwner,
                        Observer { worker ->
                            if (worker.state.isFinished) {
                                toast("upload complete")
                                isBusy = false
                            }
                        })
            } else {
                toast("System busy uploading...")
            }
        }
    }

    private fun initializePlayer(uri: Uri) {
        val mediaDataSourceFactory: DataSource.Factory
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        mediaDataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(requireContext(), "mediaPlayerSample"))
        val mediaSource =
            ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri)
        simpleExoPlayer?.prepare(mediaSource, false, false)
        simpleExoPlayer?.playWhenReady = true

        player_view.apply {
            setShutterBackgroundColor(Color.TRANSPARENT)
            player = simpleExoPlayer
            requestFocus()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        permission.initPermissionValues(
            requireContext(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_VIDEO, permissionDialogListener
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.upload_video_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_video -> {
                if (permission.askForPermissions()) {
                    openVideoGallery()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openVideoGallery() {
        val videoIntent = Intent()
        videoIntent.type = "video/*"
        videoIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(videoIntent, "Select video"), REQUEST_VIDEO)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO) {

            if (data != null) {
                val selectedFile = data.data
                try {
                    selectedFile?.let { uri ->
                        captureVideoPath = context?.let { RealPathUtil.getRealPath(it, uri) } ?: ""
                        initializePlayer(uri)
                    }
                } catch (e: Exception) {

                    e.printStackTrace()
                }

                println("video uri $captureVideoPath")
            } else {
                println("video uri $captureVideoPath")
            }
        }
    }

    override fun onPause() {
        simpleExoPlayer?.release()
        super.onPause()
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
            REQUEST_VIDEO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openVideoGallery()

                } else {
                    permission.askForPermissions()
                }
                return
            }
        }
    }
    //endregion

}
