package com.zulalnb.countrylistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CountryAdapter(context: Context, countries: ArrayList<Country>): BaseAdapter() {
    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val countries = countries

    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(position: Int): Any? {
        return countries.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val view = inflater.inflate(R.layout.list_item, parent, false)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val ivFlag = view.findViewById<ImageView>(R.id.ivFlag)
        tvName.text = countries.get(position).name
        ivFlag.setImageResource(countries.get(position).flagId)

        return view
    }
}