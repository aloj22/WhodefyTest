package com.aloj.whodefytest.ui.contacts

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aloj.whodefytest.model.Contact

/**
 * Fragment for displaying contact list
 */
class ContactListFragment : Fragment(), ContactListView {

    private var mListener: ContactListListener? = null
    private val mPresenter = ContactListPresenter()
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRecyclerView = RecyclerView(context)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.setBackgroundColor(Color.WHITE)
        mPresenter.mView = this
        mPresenter.onCreateView()
        return mRecyclerView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ContactListListener) {
            mListener = context
        } else {
            throw ClassCastException(context!!.javaClass.name + " must implement ContactListListener")
        }
    }

    override fun showContacts(contacts: List<Contact>) {
        val adapter = ContactAdapter(contacts) {
            mListener?.onContactSelected(it)
        }
        mRecyclerView.adapter = adapter
    }

    interface ContactListListener {
        fun onContactSelected(contact: Contact)
    }
}
