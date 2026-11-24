package com.zulalnb.countryrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val masterCountries: List<Country>,
    private val itemClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<CountryViewHolder>(), Filterable {

    private var currentFilteredList: List<Country> = masterCountries
    val countries = ArrayList<Country>()

    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    init {
        loadInitialPage()
    }

    private fun loadInitialPage() {
        countries.clear()
        isLastPage = false
        isLoading = false
        val end = minOf(currentFilteredList.size, pageSize)
        countries.addAll(currentFilteredList.subList(0, end))
        if (countries.size >= currentFilteredList.size) {
            isLastPage = true
        }
    }

    fun loadNextPage() {
        if (isLoading || isLastPage) return

        isLoading = true
        val previousSize = countries.size
        val start = previousSize
        if (start >= currentFilteredList.size) {
            isLastPage = true
            isLoading = false
            return
        }

        val end = minOf(currentFilteredList.size, start + pageSize)
        val newItems = currentFilteredList.subList(start, end)
        countries.addAll(newItems)

        if (countries.size >= currentFilteredList.size) {
            isLastPage = true
        }

        notifyItemRangeInserted(previousSize, newItems.size)
        isLoading = false
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,
            false
        )

        return CountryViewHolder(v, itemClick)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        val country = countries[position]
        holder.tvName.text = country.name
        holder.ivFlag.setImageResource(country.flagId)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.trim().orEmpty()
                val filteredList = if (query.isEmpty()) {
                    masterCountries
                } else {
                    masterCountries.filter { it.name.contains(query, ignoreCase = true) }
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?
            ) {
                currentFilteredList = (results?.values as? List<Country>) ?: masterCountries
                loadInitialPage()
                notifyDataSetChanged()
            }
        }
    }
}