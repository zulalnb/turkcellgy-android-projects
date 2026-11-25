package com.zulalnb.contactmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private var contacts: ArrayList<Contact>,
    private val onItemClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item,
                parent,
                false
            )
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val contact = contacts[position]
        holder.bind(contact, onItemClick)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun appendContacts(newContacts: List<Contact>) {
        val startPosition = contacts.size
        contacts.addAll(newContacts)
        notifyItemRangeInserted(startPosition, newContacts.size)
    }

    fun setContacts(newContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
    }
}
