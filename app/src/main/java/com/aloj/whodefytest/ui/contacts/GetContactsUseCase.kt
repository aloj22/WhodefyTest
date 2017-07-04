package com.aloj.whodefytest.ui.contacts


import com.aloj.whodefytest.model.Contact
import com.aloj.whodefytest.util.ConfigUtils

import io.reactivex.Observable

/**
 * Use case for getting contacts
 */
class GetContactsUseCase {


    fun getContacts(): Observable<List<Contact>> {
        return Observable.create { it.onNext(ConfigUtils.CONTACTS.filter { !it.name.equals(ConfigUtils.CURRENT_USER_NAME) }) }
    }

}
