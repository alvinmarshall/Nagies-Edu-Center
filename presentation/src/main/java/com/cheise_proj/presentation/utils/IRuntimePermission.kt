package com.cheise_proj.presentation.utils

import android.app.Activity


interface IRuntimePermission {
    fun shouldAskPermission(): Boolean
    fun shouldAskPermission(permission: String): Boolean
    fun checkPermission(permission: String, listener: PermissionAskListener)
    fun setActivity(activity: Activity)

}

interface PermissionAskListener {
    fun onPermissionPreviouslyDenied()
    fun onNeedPermission()
    fun onPermissionDisabled()
    fun onPermissionGranted()
}
