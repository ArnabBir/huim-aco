package com.maths.huim.models;

import java.util.HashMap;

public class TrieNode {

//    private String item;
//    private HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
//    private boolean isLeaf;

    String item;
    HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
    boolean isLeaf;

    public TrieNode() {}

    public TrieNode(String item){
        this.item = item;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public HashMap<String, TrieNode> getChildren() {
        return this.children;
    }

    public void setChildren(HashMap<String, TrieNode> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    @Override
    public String toString() {
        return String.valueOf(/*this.item + " -> " +*/ children);
    }
}
