package com.tech.gigabyte.mediplus.util;

import java.util.Random;

/**
 * Created by GIGABYTE on 4/7/2017.
 * UTILITY Class for Checking Number/Price
 */
public class Utility {
    public static boolean isNum(String price) {
        for (int i = 0; i < price.length(); i++) {
            if (!Character.isDigit(price.charAt(i)))
                return false;
        }
        return true;
    }

    public static int RanNum() {
        Random rand = new Random();
        return rand.nextInt(200);
    }
}
