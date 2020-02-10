package com.cheise_proj.parent_feature.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.InputValidation
import com.cheise_proj.presentation.viewmodel.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.password_fragment.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class PasswordFragment : BaseFragment() {

    companion object {
        fun newInstance() = PasswordFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var prefs: IPreference

    @Inject
    lateinit var inputValidation: InputValidation

    private lateinit var viewModel: UserViewModel
    private var identifier: Int = -1
    private lateinit var snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snackbar = Snackbar.make(root, "", Snackbar.LENGTH_LONG)
        btn_change_password.setOnClickListener {
            inputValidation.hideKeyboard(it)
            changeAccountPassword()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        identifier = prefs.getUserSession().uuid
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    private fun changeAccountPassword() {
        if (!inputValidation.isEditTextFilled(et_old_password)) return
        if (!inputValidation.isEditTextFilled(et_new_password)) return
        if (!inputValidation.isEditTextFilled(et_confirm_password)) return

        val oldPassword = et_old_password.text.toString().trim()
        val newPassword = et_new_password.text.toString().trim()
        val confirmPassword = et_confirm_password.text.toString().trim()
        if (newPassword != confirmPassword) {
            showSnackMessage(getString(R.string.password_reset_mismatch))
            return
        }

        viewModel.updateAccountPassword(identifier.toString(), oldPassword, newPassword)
            .observe(viewLifecycleOwner,
                Observer {
                    when (it.status) {
                        STATUS.LOADING -> showSnackMessage(getString(R.string.password_reset_loading))
                        STATUS.SUCCESS -> {
                            it.data?.let { isSuccess ->
                                if (isSuccess) {
                                    resetPassawordField()
                                    showSnackMessage(getString(R.string.password_reset_success))
                                } else {
                                    showSnackMessage(getString(R.string.password_reset_failed))
                                }
                            }
                        }
                        STATUS.ERROR -> showSnackMessage(it.message!!)
                    }
                })

    }

    private fun showSnackMessage(msg: String = "", show: Boolean = true) {
        if (show) {
            snackbar.setText(msg).show()
        } else {
            snackbar.dismiss()
        }
    }

    fun resetPassawordField() {
        et_old_password.text.clear()
        et_new_password.text.clear()
        et_confirm_password.text.clear()
    }

}
