package com.book.donation.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean validateString(String str) {
        return stringNotNull(str) && stringNotEmpty(str);
    }

    public static boolean stringNotNull(String str) {
        return str != null;
    }

    public static boolean stringNotEmpty(String str) {
        return !str.isEmpty();
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
            isValid = true;
        return isValid;
    }

    public static final boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10 && Patterns.PHONE.matcher(phone).matches();
    }
}
