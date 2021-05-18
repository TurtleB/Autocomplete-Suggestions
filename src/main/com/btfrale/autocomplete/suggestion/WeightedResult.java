package com.btfrale.autocomplete.suggestion;

public class WeightedResult {

    private final String key;
    private final int weight;


    //
    public WeightedResult(String key, int weight) {
        this.key = key;
        this.weight = weight;
    }


    //
    public String getKey() {
        return key;
    }


    //
    public int getWeight() {
        return weight;
    }
}
