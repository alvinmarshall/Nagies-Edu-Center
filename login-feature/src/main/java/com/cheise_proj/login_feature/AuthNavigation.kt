package com.cheise_proj.login_feature

import android.app.Activity
import android.os.Bundle

interface AuthNavigation {
    fun loginToParent(activity: Activity, bundle: Bundle? = null)
    fun loginToTeacher(activity: Activity, bundle: Bundle? = null)
}