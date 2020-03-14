package com.cheise_proj.teacherapp.navigators.login

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cheise_proj.login_feature.SplashNavigation
import com.cheise_proj.login_feature.ui.role.RoleActivity
import com.cheise_proj.teacher_feature.TeacherNavigationActivity
import com.cheise_proj.teacherapp.R
import javax.inject.Inject

class SplashNavigationImpl @Inject constructor() : SplashNavigation {
    override fun goToRolePage(activity: Activity, bundle: Bundle?) {
        activity.startActivity(
            RoleActivity.getIntent(activity)
        )
        activity.finish()
    }

    override fun skipLoginPage(activity: Activity, bundle: Bundle) {
        val role = bundle.getString("role", null)
        role?.apply {
            when (this) {
                activity.getString(R.string.label_teacher_login) -> {
                    activity.startActivity(
                        TeacherNavigationActivity
                            .getIntent(activity).putExtras(bundle)
                    )
                    activity.finish()
                }
                activity.getString(R.string.label_parent_login) ->
                    AlertDialog.Builder(activity).setMessage("Not Implemented").show()
            }

        }
    }
}