package com.btfrale.autocomplete.suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultList {

    private final int targetCount;
    private final List<WeightedResult> results;


    //
    public ResultList(int targetCount) {
        this.targetCount = targetCount;
        this.results = new ArrayList<>(targetCount);
    }


    //
    public boolean addResult(WeightedResult result) {
        if(!results.isEmpty()) {
            int insertIndex = 0;
            boolean canInsert = false;
            while(insertIndex < results.size() && !canInsert) {
                if(results.get(insertIndex).getWeight() < result.getWeight()) {
                    canInsert = true;
                } else {
                    insertIndex++;
                }
            }
            if(canInsert || results.size() < targetCount) {
                if(results.size() >= targetCount) {
                    results.remove(targetCount - 1);
                }
                results.add(insertIndex, result);
                return true;
            }
        } else {
            results.add(result);
            return true;
        }
        return false;
    }


    //
    public boolean canAddWeight(int weight) {
        if(results.size() >= targetCount) {
            return results.get(results.size() - 1).getWeight() < weight;
        } else {
            return true;
        }
    }


    //
    public List<String> getResultKeys() {
        return results.stream()
                .map(WeightedResult::getKey)
                .collect(Collectors.toList());
    }
}
