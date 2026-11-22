package com.zulalnb.multiscreenbasics

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val secondActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val returnedMessage = result.data?.getStringExtra("returnedData")
                Toast.makeText(this, "Alınan veri: $returnedMessage", Toast.LENGTH_SHORT).show()
            } else if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Geri tuşuyla önceki ekrana dönüldü", Toast.LENGTH_SHORT).show()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnOpenSecondScreen = findViewById<Button>(R.id.btnNext)
        btnOpenSecondScreen.setOnClickListener {
            navigateToSecondScreen()
        }
    }


    /* DEPRECATED
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val returnedMessage = result.data?.getStringExtra("returnedData")
            Toast.makeText(this, "Alınan veri: $returnedMessage", Toast.LENGTH_SHORT).show()
        }
    }
    */

    fun navigateToSecondScreen() {
        // Activity'ler arası geçiş yaptığımız tüm
        // durumlarda intentleri kullanacağız.
        //
        // Intent, Activity'ler arasında veri aktarımını sağlar.

        val intent = Intent(this, SecondActivity::class.java)

        // Intent'ler çok büyük dataları taşıyamazlar.
        intent.putExtra("intentData", "Aktarılan Veri")

        // startActivity(intent)
        // startActivityForResult(intent, 0) // DEPRECATED

        secondActivityLauncher.launch(intent)
    }
}