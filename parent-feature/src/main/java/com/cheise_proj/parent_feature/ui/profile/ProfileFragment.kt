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
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.parent_feature.R
import com.cheise_proj.parent_feature.base.BaseFragment
import com.cheise_proj.parent_feature.ui.profile.adapter.ProfileAdapter
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.model.vo.UserSession
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
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
                        STATUS.LOADING -> Timber.i("loading...")
                        STATUS.SUCCESS -> {
                            hideLoadingProgress()
                            it.data?.let { data ->
                                if (data.isEmpty()) {
                                    showNoDataAlert()
                                } else {
                                    showNoDataAlert(false)
                                }
                            }
                            adapter.submitList(it.data)
                            setProfileAvatar()
                            recyclerView?.adapter = adapter
                        }
                        STATUS.ERROR -> {
                            hideLoadingProgress()
                            toast("error ${it.message}")
                            Timber.w("err ${it.message}")
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
