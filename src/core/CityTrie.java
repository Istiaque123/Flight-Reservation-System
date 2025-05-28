package core;

import core.dto.TrieNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CityTrie {
    private TrieNode root;

    public CityTrie() {
        root = new TrieNode();
    }

    public void insert(String city){
        TrieNode current = this.root;
        for (char ch: city.toLowerCase().toCharArray()){
            current = current.children.computeIfAbsent(ch, c-> new TrieNode());
            if (!current.cities.contains(city)){
                current.cities.add(city);
            }
        }

        current.isEndOfWord = true;
    }

    public ArrayList<String> searchPrefix(String prefix){
        TrieNode current = this.root;

        for (char ch: prefix.toLowerCase().toCharArray()){
            TrieNode tempNode = current.children.get(ch);
            if (tempNode == null) {
                return new ArrayList<>();
            }
            current = tempNode;
        }
        return current.cities;
    }

    public boolean contains(String city){
        TrieNode current = this.root;
        for (char ch: city.toLowerCase().toCharArray()){
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord;
    }

}
