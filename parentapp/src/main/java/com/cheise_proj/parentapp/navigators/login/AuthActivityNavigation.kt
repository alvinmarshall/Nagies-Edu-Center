package com.cheise_proj.parentapp.navigators.login

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.parent_feature.ParentNavigationActivity
import javax.inject.Inject

@LoginScope
class AuthActivityNavigation @Inject constructor() : AuthNavigation {
    override fun loginToParent(activity: Activity, bundle: Bundle?) {
        activity.startActivity(ParentNavigationActivity.getIntent(activity))
    }

    override fun loginToTeacher(activity: Activity, bundle: Bundle?) {
        AlertDialog.Builder(activity).setMessage("Not Implemented").show()
    }
}