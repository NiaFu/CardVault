package com.neurocom.cardvault.util;

public class MaskingUtil {
    private  MaskingUtil() {}

    public static String maskFromLast4 (String last4) {
        return "**** **** ****" + last4;
    }
}
