package com.zulalnb.handlerbasics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Sonucu ekranda gösterilecek uzun işlemler
// yaptığımızda handler nesneleri kullanıyoruz.

// Handler sınıfı sayesinde threadler arasında veri aktarımı yapılabilir.
// Handler, ilgili thread'in mesaj kuyruğuna mesaj ve runnable nesneler
// göndermemizi sağlar.
// Böylece bir thread'den başka bir thread'e komutlar gönderebilmiş olursunuz.
// Bunu en çok UI'ı kitlemeden işlem yapıp sonucunda UI'ı güncellemek için ihtiyaç duyarız.

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

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Post işlemi çalıştırıldı.", Toast.LENGTH_SHORT).show()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, "Delayed işlemi çalıştırıldı.", Toast.LENGTH_SHORT).show()
        }, 6000)
    }
}