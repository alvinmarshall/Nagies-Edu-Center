package com.wNagiesEducationalCenterj_9905.navigators.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.di.LoginScope
import com.cheise_proj.parent_feature.ParentNavigationActivity
import com.cheise_proj.teacher_feature.TeacherNavigationActivity
import javax.inject.Inject

@LoginScope
class AuthActivityNavigation @Inject constructor() : AuthNavigation {
    override fun loginToParent(activity: Activity, bundle: Bundle?) {
        val intent = ParentNavigationActivity.getIntent(activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        activity.finish()
    }

    override fun loginToTeacher(activity: Activity, bundle: Bundle?) {
        val intent = TeacherNavigationActivity.getIntent(activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        activity.finish()
    }
}