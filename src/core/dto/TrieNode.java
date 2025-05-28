package core.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class TrieNode {
    public HashMap<Character, TrieNode> children;
    public boolean isEndOfWord;
    public ArrayList<String> cities;

    public TrieNode(){
        children = new HashMap<>();
        isEndOfWord = false;
        cities = new ArrayList<>();
    }
}
