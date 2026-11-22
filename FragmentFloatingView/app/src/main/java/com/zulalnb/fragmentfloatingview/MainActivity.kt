package com.zulalnb.fragmentfloatingview

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Uygulama genelinde kullanılan bir üst bar bulunmaktadır.
// Bu üst bardan kalan bölümlerde de ekranlar yer almaktadır.
// Ekran açıldığında parça bölüm boş gösterilmeyecektir.
// Üst barda bulunan bir butona basıldığında görünen ekran parçasının
// küçük gösterime uygun bir tasarım ile ekranın sağ altında gösterilmeye başlanacaktır.

// Fragmentlar, bir activity içerisinde kendi yaşam döngüsü olan parçalardır.

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

        var btnMinimize = findViewById<Button>(R.id.btnMinimize)
        var floatingContainer = findViewById<ConstraintLayout>(R.id.floatingFragmentContainer)
        val ft = supportFragmentManager.beginTransaction()

        floatingContainer.visibility = View.INVISIBLE

        btnMinimize.setOnClickListener { minimizeFragment() }

        ft.replace(R.id.fragmentContainer, MainFragment())
        ft.commit()


    }


    fun minimizeFragment() {
        var floatingContainer = findViewById<ConstraintLayout>(R.id.floatingFragmentContainer)
        floatingContainer.visibility = View.VISIBLE

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.floatingFragmentContainer, FloatingFragment())
        ft.commit()
    }
}