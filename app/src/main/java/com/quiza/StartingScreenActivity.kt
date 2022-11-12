package com.quiza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController

class StartingScreenActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_component, CategoryFragment()).commit()
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}