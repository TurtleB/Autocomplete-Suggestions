package com.btfrale.autocomplete.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {


    //
    @Test
    public void testGetCommonPrefix_matchingStrings() {
        testGetCommonPrefix_helper("", "", "");
        testGetCommonPrefix_helper("a", "a", "a");
        testGetCommonPrefix_helper("abc", "abc", "abc");
        testGetCommonPrefix_helper(" xyz", " xyz", " xyz");
    }


    //
    @Test
    public void testGetCommonPrefix_entireStringIsPrefix() {
        testGetCommonPrefix_helper("abcd", "", "");
        testGetCommonPrefix_helper("abcd", "a", "a");
        testGetCommonPrefix_helper("abcd", "ab", "ab");
        testGetCommonPrefix_helper("", "abcd", "");
        testGetCommonPrefix_helper("a", "abcd", "a");
        testGetCommonPrefix_helper("ab", "abcd", "ab");
    }


    //
    @Test
    public void testGetCommonPrefix_partialStringIsPrefix() {
        testGetCommonPrefix_helper("card", "cartridge", "car");
    }


    //
    @Test
    public void testGetCommonPrefix_noPrefix() {
        testGetCommonPrefix_helper("abcd", "xyz", "");
    }


    //
    private void testGetCommonPrefix_helper(String a, String b, String expected) {
        String result = StringUtil.getCommonPrefix(a, b);
        Assert.assertEquals(expected, result);
    }
}
