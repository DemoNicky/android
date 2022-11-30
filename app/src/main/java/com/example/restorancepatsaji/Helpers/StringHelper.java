package com.example.restorancepatsaji.Helpers;

public class StringHelper {

    public static boolean regexEmailVeriv(String email){

        String regex = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
        if (email.matches(regex)){
            return true;
        }
        return false;
    }
}
