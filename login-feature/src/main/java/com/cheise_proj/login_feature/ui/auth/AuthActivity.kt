package com.cheise_proj.login_feature.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.login_feature.AuthNavigation
import com.cheise_proj.login_feature.R
import com.cheise_proj.login_feature.utils.LoginInputValidation
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.user.User
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.model.vo.UserSession
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var navigation: AuthNavigation

    @Inject
    lateinit var inputValidation: LoginInputValidation

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var preference: IPreference

    private lateinit var imageView: ImageView
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val role = getUserRoleExtras()
        configureViewModel()
        btn_login.setOnClickListener {
            inputValidation.hideKeyboard(it)
            role?.let { r -> authenticateUser(r) }
        }
    }

    private fun authenticateUser(role: String) {
        if (!inputValidation.isEditTextFilled(et_username)) return
        if (!inputValidation.isEditTextFilled(et_password)) return

        val username: String = et_username.text.trim().toString()
        val password: String = et_password.text.trim().toString()

        subscribeLoginObservers(username, password, role)
    }

    private fun subscribeLoginObservers(username: String, password: String, role: String) {
        userViewModel.authenticateUser(username, password, role).observe(this, Observer {
            showErrorMessage(it.message)
            when (it.status) {
                STATUS.LOADING -> showProgress()
                STATUS.SUCCESS -> {
                    showProgress(false)
                    if (it.data == null) return@Observer
                    setSession(it.data!!, role)
                    when (role) {
                        getString(R.string.label_parent_login) -> {
                            navigation.loginToParent(this)
                        }
                        getString(R.string.label_teacher_login) -> {
                            navigation.loginToTeacher(this)
                        }
                    }
                }

                STATUS.ERROR -> {
                    showProgress(false)
                    println("error ${it.message}")
                }
            }
        })

    }

    private fun setSession(data: User, role: String) {
        with(data) {
            val session =
                UserSession(true, role, username, level)
            session.name = name
            session.token = token
            session.photo = photo
            session.uuid = uuid
            preference.setUserSession(session)
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
            label_msg_error.text = it
            if (it.contains("Unable to resolve host")) {
                label_msg_error.text = getString(R.string.error_no_network_connectivity)
            }
            if (it.contains("HTTP 401")) {
                label_msg_error.text = getString(R.string.error_invalid_credentials)
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
