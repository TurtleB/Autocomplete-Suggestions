package com.btfrale.autocomplete.suggestion;

public class RadixEdge {
    String label;
    RadixNode target;


    //
    public RadixEdge(String label, RadixNode target) {
        this.label = label;
        this.target = target;
    }
}
