package com.cheise_proj.parent_feature.ui.circular

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment

class CircularFragment : BaseFragment() {

    companion object {
        fun newInstance() = CircularFragment()
    }

    private lateinit var viewModel: CircularViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.circular_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CircularViewModel::class.java)
    }

}
