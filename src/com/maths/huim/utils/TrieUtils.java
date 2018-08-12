package com.maths.huim.utils;

import com.maths.huim.models.Trie;
import com.maths.huim.models.TrieNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieUtils {

    public TrieUtils(){}

    public void insert(Trie trie, List<String> word) {

        HashMap<String, TrieNode> children = trie.getRoot().getChildren();

        for(int i = 0; i < word.size(); ++i) {

            String item = word.get(i);
            TrieNode t;

            if(children.containsKey(item)){
                t = children.get(item);
            }else{
                t = new TrieNode(item);
                children.put(item, t);
            }

            children = t.getChildren();

            //set leaf node
            if(i==word.size()-1) {
                t.setLeaf(true);
            }
        }
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(Trie trie, List<String> itemSets) {

        if(searchNode(trie, itemSets) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(Trie trie, List<String> itemSet) {

        Map<String, TrieNode> children = trie.getRoot().getChildren();
        TrieNode t = null;
        for(int i = 0; i < itemSet.size(); ++i) {
            String item = itemSet.get(i);
            if(children.containsKey(item)) {
                t = children.get(item);
                children = t.getChildren();
            }else{
                return null;
            }
        }
        return t;
    }

    // Returns if the word is in the trie.
    public boolean search(Trie trie, List<String> itemSets) {

        TrieNode t = searchNode(trie, itemSets);
        if(t != null && t.isLeaf()) return true;
        else    return false;
    }

    public void delete(Trie trie, List<String> itemSets) {
        delete( trie.getRoot(), itemSets, 0);
    }

    private boolean delete( TrieNode current, List<String> word, int index) {

        if (index == word.size()) {
            if (!current.isLeaf()) {
                return false;
            }
            current.setLeaf(false);
            return current.getChildren().isEmpty();
        }

        String item = word.get(index);
        TrieNode node = current.getChildren().get(item);
        if (node == null)   return false;

        boolean shouldDeleteCurrentNode = delete( node, word, index + 1) && !node.isLeaf();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(item);
            return current.getChildren().isEmpty();
        }

        return false;
    }
}
