package com.cheise_proj.login_feature.utils

import android.view.View

interface LoginInputValidation {
    fun isEditTextFilled(view: View, message: String? = null, isEmail: Boolean = false): Boolean
    fun hideKeyboard(view: View)
}