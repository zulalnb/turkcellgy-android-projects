package com.zulalnb.listviewbasics

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.fruits,
            android.R.layout.simple_list_item_1
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedFruit = parent.getItemAtPosition(position).toString()

            AlertDialog.Builder(this)
                .setTitle("Meyve Seçildi")
                .setMessage("Seçtiğiniz meyve: $selectedFruit")
                .setPositiveButton("Tamam", null)
                .show()
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val selectedFruit = parent.getItemAtPosition(position).toString()

            AlertDialog.Builder(this)
                .setTitle(selectedFruit)
                .setItems(arrayOf("Meyve bilgisini göster", "İptal")) { dialog, which ->
                    when (which) {
                        0 -> {
                            AlertDialog.Builder(this)
                                .setTitle("$selectedFruit hakkında")
                                .setMessage("$selectedFruit meyvesini seçtiniz.")
                                .setPositiveButton("Tamam", null)
                                .show()
                        }
                        1 -> dialog.dismiss()
                    }
                }
                .show()

            true
        }


    }
}