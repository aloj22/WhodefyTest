package com.aloj.whodefytest.ui.contacts

import com.aloj.whodefytest.util.execute

/**
 * Presenter for displaying contacts
 */
class ContactListPresenter {

    var mView: ContactListView? = null
    val mGetContactsUseCase = GetContactsUseCase()

    fun onCreateView() {
        mGetContactsUseCase.getContacts().execute {
            mView?.showContacts(it)
        }
    }

}