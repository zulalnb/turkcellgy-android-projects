package com.zulalnb.countryrecyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryViewHolder(itemView: View, itemClick: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    var ivFlag: ImageView
    var tvName: TextView

    init {
        ivFlag = itemView.findViewById(R.id.ivFlag)
        tvName = itemView.findViewById(R.id.tvName)

        itemView.setOnClickListener {
            val position = bindingAdapterPosition

            if (position != RecyclerView.NO_POSITION) {
                itemClick(position)
            }
        }
    }
}