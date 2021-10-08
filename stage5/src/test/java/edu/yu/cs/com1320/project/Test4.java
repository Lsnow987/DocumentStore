package edu.yu.cs.com1320.project;

import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.stage5.*;
import edu.yu.cs.com1320.project.stage5.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;







class Test4 {

    //variables to hold possible values for doc1
    private URI uri11;
    private String txt11;

    //variables to hold possible values for doc2
    private URI uri22;
    String txt22;

    private URI uri33;
    String txt33;

    @BeforeEach
    public void init() throws Exception {
        //init possible values for doc1
        this.uri11 = new URI("a");
        this.txt11 = "Apple Apple Pizza Fish Pie Pizza Apple";

        //init possible values for doc2
        this.uri22 = new URI("http://edu.yu.cs/com1320/project/uri22doc2");
        this.txt22 = "Pizza Pizza Pizza Pizza Pizza";

        //init possible values for doc3
        this.uri33 = new URI("http://edu.yu.cs/com1320/uri33project/uri33doc3");
        this.txt33 = "Penguin Park Piccalo Pants Pain Possum";
    }

    /*@Test
    public void UndoTest()throws Exception {

    }*/
    @Test
    public void DocumentPersistanceManagerTest() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        DocumentStore store = new DocumentStoreImpl(new File("C:\\Users\\ysnow\\Desktop\\Computerscience\\Snow_Lawrence_800397992\\DataStructures\\project"));
        store.setMaxDocumentCount(0);

        Document d = new DocumentImpl(this.uri11, this.txt11);
        Document d2 = new DocumentImpl(this.uri22, this.txt22);
        Document d3 = new DocumentImpl(this.uri33, this.txt33);

        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes1 = string1.getBytes();
        byte[] bytes2 = string2.getBytes();
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(bytes1);
        InputStream inputStream2 = new ByteArrayInputStream(bytes2);
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("stage5c/www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        store.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
        store.deleteDocument(uri1);
        assertNull(store.getDocument(uri1));
        DocumentPersistanceManager documentPersistanceManager = new DocumentPersistanceManager(null);
        documentPersistanceManager.serialize(this.uri11, d);
        Document document = documentPersistanceManager.deserialize(this.uri11);
        System.out.println(d.getDocumentTxt() + "          dddddddddd");
        System.out.println(document.getDocumentTxt() + "          ocumentocumentdddddddddd");
        System.out.println(d.getKey() + "          dddddddddd");
        System.out.println(document.getKey() + "          ocumentocumentdddddddddd");
        assertEquals(d, document);
        documentPersistanceManager.serialize(this.uri22, d2);
        Document document2 = documentPersistanceManager.deserialize(this.uri22);
        System.out.println(d2.getDocumentTxt() + "          dddddddddd");
        System.out.println(document2.getDocumentTxt() + "          ocumentocumentdddddddddd");
        System.out.println(d2.getKey() + "          dddddddddd");
        System.out.println(document2.getKey() + "          ocumentocumentdddddddddd");
        assertEquals(d2, document2);
    }

    /*@Test
    public void BTreeImplTest() {
        BTreeImpl<Integer, String> st = new BTreeImpl<>();
        st.put(1, "one");
        st.put(2, "two");
        st.put(3, "three");
        st.put(4, "four");
        st.put(5, "five");
        st.put(6, "six");
        st.put(7, "seven");
        st.put(8, "eight");
        st.put(9, "nine");
        st.put(10, "ten");
        st.put(11, "eleven");
        st.put(12, "twelve");
        st.put(13, "thirteen");
        st.put(14, "fourteen");
        st.put(15, "fifteen");
        st.put(16, "sixteen");
        st.put(17, "seventeen");
        st.put(18, "eighteen");
        st.put(19, "nineteen");
        st.put(20, "twenty");
        st.put(21, "twenty one");
        st.put(22, "twenty two");
        st.put(23, "twenty three");
        st.put(24, "twenty four");
        st.put(25, "twenty five");
        st.put(26, "twenty six");

        System.out.println("Size: " + st.size());
        System.out.println("Height: " + st.height);
        System.out.println("Key-value pairs, sorted by key:");
        ArrayList<BTreeImpl.Entry> entries = st.getOrderedEntries();
        for (BTreeImpl.Entry e : entries) {
            System.out.println("key = " + e.getKey() + "; value = " + e.getValue());
        }

        BTreeImpl.Entry min = st.getMinEntry();
        System.out.println("Minimum Entry: " + "key = " + min.getKey() + "; value = " + min.getValue());

        BTreeImpl.Entry max = st.getMaxEntry();
        System.out.println("Maximum Entry: " + "key = " + max.getKey() + "; value = " + max.getValue());

        st.put(1, null);
        min = st.getMinEntry();
        System.out.println("Minimum Entry after deleting 1: " + "key = " + min.getKey() + "; value = " + min.getValue());

        st.put(26, null);
        max = st.getMaxEntry();
        System.out.println("Maximum Entry after deleting 26: " + "key = " + max.getKey() + "; value = " + max.getValue());

        System.out.println("Key-value pairs, sorted by key:");
        entries = st.getOrderedEntries();
        for (BTreeImpl.Entry e : entries) {
            System.out.println("key = " + e.getKey() + "; value = " + e.getValue());
        }
    }*/


