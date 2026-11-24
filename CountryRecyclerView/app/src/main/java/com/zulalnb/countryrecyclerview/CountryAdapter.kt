package com.zulalnb.countryrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    var countries: ArrayList<Country>,
    itemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<CountryViewHolder>() {
    val itemClick = itemClick
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
}