package com.quiza.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.quiza.R

class ExplainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explain)
        var text: TextView = findViewById(R.id.explainText)
        val extras = intent.extras
        text.setText(extras!!.getString("explainText"))
    }
}