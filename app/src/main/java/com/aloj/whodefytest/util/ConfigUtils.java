package com.aloj.whodefytest.util;


import com.aloj.whodefytest.model.Contact;

import java.util.ArrayList;

public class ConfigUtils {

    public static final ArrayList<Contact> CONTACTS = new ArrayList<Contact>(){{
       add(new Contact("Alberto"));
       add(new Contact("Alonso"));
       add(new Contact("Ana"));
       add(new Contact("Carmen"));
       add(new Contact("Juan"));
       add(new Contact("María"));
       add(new Contact("Pedro"));
    }};

    public static final String CURRENT_USER_NAME = "Juan";

}
