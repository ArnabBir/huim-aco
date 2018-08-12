package test.models;

import com.maths.huim.models.Trie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TrieTest {

    @Test
    public void insert() {

        Trie trie = new Trie();
        trie.insert(Arrays.asList("t", "h", "e"));
        trie.insert(Arrays.asList("a"));
        trie.insert(Arrays.asList( "t", "h", "e", "r", "e"));
        trie.insert(Arrays.asList( "a", "n", "s", "w", "e", "r"));
        trie.insert(Arrays.asList("a", "n", "y"));
        trie.insert(Arrays.asList("b", "y"));
        trie.insert(Arrays.asList("b", "y", "e"));
        trie.insert(Arrays.asList("t", "h", "e", "i", "r"));
        System.out.println(trie.getRoot());
    }
}
