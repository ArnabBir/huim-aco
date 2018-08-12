package com.maths.huim.models;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

//    private String keyItem;
//    private HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
//    private boolean isLeaf;

    String keyItem;
    HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
    boolean isLeaf;

    public TrieNode() {}

    public TrieNode(String keyItem){
        this.keyItem = keyItem;
    }

    public String getKeyItem() {
        return this.keyItem;
    }

    public void setKeyItem(String keyItem) {
        this.keyItem = keyItem;
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

        String trieString = "";
        for(Map.Entry<String, TrieNode> node : this.children.entrySet()) {
            trieString += node.getKey() + " -> {" + node.getValue() + " } ";
        }
        return trieString;
        //trieString += "] ";
        //return String.valueOf(/*this.keyItem + " -> " +*/ children);
    }
}
