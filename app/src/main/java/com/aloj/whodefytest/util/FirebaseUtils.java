package com.aloj.whodefytest.util;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {


    public static DatabaseReference getMessagesReference() {
        return FirebaseDatabase.getInstance().getReference("messages");
    }

}
