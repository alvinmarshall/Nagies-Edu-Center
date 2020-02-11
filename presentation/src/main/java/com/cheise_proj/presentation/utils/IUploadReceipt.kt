package com.cheise_proj.presentation.utils

import io.reactivex.Single
import okhttp3.MultipartBody

interface IUploadReceipt {
    fun uploadFile(params: MultipartBody.Part): Single<Int>
}