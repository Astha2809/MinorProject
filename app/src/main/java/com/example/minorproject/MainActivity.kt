package com.example.minorproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.minorproject.login.LoginFragment
import com.example.minorproject.profile.UserProfileFragment
import com.example.minorproject.timeline.ui.TimelineFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openLoginFragment()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

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
                else -> {
                    Toast.makeText(applicationContext, "Logoutclicked", Toast.LENGTH_SHORT).show()
                    Log.i("nav bar", "Logoutclicked")
                    logoutFunction()
                    return@setNavigationItemSelectedListener true
                }
            }

        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        // val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_timeline, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun openLoginFragment() {
        val fragment = LoginFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    fun openProfileFragment() {
        val userProfileFragment = UserProfileFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, userProfileFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    fun openTImelineFragment() {
        val timelineFragment = TimelineFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, timelineFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    fun logoutFunction(){


    }


}
