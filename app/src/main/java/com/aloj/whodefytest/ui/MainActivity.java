package com.aloj.whodefytest.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aloj.whodefytest.R;
import com.aloj.whodefytest.model.Contact;
import com.aloj.whodefytest.ui.chat.ChatFragment;
import com.aloj.whodefytest.ui.contacts.ContactListFragment;
import com.aloj.whodefytest.util.ConfigUtils;

public class MainActivity extends AppCompatActivity implements ContactListFragment.ContactListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.connected_as, ConfigUtils.CURRENT_USER_NAME));
    }

    @Override
    public void onContactSelected(@NonNull Contact contact) {
        Fragment chatFragment = getSupportFragmentManager().findFragmentById(R.id.chat_fragment);
        if (chatFragment != null && chatFragment instanceof ChatFragment) {
            ((ChatFragment) chatFragment).onContactSelected(contact);
        }
    }
}
