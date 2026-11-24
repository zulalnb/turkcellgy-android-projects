package com.zulalnb.countrylistview

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val adapter = CountryAdapter(this, countryList)
        val lvCountry = findViewById<ListView>(R.id.lvCountry)

        lvCountry.adapter = adapter
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