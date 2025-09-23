package com.niafu.cardvault.util;
/**
 * Utility for masking PAN values for safe display.
 */

public class MaskingUtil {
    private  MaskingUtil() {}

    /** Return masked PAN string using only the last 4 digits. */
    public static String maskFromLast4 (String last4) {
        return "**** **** " + last4;
    }
}
