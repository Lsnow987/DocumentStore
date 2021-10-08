import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


//import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Set;

import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;

import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.*;
import edu.yu.cs.com1320.project.stage4.impl.*;

import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;







class Test4{

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
            this.uri11 = new URI("http://edu.yu.cs/com1320/project/doc1");
            this.txt11 = "Apple Apple Pizza Fish Pie Pizza Apple";
    
            //init possible values for doc2
            this.uri22 = new URI("http://edu.yu.cs/com1320/project/doc2");
            this.txt22 = "Pizza Pizza Pizza Pizza Pizza";

            //init possible values for doc3
            this.uri33 = new URI("http://edu.yu.cs/com1320/project/doc3");
            this.txt33 = "Penguin Park Piccalo Pants Pain Possum";
        }



  @Test
  public void minHeapTest()throws Exception{

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
public void maxDocTest()throws Exception{
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
 URI uri1 =  URI.create("www.wrinkleintime.com");
 URI uri2 =  URI.create("www.taleoftwocities.com");
 URI uri3 =  URI.create("www.1984.com");
 URI uri4 =  URI.create("www.themoonisaharshmistress.com");
 try {
  documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
  //documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
  //documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
  //documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
 } catch (java.io.IOException e) {
  fail();
 }
 Document d1 = documentStore.getDocument(uri1);
 System.out.println(documentStore.getDocument(uri1) + "       uri1111111111111111111111111");
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
  System.out.println(documentStore.getDocument(uri1) + "       222222uri1111111111111111111111111");
  documentStore.putDocument(inputStream7, uri2, DocumentStore.DocumentFormat.TXT);
  documentStore.putDocument(inputStream8, uri3, DocumentStore.DocumentFormat.TXT);
  documentStore.putDocument(inputStream9, uri4, DocumentStore.DocumentFormat.TXT);
 } catch (java.io.IOException e) {
  fail();
 }
 System.out.println(d1.getLastUseTime() + "dddddddd111111111111TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
 Document d = documentStore.getDocument(uri1);
 System.out.println(d.getLastUseTime() + "ddddddddTIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
 documentStore.undo(uri1);
 System.out.println(d1 + "      " + d1.getLastUseTime() + "ddd1111111111111TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE   " + documentStore.getDocument(uri1).getLastUseTime());
 assertEquals(d1,documentStore.getDocument(uri1));

 assertNotNull(documentStore.getDocument(uri1));
 assertNotNull(documentStore.getDocument(uri2));
 assertNotNull(documentStore.getDocument(uri3));
 assertNotNull(documentStore.getDocument(uri4));
 System.out.println(documentStore.getDocument(uri3) + "       uri33333333333333333333333333333333333333");
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
 assertNotNull(documentStore.getDocument(uri3));
 assertNotNull(documentStore.getDocument(uri2));
 assertNull(documentStore.getDocument(uri1));
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
 documentStore.deleteDocument(uri3);
 documentStore.deleteDocument(uri2);
 documentStore.deleteDocument(uri1);







  
            documentStore.putDocument(new ByteArrayInputStream(this.txt11.getBytes()),this.uri11, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(new ByteArrayInputStream(this.txt22.getBytes()),this.uri22, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(new ByteArrayInputStream(this.txt33.getBytes()),this.uri33, DocumentStore.DocumentFormat.TXT);
            assertEquals(2, documentStore.search("pizza").size());
            //System.out.println(store.getDocument(this.uri3) + "          URIURIURIURIURIURI33333333333333");
            //System.out.println(store.getDocument(this.uri1) + "          URIURIURIURIURIURI111111111111111");
            documentStore.deleteAll("PiZZa");
            assertEquals(0, documentStore.search("pizza").size());
            assertNull(documentStore.getDocument(this.uri11));
            documentStore.setMaxDocumentCount(3);
            documentStore.undo();
            assertNull(documentStore.getDocument(uri3));
            assertNull(documentStore.getDocument(uri2));
            assertNull(documentStore.getDocument(uri1));
            assertNull(documentStore.getDocument(uri4));
            System.out.println(documentStore.getDocument(this.uri22).getLastUseTime() + "TIIIIIIIIIIIIIIIIIMMMMMEEEEEEEEEEE");
            assertNotNull(documentStore.getDocument(this.uri22));
            assertNotNull(documentStore.getDocument(this.uri11));
            assertNotNull(documentStore.getDocument(this.uri33));
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
 public void maxByteTest()throws Exception{
  DocumentStore documentStore = new DocumentStoreImpl();
  String string11 = "Itttttt was a dark and stormy night";
  String string21 = "Ittttttttt was the best of times, it was the worst of times";
  String string3111 = "Itttttttt was a bright cold day in April, and the clocks were striking thirteen";
  String string4111 = "Ittttttttt am free, no matter what rules surround me.";


  InputStream inputStream11 = new ByteArrayInputStream(string11.getBytes());
  InputStream inputStream22 = new ByteArrayInputStream(string21.getBytes());
  InputStream inputStream33 = new ByteArrayInputStream(string3111.getBytes());
  InputStream inputStream44 = new ByteArrayInputStream(string4111.getBytes());
  InputStream inputStream5 = new ByteArrayInputStream(string11.getBytes());
  InputStream inputStream6 = new ByteArrayInputStream(string21.getBytes());
  InputStream inputStream7 = new ByteArrayInputStream(string3111.getBytes());
  InputStream inputStream8 = new ByteArrayInputStream(string4111.getBytes());
  System.out.println(new String(inputStream11.readAllBytes()) + "hellooooooooooooo");
  URI uri1 =  URI.create("www.wrinkle1111intime.com");
  URI uri2 =  URI.create("www.taleof11111twocities.com");
  URI uri3 =  URI.create("www.1911111184.com");
  URI uri4 =  URI.create("www.themo111111onisaharshmistress.com");
  documentStore.setMaxDocumentBytes(string3111.getBytes().length + string4111.getBytes().length);

  try {
   documentStore.putDocument(inputStream11, uri1, DocumentStore.DocumentFormat.TXT);
   documentStore.putDocument(inputStream22, uri2, DocumentStore.DocumentFormat.TXT);
   documentStore.putDocument(inputStream33, uri3, DocumentStore.DocumentFormat.TXT);
   documentStore.putDocument(inputStream44, uri4, DocumentStore.DocumentFormat.TXT);
  } catch (java.io.IOException e) {
   fail();
  }
  assertNull(documentStore.getDocument(uri1));
  assertNull(documentStore.getDocument(uri2));
  documentStore.setMaxDocumentBytes(0);
  assertNull(documentStore.getDocument(uri3));
  assertNull(documentStore.getDocument(uri4));
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