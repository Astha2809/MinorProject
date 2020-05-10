package com.example.minorproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.setupWithNavController
import com.example.minorproject.category.ui.CategoryListFragment
import com.example.minorproject.login.LoginFragment
import com.example.minorproject.profile.DisplayUserDetailsFragment
import com.example.minorproject.profile.UserProfileFragment
import com.example.minorproject.timeline.ui.TimelineFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)




        if (savedInstanceState == null) {
            openLoginFragment()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)




        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {

                    Toast.makeText(applicationContext, "profileclicked", Toast.LENGTH_SHORT).show()
                    Log.i("nav bar", "profileclicked")

                    openProfileFragment()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_timeline -> {
                    Toast.makeText(applicationContext, "timelineclicked", Toast.LENGTH_SHORT).show()

                    Log.i("nav bar", "timelineeclicked")

                    openTImelineFragment()

                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_displayprofile -> {
                    Log.i("display", "displayclicked")
                    openDisplayProfile()
                    return@setNavigationItemSelectedListener true
                }

                else -> {
                    Toast.makeText(applicationContext, "Logoutclicked", Toast.LENGTH_SHORT).show()
                    Log.i("nav bar", "Logoutclicked")
                    logoutFunction()
                    return@setNavigationItemSelectedListener true
                }
            }

        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_timeline, R.id.nav_logout
            ), drawerLayout
        )

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }



    private fun openLoginFragment() {

        val fragment = LoginFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        //fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun openProfileFragment() {
        val userProfileFragment = UserProfileFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, userProfileFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun openTImelineFragment() {
        val timelineFragment = TimelineFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, timelineFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun openDisplayProfile() {
        val displayUserDetailsFragment = DisplayUserDetailsFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, displayUserDetailsFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun logoutFunction() {

        val currentUser = mAuth.currentUser
        if (currentUser != null) {

            mAuth.signOut()
            openLoginFragment()

        }
    }


}





