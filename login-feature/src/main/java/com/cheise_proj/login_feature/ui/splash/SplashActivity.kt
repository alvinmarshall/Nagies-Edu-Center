package com.cheise_proj.login_feature.ui.splash

import android.os.Bundle
import com.cheise_proj.login_feature.SplashNavigation
import com.cheise_proj.presentation.utils.IPreference
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var prefs: IPreference
    @Inject
    lateinit var navigation: SplashNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        val session = prefs.getUserSession()
        if (session.isLogin) {
            bundle.putString("role", session.role)
            navigation.skipLoginPage(this, bundle)
            return
        }
        navigation.goToRolePage(this)
    }
}
