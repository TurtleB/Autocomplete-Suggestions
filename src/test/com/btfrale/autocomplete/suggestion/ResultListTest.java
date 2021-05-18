package com.btfrale.autocomplete.suggestion;

import com.btfrale.autocomplete.suggestion.ResultList;
import com.btfrale.autocomplete.suggestion.WeightedResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ResultListTest {

    //
    @Test
    public void testAddResult() {
        ResultList resultList = new ResultList(5);

        Assert.assertTrue(resultList.addResult(new WeightedResult("hello", 10)));
        Assert.assertTrue(resultList.addResult(new WeightedResult("world", 4)));
        Assert.assertTrue(resultList.addResult(new WeightedResult("how", 6)));
        Assert.assertTrue(resultList.addResult(new WeightedResult("are", 2)));
        Assert.assertTrue(resultList.addResult(new WeightedResult("you", 9)));

        Assert.assertFalse(resultList.addResult(new WeightedResult("hi", 1)));
        Assert.assertFalse(resultList.addResult(new WeightedResult("cat", 0)));
        Assert.assertFalse(resultList.addResult(new WeightedResult("dog", -1)));
    }


    //
    @Test
    public void testCanAddWeight_emptyResultList() {
        ResultList resultList = new ResultList(5);

        Assert.assertTrue(resultList.canAddWeight(0));
        Assert.assertTrue(resultList.canAddWeight(-1));
        Assert.assertTrue(resultList.canAddWeight(90001));
    }


    //
    @Test
    public void testCanAddWeight_partialResultList() {
        ResultList resultList = new ResultList(5);

        resultList.addResult(new WeightedResult("hello", 10));
        Assert.assertTrue(resultList.canAddWeight(0));
        Assert.assertTrue(resultList.canAddWeight(-1));
        Assert.assertTrue(resultList.canAddWeight(90001));

        resultList.addResult(new WeightedResult("world", 4));
        resultList.addResult(new WeightedResult("how", 6));
        resultList.addResult(new WeightedResult("are", -10));
        Assert.assertTrue(resultList.canAddWeight(0));
        Assert.assertTrue(resultList.canAddWeight(-1));
        Assert.assertTrue(resultList.canAddWeight(90001));

    }


    //
    @Test
    public void testCanAddWeight_fullResultList() {
        ResultList resultList = new ResultList(5);

        resultList.addResult(new WeightedResult("hello", 10));
        resultList.addResult(new WeightedResult("world", 4));
        resultList.addResult(new WeightedResult("how", 6));
        resultList.addResult(new WeightedResult("are", 2));
        resultList.addResult(new WeightedResult("you", 9));

        Assert.assertTrue(resultList.canAddWeight(20));
        Assert.assertTrue(resultList.canAddWeight(8));
        Assert.assertTrue(resultList.canAddWeight(3));

        Assert.assertFalse(resultList.canAddWeight(1));
        Assert.assertFalse(resultList.canAddWeight(-1));
    }


    //
    @Test
    public void testGetResultKeys() {
        ResultList resultList = new ResultList(5);

        resultList.addResult(new WeightedResult("hello", 10));
        resultList.addResult(new WeightedResult("world", 4));
        resultList.addResult(new WeightedResult("how", 6));
        resultList.addResult(new WeightedResult("are", 2));
        resultList.addResult(new WeightedResult("you", 9));

        List<String> resultKeys = resultList.getResultKeys();
        String[] expectedOrder = {"hello", "you", "how", "world", "are"};
        Assert.assertEquals(5, resultKeys.size());
        for(int i = 0; i < resultKeys.size(); i++) {
            Assert.assertEquals(expectedOrder[i], resultKeys.get(i));
        }

        resultList.addResult(new WeightedResult("cat", 20));

        resultKeys = resultList.getResultKeys();
        String[] expectedOrder2 = {"cat", "hello", "you", "how", "world"};
        Assert.assertEquals(5, resultKeys.size());
        for(int i = 0; i < resultKeys.size(); i++) {
            Assert.assertEquals(expectedOrder2[i], resultKeys.get(i));
        }

        resultList.addResult(new WeightedResult("dog", 0));

        resultKeys = resultList.getResultKeys();
        Assert.assertEquals(5, resultKeys.size());
        for(int i = 0; i < resultKeys.size(); i++) {
            Assert.assertEquals(expectedOrder2[i], resultKeys.get(i));
        }
    }
}
