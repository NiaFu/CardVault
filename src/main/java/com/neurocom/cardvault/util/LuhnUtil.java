package com.neurocom.cardvault.util;

/**
 * Verify the cards information
 */
public final class LuhnUtil {
    private LuhnUtil() {}
    public static boolean isValidPan(String digits) {
        int sum = 0;
        boolean doubleIt = false; // 从最右位起隔位*2
        for (int i = digits.length() - 1; i >= 0; i--) {
            int n = digits.charAt(i) - '0';
            if (n < 0 || n > 9) return false; // 不是 0-9
            if (doubleIt) { n *= 2; if (n > 9) n -= 9; }
            sum += n;
            doubleIt = !doubleIt;
        }
        return sum % 10 == 0;
    }
}
