package com.btfrale.autocomplete.suggestion;

import com.btfrale.autocomplete.suggestion.SuggestionTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;


@RunWith(JUnit4.class)
public class SuggestionTreeTest {


    //
    @Test
    public void testContainsKey_emptyTree() {
        SuggestionTree suggestionTree = new SuggestionTree();
        Assert.assertFalse(suggestionTree.containsKey(""));
        Assert.assertFalse(suggestionTree.containsKey("ayy"));
        Assert.assertFalse(suggestionTree.containsKey(" "));
    }


    //
    @Test
    public void testContainsKey_keyExists() {
        SuggestionTree suggestionTree = buildTestTree();

        Assert.assertTrue(suggestionTree.containsKey("hill"));
        Assert.assertTrue(suggestionTree.containsKey("hit"));
        Assert.assertTrue(suggestionTree.containsKey("hi"));
        Assert.assertTrue(suggestionTree.containsKey("by"));
        Assert.assertTrue(suggestionTree.containsKey("byte"));
        Assert.assertTrue(suggestionTree.containsKey("bye"));
        Assert.assertTrue(suggestionTree.containsKey("blue"));
    }


    //
    @Test
    public void testContainsKey_keyDoesNotExist() {
        SuggestionTree suggestionTree = buildTestTree();

        Assert.assertFalse(suggestionTree.containsKey("car"));
        Assert.assertFalse(suggestionTree.containsKey("cards"));
        Assert.assertFalse(suggestionTree.containsKey("c"));
        Assert.assertFalse(suggestionTree.containsKey(""));
    }


    //
    @Test
    public void testContainsPrefix_prefixExists() {
        SuggestionTree suggestionTree = buildTestTree();

        Assert.assertTrue(suggestionTree.containsPrefix(""));
        Assert.assertTrue(suggestionTree.containsPrefix("c"));
        Assert.assertTrue(suggestionTree.containsPrefix("cr"));
        Assert.assertTrue(suggestionTree.containsPrefix("cra"));
        Assert.assertTrue(suggestionTree.containsPrefix("crazy"));
        Assert.assertTrue(suggestionTree.containsPrefix("by"));
        Assert.assertTrue(suggestionTree.containsPrefix("hil"));
        Assert.assertTrue(suggestionTree.containsPrefix("ca"));
    }


    //
    @Test
    public void testContainsPrefix_prefixDoesNotExist() {
        SuggestionTree suggestionTree = buildTestTree();

        Assert.assertFalse(suggestionTree.containsPrefix("z"));
        Assert.assertFalse(suggestionTree.containsPrefix("him"));
        Assert.assertFalse(suggestionTree.containsPrefix("a"));
        Assert.assertFalse(suggestionTree.containsPrefix(" "));
    }


    //
    @Test
    public void testGetTopResults() {
        SuggestionTree tree = buildTestTree();

        List<String> resultList = tree.getTopResults("", 5);
        Assert.assertEquals(5, resultList.size());
        String[] expectedOrder = {"hill", "hi", "bye", "blue", "hit"};
        for(int i = 0; i < resultList.size(); i++) {
            Assert.assertEquals(expectedOrder[i], resultList.get(i));
        }

        resultList = tree.getTopResults("h", 5);
        String[] expectedOrder2 = {"hill", "hi", "hit"};
        Assert.assertEquals(3, resultList.size());
        for(int i = 0; i < resultList.size(); i++) {
            Assert.assertEquals(expectedOrder2[i], resultList.get(i));
        }

        resultList = tree.getTopResults("car", 5);
        String[] expectedOrder3 = {"card", "cartridge"};
        Assert.assertEquals(2, resultList.size());
        for(int i = 0; i < resultList.size(); i++) {
            Assert.assertEquals(expectedOrder3[i], resultList.get(i));
        }

        resultList = tree.getTopResults("card", 5);
        String[] expectedOrder4 = {"card"};
        Assert.assertEquals(1, resultList.size());
        for(int i = 0; i < resultList.size(); i++) {
            Assert.assertEquals(expectedOrder4[i], resultList.get(i));
        }
    }


    //
    private SuggestionTree buildTestTree() {
        SuggestionTree suggestionTree = new SuggestionTree();
        suggestionTree.insert("card", 7);
        suggestionTree.insert("cartridge", 2);
        suggestionTree.insert("crazy", 11);
        suggestionTree.insert("hill", 80);
        suggestionTree.insert("hit", 13);
        suggestionTree.insert("hi", 55);
        suggestionTree.insert("by", 1);
        suggestionTree.insert("byte", 0);
        suggestionTree.insert("bye", 51);
        suggestionTree.insert("blue", 22);
        return suggestionTree;
    }
}
