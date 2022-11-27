package com.quiza.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatDrawableManager.preload
import com.quiza.R

class CategoryActivity : AppCompatActivity() {
    lateinit var android: ImageView
    lateinit var kotlin: ImageView
    lateinit var java: ImageView
    lateinit var oop: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        android  = findViewById(R.id.android_btn)
        kotlin  = findViewById(R.id.kotlin_btn)
        java  = findViewById(R.id.java_btn)
        oop  = findViewById(R.id.oop_btn)
        preload()
    }
    fun preload(){
        val intent: Intent  = Intent(this@CategoryActivity, QuizActivity::class.java)

        android.setOnClickListener{
            intent.putExtra("tabName", "android")
            startActivity(intent)
        }
        kotlin.setOnClickListener{
            intent.putExtra("tabName", "kotlin")
            startActivity(intent)
        }
        java.setOnClickListener{
            intent.putExtra("tabName", "java")
            startActivity(intent)
        }
        oop.setOnClickListener{
            intent.putExtra("tabName", "oop")
            startActivity(intent)
        }
    }
}