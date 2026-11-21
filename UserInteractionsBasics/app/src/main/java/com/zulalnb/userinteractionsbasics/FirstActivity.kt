package com.zulalnb.userinteractionsbasics

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.first_screen)

        val contentTextView = findViewById<TextView>(R.id.contentTextView)

        contentTextView.text = getString(R.string.text)
    }
}