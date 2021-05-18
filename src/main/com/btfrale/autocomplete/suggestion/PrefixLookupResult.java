package com.btfrale.autocomplete.suggestion;

public class PrefixLookupResult {

    private final RadixNode node;
    private final String prefix;

    //
    public PrefixLookupResult(RadixNode node, String prefix) {
        this.node = node;
        this.prefix = prefix;
    }


    //
    public RadixNode getNode() {
        return node;
    }


    //
    public String getPrefix() {
        return this.prefix;
    }
}
