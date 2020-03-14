package com.cheise_proj.teacher_feature

import android.app.Activity

interface TeacherNavigation {
    fun goToLocation(location:String?)
    fun logout(activity: Activity,topic:String)
}