package com.cheise_proj.teacher_feature

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.cheise_proj.presentation.viewmodel.SharedViewModel
import com.cheise_proj.teacher_feature.base.BaseActivity
import com.cheise_proj.teacher_feature.utils.ConnectionLiveData
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_teacher_navigation.*

class TeacherNavigationActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, TeacherNavigationActivity::class.java)
    }

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var sharedViewModel: SharedViewModel
    private val textBadgeViews = arrayListOf<TextView>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        connectionLiveData = ConnectionLiveData(this)


        val snack = Snackbar.make(root, "", Snackbar.LENGTH_INDEFINITE)
        navView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawer_layout)

        setupNavigation()
        initNavBadge()
        setProfileData(navView)
        configureViewModel()
        openNavigationMenu()
        subscribeNetworkChange(snack)


    }

    private fun subscribeNetworkChange(snack: Snackbar) {
        connectionLiveData.observe(this, Observer {
            if (!it) {
                root.background = baseContext.getDrawable(R.drawable.no_internet)
                snack.setText(getString(R.string.no_network_connection))
                snack.show()
            } else {
                root.background = getDrawable(R.drawable.background)
                snack.dismiss()
            }
        })
    }

    private fun configureViewModel() {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        sharedViewModel.getBadgeValue().observe(this, Observer {
            setNavMenuBadge(it)
        })
    }

    private fun setNavMenuBadge(badge: Pair<Int, Int?>?) {
        val str = if (badge?.second != null && badge.second!! > 0) "${badge.second} +" else ""
        when (badge?.first) {
            R.id.complaintFragment -> textBadgeViews[0].text = str
        }
    }

    private fun initNavBadge() {
        val menuNav = navView.menu
        textBadgeViews.add(menuNav.findItem(R.id.complaintFragment).actionView as TextView)

        textBadgeViews.forEach {
            it.apply {
                gravity = Gravity.CENTER_VERTICAL
                typeface = Typeface.DEFAULT_BOLD
                setTextColor(ContextCompat.getColor(baseContext, android.R.color.holo_red_dark))
            }
        }

    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragment_socket)
        setupActionBarWithNavController(navController, drawerLayout)
        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun openNavigationMenu() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                drawerLayout.openDrawer(GravityCompat.START)
            }, 2000
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> navigation.logout(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}
