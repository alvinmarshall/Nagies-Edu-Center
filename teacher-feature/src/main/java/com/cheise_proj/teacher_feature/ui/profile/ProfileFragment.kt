package com.cheise_proj.teacher_feature.ui.profile


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheise_proj.common_module.DELAY_HANDLER
import com.cheise_proj.presentation.GlideApp
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.model.vo.STATUS
import com.cheise_proj.presentation.model.vo.UserSession
import com.cheise_proj.presentation.utils.IPreference
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.user.ProfileViewModel
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.base.BaseFragment
import com.cheise_proj.teacher_feature.ui.profile.adapter.ProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var prefs: IPreference
    @Inject
    lateinit var serverPath: IServerPath
    private lateinit var adapter: ProfileAdapter
    private lateinit var viewmodel: ProfileViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var session: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = recycler_view
        recyclerView?.also {
            it.hasFixedSize()
            it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ProfileAdapter()
        configViewModel()
    }

    private fun configViewModel() {
        viewmodel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ subscribeObservable() }, DELAY_HANDLER)
    }

    private fun subscribeObservable() {
        session = prefs.getUserSession()
        with(session) {
            viewmodel.getProfile(role!!, name!!).observe(
                viewLifecycleOwner, Observer { resources ->
                    when (resources.status) {
                        STATUS.LOADING -> println("loading...")
                        STATUS.SUCCESS -> {
                            adapter.submitList(resources.data)
                            setProfileAvatar()
                            recyclerView?.adapter = adapter
                            hideLoadingProgress()
                        }
                        STATUS.ERROR -> {
                            hideLoadingProgress()
                            println("err ${resources.message}")
                        }
                    }

                }
            )

        }
    }

    private fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    private fun setProfileAvatar() {
        GlideApp.with(context!!).load(serverPath.setCorrectPath(session.photo)).into(item_img)
    }


}
