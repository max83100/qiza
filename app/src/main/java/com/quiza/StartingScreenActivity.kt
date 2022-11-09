package com.quiza

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.quiza.R
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.core.app.TaskStackBuilder
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.quiza.QuizActivity

class StartingScreenActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_component) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}