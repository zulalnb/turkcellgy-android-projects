package com.zulalnb.asynctaskbasics

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Uygulama içerisinde yapacağımız işlemlerin uzun sürmesi
// durumunda Async Task veya modern alternatiflerini kullanıyoruz.

enum class ExecutionMode {
    LEGACY,       // AsyncTask kullanımı (Deprecated / Geleneksel)
    MODERN,       // Kotlin Coroutines kullanımı (Modern / Tavsiye Edilen)
    WITHOUT_ASYNC // Async kullanmadan ana iş parçacığında bloklama (Sync)
}

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvStatus = findViewById(R.id.tvStatus)

        val btnLegacy = findViewById<Button>(R.id.btnLegacy)
        val btnModern = findViewById<Button>(R.id.btnModern)
        val btnWithoutAsync = findViewById<Button>(R.id.btnWithoutAsync)

        btnLegacy.setOnClickListener { executeTask(ExecutionMode.LEGACY) }
        btnModern.setOnClickListener { executeTask(ExecutionMode.MODERN) }
        btnWithoutAsync.setOnClickListener { executeTask(ExecutionMode.WITHOUT_ASYNC) }

        // Varsayılan çağrım (Legacy)
        executeTask(ExecutionMode.LEGACY)
    }

    fun executeTask(mode: ExecutionMode) {
        when (mode) {
            ExecutionMode.LEGACY -> {
                tvStatus.text = "Durum: Legacy AsyncTask çalışıyor..."
                Toast.makeText(this, "Çağrım yapıldı (Legacy)", Toast.LENGTH_SHORT).show()
                BackgroundTask(this).execute()
            }
            ExecutionMode.MODERN -> {
                tvStatus.text = "Durum: Modern Coroutines çalışıyor..."
                Toast.makeText(this, "Çağrım yapıldı (Modern)", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        Thread.sleep(5000)
                        "İşlem bitti (Modern)"
                    }
                    onFinishTask(result)
                }
            }
            ExecutionMode.WITHOUT_ASYNC -> {
                tvStatus.text = "Durum: Async olmadan çalışıyor (UI Bloklu)..."
                Toast.makeText(this, "pre call", Toast.LENGTH_SHORT).show()
                Thread.sleep(5000) // without async task
                Toast.makeText(this, "Çağrım yapıldı (Sync)", Toast.LENGTH_SHORT).show()
                onFinishTask("İşlem bitti (Without Async)")
            }
        }
    }

    internal class BackgroundTask(activity: Activity): AsyncTask<Void, Void, String>(){

        var activity: Activity

        init {
           this.activity = activity
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): String? {
            Thread.sleep(5000)
            return "İşlem bitti (Legacy)"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
            (activity as MainActivity).onFinishTask(result!!)
        }
    }

    fun onFinishTask(string: String) {
        tvStatus.text = "Durum: $string"
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}