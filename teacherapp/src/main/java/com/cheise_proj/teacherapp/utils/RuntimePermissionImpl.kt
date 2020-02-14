package com.cheise_proj.teacherapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionDialogListener
import javax.inject.Inject

class RuntimePermissionImpl @Inject constructor() : IRuntimePermission {
    private lateinit var mContext: Context
    private lateinit var permissions: Array<String>
    private lateinit var listener: PermissionDialogListener
    private var request = 0
    override fun askForPermissions(): Boolean {

        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as Activity, permissions[0])) {
                listener.showStorageRationalDialog()


            } else {
                ActivityCompat.requestPermissions(
                    mContext as Activity, permissions,
                    request
                )
            }
            return false
        }
        return true

    }

    override fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            mContext,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun initPermissionValues(
        context: Context,
        permission: Array<String>,
        requestCode: Int,
        dialogListener: PermissionDialogListener
    ) {
        mContext = context
        permissions = permission
        request = requestCode
        listener = dialogListener
    }

}