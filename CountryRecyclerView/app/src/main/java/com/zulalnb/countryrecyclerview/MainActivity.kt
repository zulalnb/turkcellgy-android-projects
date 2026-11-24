package com.zulalnb.countryrecyclerview

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val allCountries = loadAllCountriesFromResources()

        val rvCountries = findViewById<RecyclerView>(R.id.rvCountries)
        val searchView = findViewById<SearchView>(R.id.searchView)

        adapter = CountryAdapter(allCountries, this::onItemClick)
        rvCountries.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.scrollToPosition(0)
        rvCountries.layoutManager = layoutManager

        rvCountries.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        rvCountries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy <= 0) return

                val lastItem = layoutManager.findLastVisibleItemPosition()
                if (lastItem >= adapter.itemCount - 4) {
                    adapter.loadNextPage()
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }
        })

    }

    private fun onItemClick(position: Int) {
        Toast.makeText(this, adapter.countries[position].name, Toast.LENGTH_SHORT).show()
    }

    private fun loadAllCountriesFromResources(): List<Country> {
        val list = ArrayList<Country>()
        val names: Array<String> = resources.getStringArray(R.array.country_names)
        val flags: TypedArray = resources.obtainTypedArray(R.array.country_flags)

        for (i in names.indices) {
            val name = names[i]
            val flagId = flags.getResourceId(i, 0)
            list.add(Country(name, flagId))
        }

        flags.recycle()
        return list
    }
}