    @Test
    public void minHeapTest() {

        MinHeapImpl<Integer> minHeap = new MinHeapImpl<>();
        minHeap.insert(8);
        minHeap.insert(2);
        minHeap.insert(3);
        minHeap.insert(1);
        minHeap.insert(5);
        minHeap.insert(4);
        minHeap.insert(7);
        minHeap.insert(6);
        minHeap.insert(9);
        minHeap.insert(10);
        minHeap.insert(11);
        minHeap.insert(12);
        minHeap.insert(13);
        minHeap.insert(14);
        minHeap.insert(15);
        //minHeap.printOutArray();
        System.out.println("after print out array.................");
    /*System.out.println(minHeap.remove() + " removed 1");
    System.out.println(minHeap.remove() + " removed 2");
    System.out.println(minHeap.remove() + " removed 3");
      System.out.println(minHeap.remove() + " removed 1");
      System.out.println(minHeap.remove() + " removed 2");
      System.out.println(minHeap.remove() + " removed 3");
      System.out.println(minHeap.remove() + " removed 1");
      System.out.println(minHeap.remove() + " removed 2");
      System.out.println(minHeap.remove() + " removed 3");
      System.out.println(minHeap.remove() + " removed 1");
      System.out.println(minHeap.remove() + " removed 2");
      System.out.println(minHeap.remove() + " removed 3");*/
        minHeap.reHeapify(1);
        minHeap.reHeapify(2);
        minHeap.reHeapify(3);
        minHeap.reHeapify(4);
        minHeap.reHeapify(5);
        minHeap.reHeapify(6);
        minHeap.reHeapify(7);
        minHeap.reHeapify(8);
        minHeap.reHeapify(9);
        minHeap.reHeapify(10);
        minHeap.reHeapify(11);
        minHeap.reHeapify(12);
        minHeap.reHeapify(13);
        minHeap.reHeapify(14);
        minHeap.reHeapify(15);
        //minHeap.printOutArray();
    }

    @Test
    public void maxDocTestUndoStack() throws Exception {
        DocumentStore documentStore = new DocumentStoreImpl();
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream5 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream6 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream7 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream8 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream9 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream10 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream11 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream12 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream13 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream14 = new ByteArrayInputStream(string1.getBytes());
        URI uri1 = URI.create("http://www.wrinkleintime.com");
        URI uri2 = URI.create("http://www.taleoftwocities.com");
        URI uri3 = URI.create("http://www.1984.com");
        URI uri4 = URI.create("http://www.themoonisaharshmistress.com");
        documentStore.setMaxDocumentCount(1);
        try {
            documentStore.putDocument(inputStream6, uri1, DocumentStore.DocumentFormat.TXT);
            //System.out.println(documentStore.getDocument(uri1) + "       222222uri1111111111111111111111111");
            documentStore.putDocument(inputStream7, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream8, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream9, uri4, DocumentStore.DocumentFormat.TXT);
            System.out.println("before problematic get");
            assertNotNull(documentStore.getDocument(uri1));
        } catch (java.io.IOException e) {
            fail();
        }
        System.out.println("before problematic UNDOUNDO");
        documentStore.undo(); //btree impl 196 commented out!!!!!!!!
        //assertNotNull(documentStore.getDocument(uri1));
        //assertNull(documentStore.getDocument(uri4));
    }


