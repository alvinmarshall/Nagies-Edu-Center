package com.cheise_proj.parent_feature.ui.profile

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
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var prefs:IPreference
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val session = prefs.getUserSession()
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        viewModel.getProfile(session.role!!,session.name!!).observe(viewLifecycleOwner, Observer {
            when(it.status){
                STATUS.LOADING -> println("loading...")
                STATUS.SUCCESS -> println("data ${it.data}")
                STATUS.ERROR -> println("error ${it.message}")
            }
        })
    }

}
