package com.aloj.whodefytest.ui.contacts


import com.aloj.whodefytest.model.Contact

interface ContactListView {

    fun showContacts(contacts: List<Contact>)

}
