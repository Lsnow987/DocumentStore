package edu.yu.cs.com1320.project.impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import java.util.ArrayList;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import java.util.Comparator;

import edu.yu.cs.com1320.project.Trie;


public class TrieImplEdgeCaseTest {
    Trie<Integer> trie = new TrieImpl<>();
    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times it was the worst of times";
    String string3 = "It was a bright cold day in April and the clocks were striking thirteen";
    String string4 = "I am free no matter what rules surround me";

    @BeforeEach
    public void init() {
        for (String word : string1.split(" ")) {
            trie.put(word, string1.indexOf(word));
        }
        for (String word : string2.split(" ")) {
            trie.put(word, string2.indexOf(word));
        }
        for (String word : string3.split(" ")) {
            trie.put(word, string3.indexOf(word));
        }
        for (String word : string4.split(" ")) {
            trie.put(word, string4.indexOf(word));
        }
    }
    @Test
    public void testPut() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.put(null, 100);
        });

        trie.put("", 100);

        trie.put("the", null);

        assertEquals(trie.getAllSorted("the", Comparator.naturalOrder()).size(), 2);
    }
    @Test
    public void testGetAllSorted() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.getAllSorted("the", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            trie.getAllSorted(null, Comparator.naturalOrder());
        });

        assertEquals(trie.getAllSorted("", Comparator.naturalOrder()).size(), 0);
    }
    @Test
    public void testGetAllPrefixSorted() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.getAllWithPrefixSorted("the", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            trie.getAllWithPrefixSorted(null, Comparator.naturalOrder());
        });

        assertEquals(trie.getAllWithPrefixSorted("", Comparator.naturalOrder()).size(), 0);
    }
    @Test
    public void testDeleteWithPrefix() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.deleteAllWithPrefix(null);
        });

        assertTrue(trie.deleteAllWithPrefix("").size()==0);

        assertEquals(trie.getAllWithPrefixSorted("the", Comparator.naturalOrder()).size(), 2);
    }
    @Test
    public void testDeleteAll() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.deleteAll(null);
        });

        assertTrue(trie.deleteAll("").size()==0);

        assertEquals(trie.getAllSorted("the", Comparator.naturalOrder()).size(), 2);
    }
    @Test
    public void testDelete() {
        assertThrows(IllegalArgumentException.class, () -> {
            trie.delete("the", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            trie.delete(null, 100);
        });

        assertTrue(trie.delete("", 0)==null);
        //Deliberately don't use Integer factory
        @SuppressWarnings("deprecation")
        Integer largeInteger = new Integer(1000);
        @SuppressWarnings("deprecation")
        Integer largeInteger2 = new Integer(1000);
        trie.put("largeInteger", largeInteger);
        assertTrue(largeInteger != largeInteger2);
        assertTrue(trie.delete("largeInteger", largeInteger2)==largeInteger);
    }












    @Test
    public void complicatedTrieTest() {
        Trie trie = new TrieImpl<Integer>();
        trie.put("APPLE123", 1);
        trie.put("APPLE123", 2);
        trie.put("APPLE123", 3);
        trie.put("APPle87", 8);
        trie.put("aPpLe87", 7);
        List<Integer> appleList = trie.getAllSorted("apple123", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});
        appleList.addAll(trie.getAllSorted("apple87", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;}));
        assertEquals(5, appleList.size());
        List<Integer> testSet = List.copyOf(appleList);
        Set<Integer> deleteSet = trie.deleteAllWithPrefix("app");
        assertEquals(5, deleteSet.size());
        assertEquals(deleteSet.size(), testSet.size());
        if (!deleteSet.containsAll(testSet)){
            fail();
        }
        System.out.println("you passed complicatedTrieTest, congratulations!!!");
    }

    //variables to hold possible values for doc1
    private URI uri1;
    private String txt1;

    //variables to hold possible values for doc2
    private URI uri2;
    String txt2;

    private URI uri3;
    String txt3;
    @Test
    public void complicatedDocumentStoreTest() throws IOException, URISyntaxException {
        //init possible values for doc1
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        this.txt1 = "Apple Apple AppleProducts applesAreGood Apps APCalculus Apricots";

        //init possible values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        this.txt2 = "Apple Apple Apple Apple Apple";

        //init possible values for doc3
        this.uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
        this.txt3 = "APenguin APark APiccalo APants APain APossum";
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        List<Document> appleList = new ArrayList<>();
        appleList.addAll(store.searchByPrefix("ap"));
        assertEquals(3, appleList.size());
        List<URI> testSet = new ArrayList<>();
        for(Document doc :appleList){
            testSet.add(doc.getKey());
        }
        Set<URI> deleteSet = store.deleteAllWithPrefix("ap");
        assertEquals(3, deleteSet.size());
        assertEquals(deleteSet.size(), testSet.size());
        if (!deleteSet.containsAll(testSet)){
            fail();
        }
        System.out.println("you passed complicatedDocumentStoreTest, congratulations!!!");
    }
    @Test
    public void reallyComplicatedDocumentStoreUndoTest() throws IOException, URISyntaxException {
        //init possible values for doc1
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        this.txt1 = "Apple Apple AppleProducts applesAreGood Apps APCalculus Apricots";

        //init possible values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        this.txt2 = "Apple Apple Apple Apple Apple";

        //init possible values for doc3
        this.uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
        this.txt3 = "APenguin APark APiccalo APants APain APossum";
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        List<Document> appleList = new ArrayList<>();
        appleList.addAll(store.searchByPrefix("ap"));
        assertEquals(3, appleList.size());
        store.undo(this.uri2);
        appleList = store.searchByPrefix("ap");
        assertEquals(2, appleList.size());
        List<URI> testSet = new ArrayList<>();
        for(Document doc :appleList){
            testSet.add(doc.getKey());
        }
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        appleList = store.searchByPrefix("ap");
        assertEquals(3, appleList.size());
        Set<URI> deleteSet = store.deleteAllWithPrefix("ap");
        assertEquals(3, deleteSet.size());
        store.undo(this.uri1);
        store.undo(this.uri3);
        assertEquals(2, store.searchByPrefix("ap").size());
        deleteSet = store.deleteAllWithPrefix("ap");
        assertEquals(2, deleteSet.size());
        assertEquals(deleteSet.size(), testSet.size());
        if (!deleteSet.containsAll(testSet)){
            fail();
        }
        System.out.println("you passed reallyComplicatedDocumentStoreUndoTest, congratulations!!!");
    }







    @Test
    public void testOrder() throws IOException, URISyntaxException{
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        this.txt1 = "Apple Apple AppleProducts applesAreGood Apps APCalculus Apricots";

//init possible values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        this.txt2 = "Apple Apple Apple Apple Apple";

//init possible values for doc3
        this.uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
        this.txt3 = "APenguin APark APiccalo APants APain APossum";

        URI uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
        String txt4 = "ap APPLE apartment";
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(txt4.getBytes()), uri4, DocumentStore.DocumentFormat.TXT);
        List<Document> wordList = store.search("apple");
        List<Document> prefixList = store.searchByPrefix("ap");
        assertEquals(wordList.size(), 3);
        assertEquals((wordList.get(0)).getKey(), uri2);
        assertEquals(wordList.get(1).getKey(), uri1);
        assertEquals(wordList.get(2).getKey(), uri4);

        assertEquals(prefixList.size(), 4);
        assertEquals(prefixList.get(0).getKey(), uri1);
        assertEquals(prefixList.get(1).getKey(), uri3);
        assertEquals(prefixList.get(2).getKey(), uri2);

    }

}