package com.zulalnb.multiscreenbasics

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var intentData = intent.getStringExtra("intentData")

        var tvIntentData = findViewById<TextView>(R.id.tvIntentData)
        tvIntentData.text = intentData

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            navigateBack()
        }
    }

    fun navigateBack() {
        var intent = Intent()
        intent.putExtra("returnedData", "İkinci ekrandan dönen veri")
        setResult(RESULT_OK, intent)
        finish()
    }
}