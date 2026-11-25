package com.zulalnb.countrylistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CountryAdapter(context: Context, countries: ArrayList<Country>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val countries = countries

    // Her satırın View bileşenlerini saklar.
    private data class ViewHolder(
        val tvName: TextView,
        val ivFlag: ImageView
    )

    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(position: Int): Any {
        return countries[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Daha önce oluşturulmuş bir View yoksa yeni satır oluştur.
            view = inflater.inflate(R.layout.list_item, parent, false)

            holder = ViewHolder(
                view.findViewById(R.id.tvName),
                view.findViewById(R.id.ivFlag)
            )

            view.tag = holder
        } else {
            // Mevcut View nesnesini yeniden kullan.
            view = convertView
            holder = view.tag as ViewHolder
        }

        // İlgili pozisyondaki ülke verilerini View bileşenlerine aktar.
        val country = countries[position]

        holder.tvName.text = country.name
        holder.ivFlag.setImageResource(country.flagId)

        return view
    }
}
