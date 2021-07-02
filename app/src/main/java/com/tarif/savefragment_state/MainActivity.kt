package com.tarif.savefragment_state

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tarif.savefragment_state.databinding.ActivityMainBinding
import com.tarif.savefragment_state.ui.GalleryFragment
import com.tarif.savefragment_state.ui.HomeFragment
import com.tarif.savefragment_state.ui.NotificationFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val CURRENT_FRAGMENT = "current_fragment"
        const val FRAGMENT_HOME = "fragment_home"
        const val FRAGMENT_GALLERY = "fragment_gallery"
        const val FRAGMENT_NOTIFICATION = "fragment_notification"
    }

    private lateinit var bind: ActivityMainBinding
    private var selectedFragment: Int = R.id.nav_home
    private var activeFragment: Fragment? = null

    private val homeFragment: HomeFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(FRAGMENT_HOME)
        if (fr != null) fr as HomeFragment
        else HomeFragment()
    }

    private val galleryFragment: GalleryFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(FRAGMENT_GALLERY)
        if (fr != null) fr as GalleryFragment
        else GalleryFragment()
    }

    private val notificationFragment: NotificationFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(FRAGMENT_NOTIFICATION)
        if (fr != null) fr as NotificationFragment
        else NotificationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        savedInstanceState?.let {
            selectedFragment = it.getInt(CURRENT_FRAGMENT, bind.fragmentContainer.id)
        }

        when (selectedFragment) {
            R.id.nav_home -> activeFragment = homeFragment
            R.id.nav_gallery -> activeFragment = galleryFragment
            R.id.nav_notification -> activeFragment = notificationFragment
        }

        if (savedInstanceState == null) {
            //add all fragments but show only active fragment
            val id = bind.fragmentContainer.id
            supportFragmentManager.beginTransaction()
                .add(id, homeFragment, FRAGMENT_HOME)
                .hide(homeFragment).add(id, galleryFragment, FRAGMENT_GALLERY)
                .hide(galleryFragment).add(id, notificationFragment, FRAGMENT_NOTIFICATION)
                .hide(notificationFragment)
                .show(activeFragment!!).commit()

        }

        bind.bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                if (activeFragment is HomeFragment) return false
                addFragment(homeFragment)
            }
            R.id.nav_gallery -> {
                if (activeFragment is GalleryFragment) return false
                addFragment(galleryFragment)
            }
            R.id.nav_notification -> {
                if (activeFragment is NotificationFragment) return false
                addFragment(notificationFragment)
            }
        }
        return true
    }

    private fun addFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .hide(activeFragment!!)
            .show(fragment).commit()

        activeFragment = fragment
    }

    override fun onBackPressed() {
        if(activeFragment != homeFragment){
            addFragment(homeFragment)
            bind.bottomNavigation.menu.findItem(R.id.nav_home).isChecked = true
        }else{
            super.onBackPressed()
        }
    }
}