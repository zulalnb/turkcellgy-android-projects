package com.zulalnb.spinnerbasics

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Dinamik olarak doldurulan viewlar bir
// adapter nesnesine ihtiyaç duyarlar.
//
// Adapter nesneleri, kaynak verilerin liste yapılarının
// içerisine doldurulma işlemlerini ve ekranda görünen
// verilerin yönetimini sağlar.
//
// Spinner, üzerine basıldığında açılan bir liste barındırır.
// Listeden elemanlar seçilerek işlemler yapılabilir.

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

        val fruits = arrayListOf(
            "Elma",
            "Muz",
            "Portakal",
            "Çilek",
            "Karpuz",
            "Ananas",
            "Mango",
            "Üzüm",
            "Şeftali",
            "Kiraz"
        )

        var spinner = findViewById<Spinner>(R.id.spinner)

        val tvSelectedFruit = findViewById<TextView>(R.id.tvSelectedFruit)

        // val adapter =
        //      ArrayAdapter(
        //         this,
        //         android.R.layout.simple_spinner_dropdown_item,
        //         fruits
        //     )


        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.fruits,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFruit = spinner.adapter.getItem(position)
                tvSelectedFruit.text = "Seçilen meyve: $selectedFruit"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}