package com.maths.huim.models;

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "{ " + this.root.getKeyItem() + " -> {" + this.root + " } }";
    }
}