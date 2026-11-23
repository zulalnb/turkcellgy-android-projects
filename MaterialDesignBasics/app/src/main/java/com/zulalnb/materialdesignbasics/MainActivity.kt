package com.zulalnb.materialdesignbasics

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// Google, 2014 I/O etkinliklerinde tanıtılan yenilikçi bir tasarım
// yaklaşımı ile tüm ürünlerinde tek bir tasarım yapısını
// oturtmak istedi ve tüm uygulamalarında bu yapının özelliklerini
// kullanmaya başladı.
//
// Böyle bir tasarım geleneğinin getirilme sebebi tüm uygulamalarda
// benzer alışkanlıklar ve davranışlar kullanarak kullacılara tüm uygulamalarda
// ortak bir deneyim sunabilmektir. Böylece kullanıcı yeni kullanmaya başladığı
// bir uygulamada dahi nerede ne araması gerektiğini kolaylıkla bulabilecektir.
//
// Material Design, çok fazla detay barındıran bir yapıdır.


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // Floating Action Button (FAB), activity içerisinde ek bir
        // işi yapmak için kullanılan butondur. Ekranın sağ alt köşesinde bulunur.
        // FAB, öne çıkarılmak istenen işlev olarak tanımlanır.

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener { _ ->
            Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()
        }

        // Text Input Layout içerisinde kayan bir label yapısı bulunuyor.
        // Edit text alanına girilecek değerlerin ne olduğunu kullanıcıya
        // belirtmek için 2 farklı şekil yaygın olarak kullanılır:
        // 1- Hint değeri ile hangi değer olduğunu yazmak
        // 2- Edit text yanına ya da üzerine hangi değer olduğunu yazmak

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            validateForm()
        }

        // Toolbar, uygulama genelinde veya bir bölümünde üst kısımda sabit olarak kullanılan yapıdır.
        // Toolbar, AppBarLayout içerisinde konumlandırılır.
        // AppBarLayout içerisindeki elemanlara animasyon şeklinde işlevler tanımlamamızı sağlar.
    }

    fun validateForm() {
        val tilUser = findViewById<TextInputLayout>(R.id.tilUser)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)

        val email = etEmail.text.toString().trim()

        if (email.isEmpty()) {
            tilUser.error = "Email adresi boş olamaz"
            tilUser.isErrorEnabled = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilUser.error = "Geçerli bir email adresi giriniz"
            tilUser.isErrorEnabled = true
        } else {
            tilUser.isErrorEnabled = false
        }
    }
}