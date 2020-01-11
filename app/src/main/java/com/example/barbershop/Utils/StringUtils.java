package com.example.barbershop.Utils;

import java.util.Random;

public class StringUtils {

    public static String generateRandomString (char start , char end , int length) {
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randC = (char) (new Random().nextInt(end - start) + start + 1) ;
            randomStr.append(randC);
        }
        return randomStr.toString();
    }
}