    @Test
    public void maxDocTest() throws Exception {
        DocumentStore documentStore = new DocumentStoreImpl();
        //documentStore.setMaxDocumentCount(3);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream5 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream6 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream7 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream8 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream9 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream10 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream11 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream12 = new ByteArrayInputStream(string4.getBytes());
        InputStream inputStream13 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream14 = new ByteArrayInputStream(string1.getBytes());
        URI uri1 = URI.create("http://www.wrinkleintime.com");
        URI uri2 = URI.create("http://www.taleoftwocities.com");
        URI uri3 = URI.create("http://www.1984.com");
        URI uri4 = URI.create("http://www.themoonisaharshmistress.com");

        //try {
        //documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
        //documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
        //documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
        //documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
// } catch (java.io.IOException e) {
        //fail();
        //}
        //Document d1 = documentStore.getDocument(uri1);
        //System.out.println(documentStore.getDocument(uri1) + "       uri1111111111111111111111111");
        //assertNull(documentStore.getDocument(uri1));
        //documentStore.setMaxDocumentCount(2);
        //assertNull(documentStore.getDocument(uri2));
// documentStore.setMaxDocumentCount(1);
        //assertNull(documentStore.getDocument(uri3));
        //try {
        //documentStore.putDocument(inputStream5, uri1, DocumentStore.DocumentFormat.TXT);
        //} catch (java.io.IOException e) {
        //fail();
        //}
        //assertNull(documentStore.getDocument(uri4));
        //assertNotNull(documentStore.getDocument(uri1));
        //documentStore.setMaxDocumentCount(4);
        try {
            documentStore.putDocument(inputStream6, uri1, DocumentStore.DocumentFormat.TXT);
            //System.out.println(documentStore.getDocument(uri1) + "       222222uri1111111111111111111111111");
            documentStore.putDocument(inputStream7, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream8, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream9, uri4, DocumentStore.DocumentFormat.TXT);
        } catch (java.io.IOException e) {
            fail();
        }
        //System.out.println(d1.getLastUseTime() + "dddddddd111111111111TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
        //Document d = documentStore.getDocument(uri1);
        //System.out.println(d.getLastUseTime() + "ddddddddTIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
        //documentStore.undo(uri1);
        //System.out.println(d1 + "      " + d1.getLastUseTime() + "ddd1111111111111TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE   " + documentStore.getDocument(uri1).getLastUseTime());
        //assertEquals(d1,documentStore.getDocument(uri1));

        //assertNotNull(documentStore.getDocument(uri1));
        //assertNotNull(documentStore.getDocument(uri2));
        //assertNotNull(documentStore.getDocument(uri3));
        //assertNotNull(documentStore.getDocument(uri4));
        //System.out.println(documentStore.getDocument(uri3) + "       uri33333333333333333333333333333333333333");
        documentStore.deleteDocument(uri3);
        System.out.println("after delete document 3AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        documentStore.setMaxDocumentCount(3); ///////////////
        System.out.println("after setmax document(3)AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        assertNotNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(uri2));
        assertNull(documentStore.getDocument(uri3));
        assertNotNull(documentStore.getDocument(uri4));
        System.out.println("before undo uri3");
        documentStore.undo(uri3);
        System.out.println("after undo uri3");
        assertNotNull(documentStore.getDocument(uri3));
        assertNotNull(documentStore.getDocument(uri2));
        assertNotNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(uri4));
        documentStore.setMaxDocumentCount(4);
        try {
            documentStore.putDocument(inputStream10, uri1, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream14, uri1, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream11, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream12, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream13, uri4, DocumentStore.DocumentFormat.TXT);

        } catch (java.io.IOException e) {
            fail();
        }
        assertNotNull(documentStore.getDocument(uri3));
        assertNotNull(documentStore.getDocument(uri2));
        assertNotNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(uri4));
        System.out.println("before delete uri 3333333");
        documentStore.deleteDocument(uri3);
        System.out.println("before delete uri 222222");
        documentStore.deleteDocument(uri2);
        documentStore.deleteDocument(uri1);
        assertNull(documentStore.getDocument(uri1));


        documentStore.putDocument(new ByteArrayInputStream(this.txt11.getBytes()), this.uri11, DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(new ByteArrayInputStream(this.txt22.getBytes()), this.uri22, DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(new ByteArrayInputStream(this.txt33.getBytes()), this.uri33, DocumentStore.DocumentFormat.TXT);
        assertNotNull(documentStore.getDocument(this.uri33));
        assertEquals(2, documentStore.search("pizza").size());
        //System.out.println(store.getDocument(this.uri3) + "          URIURIURIURIURIURI33333333333333");
        //System.out.println(store.getDocument(this.uri1) + "          URIURIURIURIURIURI111111111111111");
        System.out.println("before delete all pizza");
        System.out.println(documentStore.getDocument(uri4) + "pizza");
        assertNotNull(documentStore.getDocument(uri4));
        assertNotNull(documentStore.getDocument(this.uri33));
        documentStore.deleteAll("PiZZa");
        System.out.println("after delete all pizza");
        System.out.println(documentStore.getDocument(uri4) + "pizza");
        assertNotNull(documentStore.getDocument(uri4));


        System.out.println("after delete all pizza111");
        assertEquals(0, documentStore.search("pizza").size());
        assertNull(documentStore.getDocument(this.uri11));
        documentStore.setMaxDocumentCount(3);
        System.out.println("at undooooooooooooo");
        System.out.println("before before beforeafter documentStore.undo();");
        documentStore.undo();
        System.out.println("after documentStore.undo();");
        assertNull(documentStore.getDocument(uri3));
        assertNull(documentStore.getDocument(uri2));
        assertNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(this.uri33));
        assertNotNull(documentStore.getDocument(uri4));

        System.out.println(documentStore.getDocument(this.uri22).getLastUseTime() + "TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
        assertNotNull(documentStore.getDocument(this.uri22));
        assertNotNull(documentStore.getDocument(this.uri11));

        System.out.println(documentStore.getDocument(this.uri22).getLastUseTime() + "TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
        //documentStore.undo(uri2);
        //documentStore.undo(uri3);
        //documentStore.undo(uri1);
            /*assertNotNull(documentStore.getDocument(uri3));
            assertNotNull(documentStore.getDocument(uri2));
            assertNotNull(documentStore.getDocument(uri1));
            assertNull(documentStore.getDocument(uri4));
            assertNull(documentStore.getDocument(this.uri22));
            assertNull(documentStore.getDocument(this.uri11));
            assertNull(documentStore.getDocument(this.uri33));



            documentStore.putDocument(new ByteArrayInputStream(this.txt22.getBytes()),this.uri22, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(new ByteArrayInputStream(this.txt11.getBytes()),this.uri11, DocumentStore.DocumentFormat.TXT);
            assertEquals(2, documentStore.search("pizza").size());
            assertNotNull(documentStore.getDocument(this.uri11));
            assertNotNull(documentStore.getDocument(this.uri22));
            assertNotNull(documentStore.getDocument(this.uri33));
            documentStore.deleteAllWithPrefix("p");
            documentStore.setMaxDocumentCount(2);
            assertNull(documentStore.getDocument(this.uri11));assertNull(documentStore.getDocument(this.uri22));assertNull(documentStore.getDocument(this.uri33));
            documentStore.undo();
            assertNotNull(documentStore.getDocument(this.uri11));assertNotNull(documentStore.getDocument(this.uri22));assertNull(documentStore.getDocument(this.uri33));
*/
    }

    @Test
    public void maxByteTest() throws Exception {
        DocumentStore documentStore = new DocumentStoreImpl();
        //System.out.println("new docStroe");
        String string11 = "Itttttt was a dark and stormy night";
        URI uri1 = URI.create("www.wrinkle1111intime.com");
        InputStream inputStream11 = new ByteArrayInputStream(string11.getBytes());
        String string21 = "Ittttttttt was the best of times, it was the worst of times";
        String string3111 = "Itttttttt was a bright cold day in April, and the clocks were striking thirteen";
        String string4111 = "Ittttttttt am free, no matter what rules surround me.";


        InputStream inputStream22 = new ByteArrayInputStream(string21.getBytes());
        InputStream inputStream33 = new ByteArrayInputStream(string3111.getBytes());
        InputStream inputStream44 = new ByteArrayInputStream(string4111.getBytes());
        InputStream inputStream5 = new ByteArrayInputStream(string11.getBytes());
        InputStream inputStream6 = new ByteArrayInputStream(string21.getBytes());
        InputStream inputStream7 = new ByteArrayInputStream(string3111.getBytes());
        InputStream inputStream8 = new ByteArrayInputStream(string4111.getBytes());
        //System.out.println(new String(inputStream11.readAllBytes()) + "hellooooooooooooo");

        URI uri2 = URI.create("www.taleof11111twocities.com");
        URI uri3 = URI.create("www.1911111184.com");
        URI uri4 = URI.create("www.themo111111onisaharshmistress.com");
        documentStore.setMaxDocumentBytes(string3111.getBytes().length + string4111.getBytes().length);

        try {
            //System.out.println("before put in test");
            documentStore.putDocument(inputStream11, uri1, DocumentStore.DocumentFormat.TXT);
            System.out.println(documentStore.getDocument(uri1));
            documentStore.putDocument(inputStream22, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream33, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream44, uri4, DocumentStore.DocumentFormat.TXT);
        } catch (java.io.IOException e) {
            fail();
        }
        assertNotNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(uri2));
        documentStore.setMaxDocumentBytes(0);
        assertNotNull(documentStore.getDocument(uri3));
        assertNotNull(documentStore.getDocument(uri4));
        documentStore.setMaxDocumentBytes(Integer.MAX_VALUE);
        try {
            documentStore.putDocument(inputStream5, uri1, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream6, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream7, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream8, uri4, DocumentStore.DocumentFormat.TXT);
        } catch (java.io.IOException e) {
            fail();
        }
        assertNotNull(documentStore.getDocument(uri1));
        assertNotNull(documentStore.getDocument(uri2));
        assertNotNull(documentStore.getDocument(uri3));
        assertNotNull(documentStore.getDocument(uri4));

    }

    @Test
    public void serializeWithBinaryDocTest() throws URISyntaxException, IOException {
    DocumentStore documentStore = new DocumentStoreImpl();
    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times, it was the worst of times";
    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
    String string4 = "I am free, no matter what rules surround me.";
    byte[] bytes1 = string1.getBytes();
    byte[] bytes2 = string2.getBytes();
    byte[] bytes3 = string3.getBytes();
    byte[] bytes4 = string4.getBytes();
    InputStream inputStream1 = new ByteArrayInputStream(bytes1);
    InputStream inputStream2 = new ByteArrayInputStream(bytes2);
    InputStream inputStream3 = new ByteArrayInputStream(bytes3);
    InputStream inputStream4 = new ByteArrayInputStream(bytes4);
    InputStream inputStream5 = new ByteArrayInputStream(bytes4);
    InputStream inputStream6 = new ByteArrayInputStream(bytes4);
    URI uri1 = URI.create("www.wrinkleintime.com");
    URI uri2 = URI.create("www.taleoftwocities.com");
    URI uri3 = URI.create("www.1984.com");
    URI uri4 = URI.create("www.themoonisaharshmistress.com");
	  try
    {
        documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
        documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
        documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
        documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
    } catch(
    java.io.IOException e)

    {
        fail();
    }
    documentStore.setMaxDocumentCount(1);
    Document document1 = (Document) new DocumentImpl(uri1, bytes1);
    Document document2 = (Document) new DocumentImpl(uri2, bytes2);
    Document document3 = (Document) new DocumentImpl(uri3, bytes3);
    Document document4 = (Document) new DocumentImpl(uri4, bytes4);
    int test1 = documentStore.getDocument(uri1).hashCode();
    int test2 = documentStore.getDocument(uri2).hashCode();
    int test3 = documentStore.getDocument(uri3).hashCode();
    int test4 = documentStore.getDocument(uri4).hashCode();
    assertTrue(Arrays.equals(document1.getDocumentBinaryData(), documentStore.getDocument(uri1).getDocumentBinaryData()));
    assertEquals(document1.getDocumentBinaryData().length, documentStore.getDocument(uri1).getDocumentBinaryData().length);
    documentStore.putDocument(inputStream5, uri1, DocumentStore.DocumentFormat.BINARY);
    documentStore.putDocument(inputStream6, uri2, DocumentStore.DocumentFormat.BINARY);
    //System.out.println(document1.getWordMap() + "   wordmap");
    assertEquals(document1.hashCode(),test1);

    assertEquals(document2.hashCode(),test2);

    assertEquals(document3.hashCode(),test3);

    assertEquals(document4.hashCode(),test4);
}








@Test
 public void wordCountAndGetWordsTest() throws URISyntaxException {
  DocumentImpl txtDoc = new DocumentImpl(new URI("placeholder"), "The!se ARE? sOme W@o%$rds with^ s**ymbols (m)ixed [in]. Hope this test test passes!");
  assertEquals(0, txtDoc.wordCount("bundle"));
  assertEquals(1, txtDoc.wordCount("these"));
  assertEquals(1, txtDoc.wordCount("WORDS"));
  assertEquals(1, txtDoc.wordCount("S-Y-M-B-O-??-LS"));
  assertEquals(1, txtDoc.wordCount("p@A$$sse$s"));
  assertEquals(2, txtDoc.wordCount("tEst"));
  Set<String> words = txtDoc.getWords();
  assertEquals(12, words.size());
  assertTrue(words.contains("some"));

  DocumentImpl binaryDoc = new DocumentImpl(new URI("0110"), new byte[] {0,1,1,0});
  assertEquals(0, binaryDoc.wordCount("anythingYouPutHereShouldBeZero"));
  Set<String> words2 = binaryDoc.getWords();
  assertEquals(0, words2.size());
 }
}