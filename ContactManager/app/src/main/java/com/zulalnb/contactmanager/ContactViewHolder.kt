package com.zulalnb.contactmanager

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvFullName: TextView = itemView.findViewById(R.id.tvFullName)
    private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

    fun bind(contact: Contact, onItemClick: (Contact) -> Unit) {
        tvFullName.text = "${contact.firstName} ${contact.lastName}"
        tvEmail.text = contact.email

        itemView.setOnClickListener {
            onItemClick(contact)
        }
    }
}
