package com.zulalnb.themepreference

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Ekranda koyu tema ve açık temanın seçilmesini sağlayacak buton olacak.
// Bu butonlar aracılığı ile kullanıcı uygulamasının temasını seçebilecek.
// Uygulama açıldığında kullanıcının teması ne ise arkaplan ona göre renklenecek.
// Varsayılan tema, açık renk tema olacaktır.

class MainActivity : AppCompatActivity() {

    val lightColor = Color.WHITE
    val darkColor = Color.DKGRAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLightMode = findViewById<Button>(R.id.btnLightMode)
        val btnDarkMode = findViewById<Button>(R.id.btnDarkMode)

        btnLightMode.setOnClickListener {
            onLightModeClick()
        }

        btnDarkMode.setOnClickListener {
            onDarkModeClick()
        }

        applyTheme()
    }

    fun onLightModeClick() {
        saveTheme("light")
        applyTheme()
    }

    fun onDarkModeClick() {
        saveTheme("dark")
        applyTheme()
    }

    fun saveTheme(theme: String) {
        val pref = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("theme", theme)
        editor.commit()
    }

    fun applyTheme() {
        if (getSharedPreferences(packageName, MODE_PRIVATE).getString("theme", "light") == "dark") {
            window.decorView.rootView.setBackgroundColor(darkColor)
        } else {
            window.decorView.rootView.setBackgroundColor(lightColor)
        }
    }
}