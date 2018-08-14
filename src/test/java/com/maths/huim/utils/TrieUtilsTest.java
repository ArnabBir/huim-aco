package com.maths.huim.utils;

import com.maths.huim.models.Trie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrieUtilsTest {

    @Test
    public void insert() {

        Trie trie = new Trie();
        TrieUtils trieUtils = new TrieUtils();

        trieUtils.insert(trie, Arrays.asList("t", "h", "e"));
        trieUtils.insert(trie, Arrays.asList("a"));
        trieUtils.insert(trie, Arrays.asList( "t", "h", "e", "r", "e"));
        trieUtils.insert(trie, Arrays.asList( "a", "n", "s", "w", "e", "r"));
        trieUtils.insert(trie, Arrays.asList("a", "n", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y", "e"));
        trieUtils.insert(trie, Arrays.asList("t", "h", "e", "i", "r"));
        System.out.println(trie);

    }

    @Test
    public void search() {

        Trie trie = new Trie();
        TrieUtils trieUtils = new TrieUtils();

        trieUtils.insert(trie, Arrays.asList("t", "h", "e"));
        trieUtils.insert(trie, Arrays.asList("a"));
        trieUtils.insert(trie, Arrays.asList( "t", "h", "e", "r", "e"));
        trieUtils.insert(trie, Arrays.asList( "a", "n", "s", "w", "e", "r"));
        trieUtils.insert(trie, Arrays.asList("a", "n", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y", "e"));
        trieUtils.insert(trie, Arrays.asList("t", "h", "e", "i", "r"));

        assertEquals(true, trieUtils.search(trie, Arrays.asList("t", "h", "e", "i", "r")));
        assertEquals(false, trieUtils.search(trie, Arrays.asList("t", "h", "e", "i")));
        assertEquals(true, trieUtils.search(trie, Arrays.asList("a", "n", "y")));
        assertEquals(false, trieUtils.search(trie, Arrays.asList("b", "y", "e", "e")));
    }

    @Test
    public void delete() {

        Trie trie = new Trie();
        TrieUtils trieUtils = new TrieUtils();

        trieUtils.insert(trie, Arrays.asList("t", "h", "e"));
        trieUtils.insert(trie, Arrays.asList("a"));
        trieUtils.insert(trie, Arrays.asList( "t", "h", "e", "r", "e"));
        trieUtils.insert(trie, Arrays.asList( "a", "n", "s", "w", "e", "r"));
        trieUtils.insert(trie, Arrays.asList("a", "n", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y"));
        trieUtils.insert(trie, Arrays.asList("b", "y", "e"));
        trieUtils.insert(trie, Arrays.asList("t", "h", "e", "i", "r"));

        System.out.println("Testing delete :");
        System.out.println("Before deleting : " + trieUtils.search(trie, Arrays.asList("t", "h", "e", "i", "r")));
        trieUtils.delete(trie, Arrays.asList("t", "h", "e", "i", "r"));
        System.out.println("After deleting : " + trieUtils.search(trie, Arrays.asList("t", "h", "e", "i", "r")));

        // Assertions.assertEquals("w", trieUtils.search(trie, "w"));
    }
}
