package com.btfrale.autocomplete.suggestion;

import com.btfrale.autocomplete.util.StringUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


//
public class RadixNode {

    // Sorted by weight.
    private LinkedList<RadixEdge> edges;
    private int weight;


    //
    public RadixNode(int weight) {
        this.weight = weight;
        this.edges = new LinkedList<>();
    }


    //
    public RadixNode(int weight, String key) {
        this.weight = weight;
        this.edges = new LinkedList<>();
        insertEdgeInOrder(new RadixEdge(key, new RadixNode(weight)));
    }


    //
    public void insert(String remainingKey, int weight) {
        if(isLeaf()) {
            insertOnLeaf(remainingKey, weight);
        } else {
            RadixEdge nextEdge = getNextEdge(remainingKey);
            if(null != nextEdge) {
                // Continue searching.
                nextEdge.target.insert(remainingKey.substring(nextEdge.label.length()), weight);
                edges.remove(nextEdge);
                insertEdgeInOrder(nextEdge);
                this.weight = edges.get(0).target.weight;
            } else if(remainingKey.isEmpty()) {
                insertEdgeInOrder(new RadixEdge(remainingKey, new RadixNode(weight)));
            } else {
                insertSplit(remainingKey, weight);
            }
        }
    }


    //
    public RadixNode lookup(String key) {
        if(isLeaf()) {
            if(key.isEmpty()) {
                return this;
            } else {
                return null;
            }
        } else {
            RadixEdge nextEdge = getNextEdge(key);
            if(null != nextEdge) {
                return nextEdge.target.lookup(key.substring(nextEdge.label.length()));
            } else {
                return null;
            }
        }
    }


    //
    public PrefixLookupResult lookupPrefix(String key) {
        return lookupPrefix("", key);
    }


    //
    public ResultList getTopResultsByWeight(ResultList resultList, String keySoFar) {
        if(isLeaf()) {
            resultList.addResult(new WeightedResult(keySoFar, 0));
        }
        for(RadixEdge e : edges) {
            if(e.target.isLeaf() && resultList.canAddWeight(e.target.weight)) {
                resultList.addResult(new WeightedResult(keySoFar + e.label, e.target.weight));
            } else {
                e.target.getTopResultsByWeight(resultList, keySoFar + e.label);
            }
        }
        return resultList;
    }


    //
    private PrefixLookupResult lookupPrefix(String keySoFar, String remainingKey) {
        if(isLeaf()) {
            return new PrefixLookupResult(this, keySoFar);
        } else {
            RadixEdge nextEdge = getNextEdge(remainingKey);
            if(null != nextEdge) {
                return nextEdge.target.lookupPrefix(keySoFar + nextEdge.label, remainingKey.substring(nextEdge.label.length()));
            } else if(remainingKey.isEmpty()){
                return new PrefixLookupResult(this, keySoFar);
            } else {
                for(RadixEdge e : edges) {
                    if(e.label.startsWith(remainingKey)) {
                        return new PrefixLookupResult(e.target, keySoFar + e.label);
                    }
                }
                return null;
            }
        }
    }


    //
    private void insertOnLeaf(String remainingKey, int weight) {
        if(remainingKey.isEmpty()) {
            // If this node matches the key being inserted, then update the value for that key.
            this.weight += weight;
        } else {
            // Add a new edge to preserve the value of the current node.
            insertEdgeInOrder(new RadixEdge("", new RadixNode(weight)));
            // Add a new edge for the rest of the key.
            insertEdgeInOrder(new RadixEdge(remainingKey, new RadixNode(weight)));
            if(weight > this.weight) {
                this.weight = weight;
            }
        }
    }


    //
    private void insertSplit(String remainingKey, int weight) {
        RadixEdge nextEdge = null;
        String remainingKeyNextChar = remainingKey.substring(0, 1);
        Iterator<RadixEdge> iterator = edges.iterator();
        while(nextEdge == null && iterator.hasNext()) {
            RadixEdge edge = iterator.next();
            if(edge.label.startsWith(remainingKeyNextChar)) {
                nextEdge = edge;
            }
        }
        if(nextEdge != null) {
            String sharedPrefix = StringUtil.getCommonPrefix(nextEdge.label, remainingKey);
            String splitEdgeSuffix = nextEdge.label.substring(sharedPrefix.length());
            nextEdge.label = sharedPrefix;
            RadixNode splitNode = nextEdge.target;
            RadixEdge splitEdge = new RadixEdge(splitEdgeSuffix, new RadixNode(splitNode.weight));
            splitEdge.target.edges = splitNode.edges;
            splitNode.edges = new LinkedList<>();
            splitNode.insertEdgeInOrder(splitEdge);
            splitNode.insert(remainingKey.substring(sharedPrefix.length()), weight);
        } else {
            insertEdgeInOrder(new RadixEdge(remainingKey, new RadixNode(weight)));
        }
    }


    //
    private void insertEdgeInOrder(RadixEdge e) {
        if(edges.isEmpty()) {
            edges.add(e);
        } else {
            ListIterator<RadixEdge> edgeIterator = edges.listIterator();
            while(edgeIterator.hasNext()) {
                RadixEdge nextEdge = edgeIterator.next();
                if(nextEdge.target.weight < e.target.weight) {
                    edgeIterator.previous();
                    edgeIterator.add(e);
                    return;
                }
            }
            edgeIterator.add(e);
        }
    }


    //
    private RadixEdge getNextEdge(String remainingKey) {
        RadixEdge nextEdge = null;
        if(!remainingKey.isEmpty()) {
            for(RadixEdge edge : edges) {
                if(!edge.label.isEmpty() && remainingKey.startsWith(edge.label)) {
                    nextEdge = edge;
                }
            }
        } else {
            for(RadixEdge edge : edges) {
                if(edge.label.isEmpty()) {
                    nextEdge = edge;
                }
            }
        }
        return nextEdge;
    }


    //
    private boolean isLeaf() {
        return edges.isEmpty();
    }
}
