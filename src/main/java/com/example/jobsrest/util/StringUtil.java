package com.example.jobsrest.util;

public class StringUtil {

    public String trim(String msg) {
        if (msg == null) {
            return "";
        }
        return msg.trim();
    }

    public String revert(String msg) {
        if (msg == null) {
            return null;
        }
        return new StringBuilder(msg).reverse().toString();
    }
}
