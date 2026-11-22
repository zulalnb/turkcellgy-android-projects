package com.zulalnb.backpressalert

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback(this) {
            showAlert()
        }
    }

    // Kullanıcıları ikaz etmek ve bilgi vermek için
    // en sık kullanılan yapı Alert Dialog yapısıdır.

    fun showAlert() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Çıkış")
        alertBuilder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?")

        alertBuilder.setPositiveButton("Evet") { _, _ ->
            finish()
        }

        alertBuilder.setNegativeButton("Hayır", null)

        alertBuilder.create().show()
    }
}