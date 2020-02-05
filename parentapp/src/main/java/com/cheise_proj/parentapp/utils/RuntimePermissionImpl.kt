package com.cheise_proj.parentapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cheise_proj.presentation.utils.IRuntimePermission
import com.cheise_proj.presentation.utils.PermissionAskListener
import javax.inject.Inject
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.cheise_proj.parentapp.preference.PreferenceImpl

class RuntimePermissionImpl @Inject constructor(
    private val context: Context,
    private val prefs: PreferenceImpl
) : IRuntimePermission {
    private lateinit var mActivity: Activity
    override fun shouldAskPermission(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    }


    override fun shouldAskPermission(permission: String): Boolean {
        if (shouldAskPermission()) {
            val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    override fun checkPermission(permission: String, listener: PermissionAskListener) {
        if (shouldAskPermission()) {
            if (shouldShowRequestPermissionRationale(mActivity, permission)) {
                listener.onPermissionPreviouslyDenied()
            } else {
                if (prefs.isFirstTimeAskingPermission(permission)) {
                    prefs.firstTimeAskingPermission(permission, false)
                    listener.onNeedPermission()
                } else {
                    listener.onPermissionDisabled()
                }
            }
        }
    }

    override fun setActivity(activity: Activity) {
        mActivity = activity
    }
}