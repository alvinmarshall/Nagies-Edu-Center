package com.cheise_proj.login_feature

import android.app.Activity
import android.os.Bundle

interface SplashNavigation {
    fun goToRolePage(activity: Activity, bundle: Bundle? = null)
    fun skipLoginPage(activity: Activity, bundle: Bundle)
}