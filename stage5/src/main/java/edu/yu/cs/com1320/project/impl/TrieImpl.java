package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;
//import edu.yu.cs.com1320.project.stage3.Document;

import java.util.*;

/**
 * FOR STAGE 3
 * @param <Value>
 */
public class TrieImpl<Value> implements Trie<Value>{
    //private final int alphabetSize = 36;
    private Node<Value> root; // root of trie


    private class Node<Value>
    {
        private List<Value> valueList = new ArrayList<Value>();
        private Node[] links = new Node[36];
    }


    /**
     * add the given value at the given key
     * @param key
     * @param val
     */
    public void put(String key, Value val){ 
       //deleteAll the value from this key
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (val == null || key.length() == 0){
            return;
        }else{
            this.root = put(this.root, key, val, 0);
        }
    }



    private Node put(Node x, String key, Value val, int d){
        //create a new node
        if (x == null) {
            x = new Node(); //how do i know whats stored here - i need to put the key here but no value
        }
        //String noCharacterText = key.replaceAll( "[^a-zA-Z0-9\\s]", "");
        key = key.toLowerCase();
        //we've reached the last node in the key,
        //so set the value for the key and return the node
        if (d == key.length()){
            if (!x.valueList.contains(val)) {
                x.valueList.add(val);
            }
            return x;
        }
        //else, proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        if ((int) c < 65){
            c = (char)(c-48);
        }else{
            c = (char)(c-87);
        }
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }

    /**
     * get all exact matches for the given key, sorted in descending order.
     * Search is CASE INSENSITIVE.
     * @param key
     * @param comparator used to sort  values
     * @return a List of matching Values, in descending order
     */
    public List<Value> getAllSorted(String key, Comparator<Value> comparator){
        if(key == null || comparator == null){
            throw new IllegalArgumentException("arguments are null");
        }
        if (key.length() == 0) {
            return new ArrayList<Value>();
        }
        //String noCharacterText = key.replaceAll( "[^a-zA-Z0-9\\s]", "");
        key = key.toLowerCase();
        List<Value> docList = new ArrayList<>();
        get(this.root, key, docList);
        docList.sort(comparator);
        return docList;
    }

    private void get(Node x, String key, List<Value> docList) {
        //link was null - return null, indicating a miss
        for (int counter = 0; counter < key.length() && x != null; counter++){
            char c = key.charAt(counter);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x = x.links[c];
        }
        if (x!=null){
            docList.addAll(x.valueList);
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        //return (List<Value>) this.get(x.links[c], key, d + 1);
    }

    /**
     * get all matches which contain a String with the given prefix, sorted in descending order.
     * For example, if the key is "Too", you would return any value that contains "Tool", "Too", "Tooth", "Toodle", etc.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @param comparator used to sort values
     * @return a List of all matching Values containing the given prefix, in descending order
     */
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator){
        if ( comparator == null || prefix == null) {
            throw new IllegalArgumentException("null arguments");
        }
        if (prefix.length() == 0) {
            return new ArrayList<Value>();
        }

        //String noCharacterText = prefix.replaceAll( "[^a-zA-Z0-9\\s]", "");
        prefix = prefix.toLowerCase();
        List<Value> docList = getWithPrefix(this.root, prefix, comparator);
        docList.sort(comparator);
        return docList;
    }

    private List<Value> getWithPrefix(Node x, String key, Comparator<Value> comparator) {
        //link was null - return null, indicating a miss
        List<Value> prefixList = new ArrayList<>();
        Set<Value> prefixSet = new HashSet<>();
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        for (int counter = 0; counter < key.length() && x != null; counter++){
            char c = key.charAt(counter);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x = x.links[c];
        }

        if (x == null){
            List<Value> emptyList = new ArrayList<>();
            return emptyList;
        }
        //we've reached the last node in the key,
        //return the node
        //now wherever there is a document stored i need to return that
        prefixList.addAll(getWithPrefixKeyLength(x, prefixSet, comparator));

        return prefixList;
    }

    

    private Set<Value> getWithPrefixKeyLength(Node x, Set<Value> prefixSet, Comparator<Value> comparator) {
        prefixSet.addAll(x.valueList);
        for (Node x1: x.links) {
            if (x1 != null) {
                getWithPrefixKeyLength(x1, prefixSet, comparator);
            }
        }
        return prefixSet;
    }

    /**
     * Delete the subtree rooted at the last character of the prefix.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAllWithPrefix(String prefix){
        if (prefix == null) {
            throw new IllegalArgumentException("null prefix");
        }
        if (prefix.length() == 0) {
            return new HashSet<Value>();
        }
        //String noCharacterText = prefix.replaceAll( "[^a-zA-Z0-9\\s]", "");
        prefix = prefix.toLowerCase();
        List<Value> valueList = getWithPrefix(this.root, prefix, null);
        Set<Value> valueSet = new HashSet<Value>(valueList);
        this.root = deleteAllWithPrefixPrivate(this.root, prefix, 0);
        return valueSet;
    }

    private Node deleteAllWithPrefixPrivate(Node x, String prefix, int d){
        if (x == null) return null;

        boolean endOfList = true;

        if(d<prefix.length()) endOfList = false;

        if (endOfList) x.valueList.clear(); 

        if (d < prefix.length()){ 
            char c = prefix.charAt(d);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x.links[c] = this.deleteAllWithPrefixPrivate(x.links[c], prefix, d + 1);
        }else{ //after prefix but not end of list
            for (Node x1: x.links) {
                if (x1 != null) x1 = this.deleteAllWithPrefixPrivate(x1, prefix, d + 1);
            }
        }
        //this node has a val – do nothing, return the node
        if (!x.valueList.isEmpty()) return x;
        //remove subtrie rooted at x if it is completely empty  
        for (int c = 0; c <36; c++){
            if (x.links[c] != null){
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }





/*
    private Node deleteAllPrefixPrivate(Node x, String keyPrefix, int d){
       List<Value> prefixList = new ArrayList<>();
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        for (int counter = 0; counter < keyPrefix.length() && x != null; counter++){
            char c = keyPrefix.charAt(counter);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x = x.links[c];
        }

        if (x == null){
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        //now wherever there is a document stored i need to return that
        deleteWithPrefixKeyLength(x, prefixList);
        return x;
    }

    private void deleteWithPrefixKeyLength(Node x, List<Value> prefixList) {
        prefixList.addAll(x.valueList);
        for (Node x1: x.links) {
            if (x1 != null) {
                if (!x1.valueList.isEmpty()) {
                    prefixList.addAll(x1.valueList);
                    deleteWithPrefixKeyLength(x1, prefixList);
                }
                x1.valueList.clear(); 
            }
        }
        x.valueList.clear(); //dont do anything to parents....
    }*/

    /**
     * Delete all values from the node of the given key (do not remove the values from other nodes in the Trie)
     * @param key
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAll(String key){
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (key.length() == 0){
            return new HashSet<Value>();
        }
        //String noCharacterText = key.replaceAll( "[^a-zA-Z0-9\\s]", "");
        key = key.toLowerCase();
        Set<Value> deletedValues = new HashSet<>();
        this.root = deleteAll(this.root, key, 0, deletedValues);
        return deletedValues;
    }

    private Node deleteAll(Node x, String key, int d, Set<Value> deletedValues){
        if (x == null) return null;
        //we're at the node to del - set the val to null
        if (d == key.length()){
            deletedValues.addAll(x.valueList);
            x.valueList.clear(); //or the emptySet
        }else{
        //continue down the trie to the target node
            char c = key.charAt(d);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x.links[c] = this.deleteAll(x.links[c], key, d + 1, deletedValues);
        }
        //this node has a val – do nothing, return the node
        if (!x.valueList.isEmpty()){ //or does not equal the emty list
            return x;
        }
        //remove subtrie rooted at x if it is completely empty  
        for (int c = 0; c <36; c++){
            if (x.links[c] != null){
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }

    /**
     * Remove the given value from the node of the given key (do not remove the value from other nodes in the Trie)
     * @param key
     * @param val
     * @return the value which was deleted. If the key did not contain the given value, return null.
     */
    public Value delete(String key, Value val){
        if (key == null || val == null) {
            throw new IllegalArgumentException("arguments are null"); //WHAT IF KEY IS STRING OF LENGTH 0??????????
        }
        if (key.length()==0) {
            return null;
        }
        //String noCharacterText = key.replaceAll( "[^a-zA-Z0-9\\s]", "");
        key = key.toLowerCase();
        Value deletedValue = getDeletedValue(this.root, key, val);
        boolean hasValue = false;
        deletePrivate(this.root, key, val, 0);
        return deletedValue;
    }


     private Value getDeletedValue(Node x, String key, Value val) {
        for (int counter = 0; counter < key.length() && x != null; counter++){
            char c = key.charAt(counter);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x = x.links[c];
        }
        if (x!=null){
            for (Object value : x.valueList) {
                if (val.equals((Value)value)) {
                    return (Value)value;
                }
            }
        }
        return null;
    }

    private Node deletePrivate(Node x, String key, Value val, int d){
        if (x == null){
            return null;
        }
        //we're at the node to del - set the val to null
        if (d == key.length()){
            x.valueList.remove(val);
        }else{
        //continue down the trie to the target node
            char c = key.charAt(d);
            if ((int) c < 65){
                c = (char)(c-48);
            }else{
                c = (char)(c-87);
            }
            x.links[c] = this.deletePrivate(x.links[c], key, val, d + 1);
        }
        //this node has a val – do nothing, return the node
        if (x.valueList != null){ 
            return x;
        }
        //remove subtrie rooted at x if it is completely empty  
        for (int c = 0; c <36; c++){
            if (x.links[c] != null){
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }
}