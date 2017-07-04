package com.aloj.whodefytest.ui.contacts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.aloj.whodefytest.R
import com.aloj.whodefytest.model.Contact

/**
 * Adapter for displaying contact list
 */
class ContactAdapter(private val mContactList: List<Contact>, var mContactSelectedListener: ((Contact) -> Unit)? = null)
    : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var mSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindView(mContactList[position])
    }

    override fun getItemCount() = mContactList.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mContactName: TextView = itemView as TextView

        fun bindView(contact: Contact) {
            mContactName.text = contact.name
            mContactName.setOnClickListener {
                mContactSelectedListener?.invoke(contact)
                mSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }

            if (mSelectedPosition == adapterPosition) {
                mContactName.setBackgroundResource(R.color.received_message_background)
                mContactName.setTextColor(ContextCompat.getColor(mContactName.context, R.color.white))
            } else {
                mContactName.setBackgroundResource(android.R.color.transparent)
                mContactName.setTextColor(ContextCompat.getColor(mContactName.context, android.R.color.black))
            }
        }
    }
}
