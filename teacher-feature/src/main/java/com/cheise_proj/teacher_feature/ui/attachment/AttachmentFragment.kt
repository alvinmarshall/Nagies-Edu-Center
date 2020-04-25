package com.cheise_proj.teacher_feature.ui.attachment


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cheise_proj.common_module.REQUEST_CAMERA
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.job.UploadAssignmentWorker
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionDialogListener
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.utils.RealPathUtil
import kotlinx.android.synthetic.main.fragment_attachment.*
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AttachmentFragment : BaseFragment() {

    @Inject
    lateinit var permission: IRuntimePermission
    private var captureImagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attachment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isBusy = false
        avatar_image.setOnClickListener {
            if (permission.askForPermissions()) {
                pickAnImage()
            }
        }
        btn_upload_file.setOnClickListener {
            if (TextUtils.isEmpty(captureImagePath)) {
                toast("no file selected")
                return@setOnClickListener
            }

            if (!isBusy) {
                toast("upload started")
                isBusy = true
                UploadAssignmentWorker.start(it.context, captureImagePath, R.id.attachmentFragment)
                    .observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer { worker ->
                            if (worker.state.isFinished) {
                                toast("upload complete")
                                isBusy = false
                            }
                        })
            } else {
                toast("System busy uploading...")
            }


        }
        btn_upload_file_report.setOnClickListener {
            if (TextUtils.isEmpty(captureImagePath)) {
                toast("no file selected")
                return@setOnClickListener
            }
            val action = AttachmentFragmentDirections.actionAttachmentFragmentToStudentFragment(
                captureImagePath
            )
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        permission.initPermissionValues(
            requireContext(),
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CAMERA, permissionDialogListener
        )
    }

    private fun getGalleryIntent(): Intent {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        return intent
    }

    private fun pickAnImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()
        val photoUri = photoFile?.let {
            FileProvider.getUriForFile(
                requireContext(), getString(R.string.file_provider_authority, context?.packageName),
                it
            )
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        val chooser = Intent.createChooser(getGalleryIntent(), "Select an option")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        startActivityForResult(chooser, REQUEST_CAMERA)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timestamp = SimpleDateFormat(
            getString(R.string.date_long_format),
            Locale.getDefault()
        ).format(Date())
        val storageDir: File? =
            File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir).apply {
            captureImagePath = this.absolutePath

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {

            if (data != null) {
                val selectedFile = data.data
                try {
                    selectedFile?.let { uri ->
                        captureImagePath = context?.let { RealPathUtil.getRealPath(it, uri) } ?: ""
                    }
                } catch (e: Exception) {

                    e.printStackTrace()
                }
                GlideApp.with(this).load(selectedFile).into(avatar_image)

                println("image uri $captureImagePath")
            } else {
                GlideApp.with(this).load(captureImagePath).into(avatar_image)
                println("image uri $captureImagePath")
            }
        }
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
            REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickAnImage()

                } else {
                    permission.askForPermissions()
                }
                return
            }
        }
    }
    //endregion


}
