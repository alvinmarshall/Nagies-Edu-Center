package com.cheise_proj.login_feature.ui.role

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.cheise_proj.login_feature.R
import com.cheise_proj.login_feature.RoleNavigation
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_role.*
import javax.inject.Inject

class RoleActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var navigation: RoleNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        val bundle = Bundle()

        btn_parent_role.setOnClickListener {
            setAuthNavigation(getString(R.string.label_parent_login), bundle, img_parent)
        }

        btn_teacher_role.setOnClickListener {
            setAuthNavigation(getString(R.string.label_teacher_login), bundle, img_teacher)
        }
    }

    private fun setAuthNavigation(role: String, bundle: Bundle, view: View) {
        val options = ViewCompat.getTransitionName(view)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                it
            )
        }
        bundle.putString(getString(R.string.user_role_extra), role)
        navigation.goToAuthPage(this, bundle, options)
    }

}
