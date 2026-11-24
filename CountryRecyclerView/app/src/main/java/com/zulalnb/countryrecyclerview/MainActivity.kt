package com.zulalnb.countryrecyclerview

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val countryList = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createCountryList()

        val rvCountries = findViewById<RecyclerView>(R.id.rvCountries)
        val adapter = CountryAdapter(countryList, this::onItemClick)
        rvCountries.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.scrollToPosition(0)
        rvCountries.layoutManager = layoutManager

        rvCountries.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

    }

    private fun onItemClick(position: Int) {
        Toast.makeText(this, countryList[position].name, Toast.LENGTH_SHORT).show()
    }

    private fun createCountryList() {
        val names: Array<String> = resources.getStringArray(R.array.country_names)
        val flags: TypedArray = resources.obtainTypedArray(R.array.country_flags)

        for (i in names.indices) {
            val name = names[i]
            val flagId = flags.getResourceId(i, 0)
            countryList.add(Country(name, flagId))
        }

        flags.recycle()
    }
}