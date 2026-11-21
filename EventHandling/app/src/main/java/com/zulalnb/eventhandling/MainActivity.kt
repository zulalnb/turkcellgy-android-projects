package com.zulalnb.eventhandling

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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

        var btnWarn = findViewById<Button>(R.id.btnWarn)

        // 1 view'ın sadece 1 tane click event'i olabilir.
        // Ve siz en son ne zaman set demişseniz onu kabul eder. Eskisinin üzerine yazmış olur.

        // 1. yöntem: Lambda
        /* btnWarn.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Lambda ile tıklama olayı", Toast.LENGTH_LONG).show()
        }) */

        // 2. yöntem: Listener değişkeni
        // btnWarn.setOnClickListener(warnClickListener)

        window.decorView.rootView.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            Toast.makeText(this, "Ekrana dokunuldu", Toast.LENGTH_LONG).show()

            return@OnTouchListener true
        })

    }

    var warnClickListener = View.OnClickListener {
        Toast.makeText(this, "Listener değişkeni ile tıklama olayı", Toast.LENGTH_LONG).show()
    }

    fun showWarningWithXml(view: View) {
        Toast.makeText(this, "XML onClick ile tıklama olayı", Toast.LENGTH_LONG).show()
    }
}