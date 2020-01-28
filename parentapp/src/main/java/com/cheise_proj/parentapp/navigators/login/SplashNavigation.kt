package com.cheise_proj.parentapp.navigators.login

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cheise_proj.login_feature.SplashNavigation
import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.role.RoleActivity
import com.cheise_proj.parent_feature.ParentNavigationActivity
import com.cheise_proj.parentapp.R
import javax.inject.Inject

@LoginScope
class SplashNavigation @Inject constructor() : SplashNavigation {
    override fun goToRolePage(activity: Activity, bundle: Bundle?) {
        activity.startActivity(
            RoleActivity.getIntent(activity)
        )
        activity.finish()
    }

    override fun skipLoginPage(activity: Activity, bundle: Bundle) {
        val role = bundle.getString("role", null)
        role?.let { r ->
            when (r) {
                activity.getString(R.string.label_parent_login) -> {
                    activity.startActivity(
                        ParentNavigationActivity
                            .getIntent(activity).putExtras(bundle)
                    )
                    activity.finish()
                }
                else -> AlertDialog.Builder(activity).setMessage("Not Implemented").show()
            }
        }
    }
}