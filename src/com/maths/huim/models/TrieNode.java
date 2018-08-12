package com.maths.huim.models;

import java.util.HashMap;

public class TrieNode {

    String item;
    HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
    boolean isLeaf;

    public TrieNode() {}

    public TrieNode(String item){
        this.item = item;
    }

    @Override
    public String toString() {
        return String.valueOf(/*this.item + " -> " +*/ children);
    }
}
