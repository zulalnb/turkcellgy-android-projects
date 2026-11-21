package com.zulalnb.eventhandling

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton
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

        // var btnWarn = findViewById<Button>(R.id.btnWarn)

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

        var toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        var switchBtn = findViewById<Switch>(R.id.switch1)
        var checkBox = findViewById<CheckBox>(R.id.checkBox)
        var imageView = findViewById<ImageView>(R.id.imageView)
        var webView = findViewById<WebView>(R.id.webView)

        toggleButton.setOnCheckedChangeListener(toggleButtonListener)

        // Switch başlangıçta OFF ise ToggleButton gizli olsun.
        toggleButton.visibility = if (switchBtn.isChecked) View.VISIBLE else View.INVISIBLE

        switchBtn.setOnCheckedChangeListener(switchListener)

        checkBox.setOnCheckedChangeListener { btn: CompoundButton, status: Boolean ->
            Toast.makeText(this, "Seçim: " + status, Toast.LENGTH_LONG).show()
        }

        imageView.setImageResource(R.drawable.avatar)

        webView.loadUrl("https://www.google.com")

    }

    var warnClickListener = View.OnClickListener {
        Toast.makeText(this, "Listener değişkeni ile tıklama olayı", Toast.LENGTH_LONG).show()
    }

    var toggleButtonListener = CompoundButton.OnCheckedChangeListener{ btn: CompoundButton, status: Boolean ->
        Toast.makeText(this, "toggle status: " + status, Toast.LENGTH_LONG).show()
    }

    var switchListener = CompoundButton.OnCheckedChangeListener{ btn: CompoundButton, status: Boolean ->
        var toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        toggleButton.visibility = if (status) View.VISIBLE else View.INVISIBLE
    }


    fun showWarningWithXml(view: View) {
        Toast.makeText(this, "XML onClick ile tıklama olayı", Toast.LENGTH_LONG).show()
    }
}