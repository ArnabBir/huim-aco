package com.maths.huim.models;

import java.util.*;

public class TrieNode {

    private String keyItem;
    private List<String> itemSet;
    private HashMap<String, TrieNode> children;
    private boolean isLeaf;

    public TrieNode() {

        this.keyItem = "";
        this.itemSet = new ArrayList<>();
        this.children = new HashMap<String, TrieNode>();
        this.isLeaf = false;
    }

    public TrieNode(String keyItem){
        this.keyItem = keyItem;
        this.setItemSet(Arrays.asList(keyItem));
    }

    public String getKeyItem() {
        return this.keyItem;
    }

    public void setKeyItem(String keyItem) {
        this.keyItem = keyItem;
    }

    public List<String> getItemSet() {
        return itemSet;
    }

    public void setItemSet(List<String> itemSet) {
        this.itemSet = itemSet;
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
    }
}
