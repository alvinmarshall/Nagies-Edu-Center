package com.cheise_proj.parentapp.utils

import com.cheise_proj.common_module.DEV_SERVER_URL
import com.cheise_proj.common_module.SERVER_URL
import com.cheise_proj.parentapp.BuildConfig
import com.cheise_proj.presentation.utils.IServerPath
import javax.inject.Inject

class ServerPathUtils @Inject constructor() :IServerPath {
    override fun setCorrectPath(path: String?): String? {
        if (BuildConfig.DEBUG){
            return path?.let { "$DEV_SERVER_URL$path" }
        }
        return path?.let { "$SERVER_URL$path" }
    }

}