package com.cheise_proj.parentapp.navigators.login

import android.app.Activity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.cheise_proj.login_feature.RoleNavigation
import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.login_feature.ui.auth.AuthActivity
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