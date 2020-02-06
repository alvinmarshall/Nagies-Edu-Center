package com.cheise_proj.parent_feature.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    private var alertDialog: AlertDialog.Builder? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        alertDialog = context?.let { AlertDialog.Builder(it) }
    }

    //region Permission
    protected fun dialogForSettings(title: String, message: String) {
        alertDialog?.setTitle(title)
        alertDialog?.setMessage(message)
        alertDialog?.setPositiveButton("settings") { dialog, _ ->
            goToSettings()
            dialog.dismiss()
        }
        alertDialog?.setNegativeButton("not now", null)
        alertDialog?.setCancelable(false)
        alertDialog?.show()
    }

    private fun goToSettings() {
        val openAppSettings = Intent()
        openAppSettings.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.parse("package:${context?.packageName}")
        openAppSettings.data = uri
        startActivity(openAppSettings)
    }

    //endregion
}