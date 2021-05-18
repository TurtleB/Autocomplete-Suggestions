package com.btfrale.autocomplete.util;

public class StringUtil {

    //
    private StringUtil() {}

    /**
     * Gets the longest prefix shared between two Strings
     *
     * @param a the first String
     * @param b the second String
     * @return the longest prefix shared by a and b
     */
    public static String getCommonPrefix(String a, String b) {
        int i = 0;
        int maxSize = Integer.min(a.length(), b.length());
        char[] prefixChars = new char[maxSize];
        while(i < maxSize && (a.charAt(i) == b.charAt(i))) {
            prefixChars[i] = a.charAt(i);
            i++;
        }
        return new String(prefixChars).substring(0, i);
    }
}
