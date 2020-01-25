package com.cheise_proj.login_feature

import android.app.Activity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat

interface RoleNavigation {
    fun goToAuthPage(activity: Activity, bundle: Bundle? = null, options: ActivityOptionsCompat?=null)
}