package com.cheise_proj.presentation.utils

import android.content.Context


interface IRuntimePermission {
    fun askForPermissions(): Boolean
    fun isPermissionsAllowed(): Boolean
    fun initPermissionValues(
        context: Context,
        permission: Array<String>,
        requestCode: Int,
        dialogListener: PermissionDialogListener
    )
}

interface PermissionDialogListener {
    fun showStorageRationalDialog()
}
