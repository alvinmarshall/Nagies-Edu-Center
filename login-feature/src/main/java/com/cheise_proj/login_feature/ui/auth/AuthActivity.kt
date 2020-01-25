package com.cheise_proj.login_feature.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.R
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var navigation: AuthNavigation
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var imageView: ImageView
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val role = getUserRoleExtras()
        configureViewModel()
        btn_login.setOnClickListener {
            userViewModel.authenticateUser("creche", "1234", "parent").observe(this, Observer {
                showErrorMessage(it.message)
                when (it.status) {
                    STATUS.LOADING -> showProgress()
                    STATUS.SUCCESS -> {
                        showProgress(false)
                        println("data ${it.data}")
                    }

                    STATUS.ERROR -> {
                        showProgress(false)
                        println("error ${it.message}")
                    }
                }
            })

//            role?.let {
//                when (it) {
//                    getString(R.string.label_parent_login) -> {
//                        navigation.loginToParent(this)
//                    }
//                    getString(R.string.label_teacher_login) -> {
//                        navigation.loginToTeacher(this)
//                    }
//                }
//
//            }
        }

    }

    private fun configureViewModel() {
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showProgress(show: Boolean = true) {
        if (!show) {
            progressBar.visibility = View.GONE
            return
        }
        progressBar.visibility = View.VISIBLE
    }

    private fun showErrorMessage(msg: String?) {
        label_msg_error.visibility = View.GONE
        msg?.let {
            if (msg.contains("Unable to resolve host")) {
                label_msg_error.text = "No internet connection"
            }
            if (msg.contains("HTTP 401")) {
                label_msg_error.text = "Username or password incorrect"
            }
            label_msg_error.visibility = View.VISIBLE
        }

    }

    private fun getUserRoleExtras(): String? {
        val role = intent.extras?.getString("role")
        imageView = login_avatar
        role?.let {
            tv_role.text = it
            if (it == "Parent") {
                imageView.setImageResource(R.drawable.parents)
            }
            if (it == "Teacher") {
                imageView.setImageResource(R.drawable.teachers)
            }
        }
        return role
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AuthActivity::class.java)
    }

}
