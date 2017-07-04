package com.aloj.whodefytest.util;


import android.content.Context;
import android.text.format.DateFormat;


public class Utils {

    public static String formatTime(Context context, long time) {
        return DateFormat.getTimeFormat(context).format(time);
    }

}
