package com.maths.huim.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Inserts a word into the trie.
    public void insert(List<String> word) {
        HashMap<String, TrieNode> children = root.children;

        for(int i=0; i<word.size(); i++){
            String item = word.get(i);

            TrieNode t;
            if(children.containsKey(item)){
                t = children.get(item);
            }else{
                t = new TrieNode(item);
                children.put(item, t);
            }

            children = t.children;

            //set leaf node
            if(i==word.size()-1)
                t.isLeaf = true;
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);

        if(t != null && t.isLeaf)
            return true;
        else
            return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(String str){
        Map<String, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }

        return t;
    }
}