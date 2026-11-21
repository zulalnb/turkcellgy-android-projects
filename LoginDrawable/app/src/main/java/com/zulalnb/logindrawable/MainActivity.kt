package com.zulalnb.logindrawable

import android.os.Bundle
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
    }
}

/*
E-posta ve şifre alanları ve giriş yap butonunun bulunduğu login ekranı oluşturulacak.
Kullanılacak alanlara dair bilgiler aşağıdaki şekildedir.

- Ekranın arka plan renk kodu #0B1032
- Metin giriş alanı özellikleri
  - Arka plan renk kodu #FFFFFF
  - Köşeleri 5dp oval
  - 2dp border
  - Border rengi #858585
- Giriş butonu özellikleri
  - Arka plan renk kodu #0288D1
  - Köşeleri 5dp oval
  - 2dp border
  - Border rengi #858585
*/