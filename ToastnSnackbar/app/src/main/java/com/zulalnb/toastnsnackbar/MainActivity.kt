package com.zulalnb.toastnsnackbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

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

        // Toast, ekranın alt tarafında gri bir metin olarak gösterebildiğimiz uyarı sistemidir.
        Toast.makeText(this@MainActivity, "Uyarı metni", Toast.LENGTH_LONG).show()

        // Snackbar, görseli değiştirilebilen ve üzerinden aksiyon alınabilen uyarı mesajlarıdır.
        Snackbar.make(window.decorView.rootView, "Metin", Snackbar.LENGTH_LONG).show()
    }
}