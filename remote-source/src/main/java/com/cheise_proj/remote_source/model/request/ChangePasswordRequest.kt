package com.cheise_proj.remote_source.model.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("confirm_password")
    val confirmPassword: String
)