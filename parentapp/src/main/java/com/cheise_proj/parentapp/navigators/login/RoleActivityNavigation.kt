package com.cheise_proj.parentapp.navigators.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import com.cheise_proj.login_feature.RoleNavigation
import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.auth.AuthActivity
import com.cheise_proj.parent_feature.ParentNavigationActivity
import com.cheise_proj.parentapp.R
import javax.inject.Inject

@LoginScope
class RoleActivityNavigation @Inject constructor() : RoleNavigation {
    override fun goToAuthPage(
        activity: Activity,
        bundle: Bundle?,
        options: ActivityOptionsCompat?
    ) {
        bundle?.let {
            activity.startActivity(
                AuthActivity.getIntent(activity).putExtras(it),
                options?.toBundle()
            )
        }
            ?: activity.startActivity(AuthActivity.getIntent(activity), options?.toBundle())
    }


}