package com.btfrale.autocomplete.suggestion;

import java.util.ArrayList;
import java.util.List;

public class SuggestionTree {
    private RadixNode root;


    // default constructor
    public SuggestionTree() {
        this.root = null;
    }


    //
    public boolean containsKey(String key) {
        if(root == null) {
            return false;
        }
        return null != root.lookup(key);
    }


    //
    public boolean containsPrefix(String key) {
        if(root == null) {
            return false;
        }
        return null != root.lookupPrefix(key);
    }


    //
    public void insert(String key, int weight) {
        if(root != null) {
            root.insert(key, weight);
        } else {
            root = new RadixNode(weight, key);
        }
    }


    //
    public List<String> getTopResults(String prefix, int numResults) {
        PrefixLookupResult prefixResult = root.lookupPrefix(prefix);
        if(prefixResult != null) {
            ResultList resultList = new ResultList(numResults);
            resultList = prefixResult.getNode().getTopResultsByWeight(resultList, prefixResult.getPrefix());
            return resultList.getResultKeys();
        } else {
            return new ArrayList<>();
        }
    }


}
