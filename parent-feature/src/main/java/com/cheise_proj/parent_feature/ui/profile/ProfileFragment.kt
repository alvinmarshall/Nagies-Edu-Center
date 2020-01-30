package com.cheise_proj.parent_feature.ui.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.di.GlideApp
import com.cheise_proj.parent_feature.ui.profile.adapter.ProfileAdapter
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.model.vo.UserSession
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
        const val DELAY_HANDLER: Long = 1000
    }

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var prefs: IPreference
    @Inject
    lateinit var serverPath: IServerPath

    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var session: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProfileAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = recycler_view
        recyclerView?.also {
            it.hasFixedSize()
            it.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            session = prefs.getUserSession()
            viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
            viewModel.getProfile(session.role!!, session.name!!)
                .observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        STATUS.LOADING -> println("loading...")
                        STATUS.SUCCESS -> {
                            adapter.submitList(it.data)
                            setProfileAvatar()
                            recyclerView?.adapter = adapter
                            hideLoadingProgress()
                        }
                        STATUS.ERROR -> {
                            hideLoadingProgress()
                            println("err ${it.message}")
                        }
                    }
                })
        }, DELAY_HANDLER)
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    private fun setProfileAvatar() {
        GlideApp.with(this).load(serverPath.setCorrectPath(session.photo)).into(item_img)
    }

}
