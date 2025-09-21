package com.neurocom.cardvault.util;

/**
 * Verify the cards information
 */
public class LuhnUtil {
    private LuhnUtil() {}

    public static boolean isValidPan(String digits) {
        int sum = 0;
        boolean dbl = false;
        for (int i = digits.length() - 1; i >= 0; i--) {
            int n = digits.charAt(i) - '0';
            if (dbl) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            dbl = true;
        }
        return sum % 10 == 0;
    }
}
