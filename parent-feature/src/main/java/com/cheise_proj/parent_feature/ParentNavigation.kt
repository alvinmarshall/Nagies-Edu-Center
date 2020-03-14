package com.cheise_proj.parent_feature

import android.app.Activity

interface ParentNavigation {
    fun goToLocation(location:String?)
    fun logout(activity: Activity,topic:String)
}