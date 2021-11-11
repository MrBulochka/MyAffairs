package com.notacompany.myaffairs

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity: AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        bottomNavView = findViewById(R.id.bottom_nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavView.setupWithNavController(navController)
    }

}