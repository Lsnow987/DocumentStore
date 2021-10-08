
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
import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.stage2.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;

import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.stage2.impl.*;


class Test2 {
	 URI[] uriArray = new URI[21];
	 Document[] docArray = new Document[21];
	 String[] stringArray = {"The blue parrot drove by the hitchhiking mongoose.",
	  "She thought there'd be sufficient time if she hid her watch.",
	  "Choosing to do nothing is still a choice, after all.",
	  "He found the chocolate covered roaches quite tasty.",
	  "The efficiency we have at removing trash has made creating trash more acceptable.",
	  "Peanuts don't grow on trees, but cashews do.",
	  "A song can make or ruin a person’s day if they let it get to them.",
	  "You bite up because of your lower jaw.",
	  "He realized there had been several deaths on this road, but his concern rose when he saw the exact number.",
	  "So long and thanks for the fish.",
	  "Three years later, the coffin was still full of Jello.",
	  "Weather is not trivial - it's especially important when you're standing in it.",
	  "He walked into the basement with the horror movie from the night before playing in his head.",
	  "He wondered if it could be called a beach if there was no sand.",
	  "Jeanne wished she has chosen the red button.",
	  "It's much more difficult to play tennis with a bowling ball than it is to bowl with a tennis ball.",
	  "Pat ordered a ghost pepper pie.",
	  "Everyone says they love nature until they realize how dangerous she can be.",
	  "The memory we used to share is no longer coherent.",
	  "My harvest will come Tiny valorous straw Among the millions Facing to the sun",
	  "A dreamy-eyed child staring into night On a journey to storyteller's mind Whispers a wish speaks with the stars the words are silent in him"};
	 @Test
	 void testPutDocumentStoreAsText() {
	  DocumentStore documentStore = new DocumentStoreImpl();
	  String string1 = "It was a dark and stormy night";
	  String string2 = "It was the best of times, it was the worst of times";
	  String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
	  String string4 = "I am free, no matter what rules surround me.";
	  InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
	  InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
	  InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
	  InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
	  URI uri1 =  URI.create("www.wrinkleintime.com");
	  URI uri2 =  URI.create("www.taleoftwocities.com");
	  URI uri3 =  URI.create("www.1984.com");
	  URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	  try {
	   documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
	   documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
	   documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
	   documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
	  } catch (java.io.IOException e) {
	   fail();
	  }
	  Document document1 = new DocumentImpl(uri1, string1);
	  Document document2 = new DocumentImpl(uri2, string2);
	  Document document3 = new DocumentImpl(uri3, string3);
	  Document document4 = new DocumentImpl(uri4, string4);
	  int test1 = documentStore.getDocument(uri1).hashCode();
	  int test2 = documentStore.getDocument(uri2).hashCode();
	  int test3 = documentStore.getDocument(uri3).hashCode();
	  int test4 = documentStore.getDocument(uri4).hashCode();
	  assertEquals(document1.hashCode(),test1);
	  assertEquals(document2.hashCode(),test2);
	  assertEquals(document3.hashCode(),test3);
	  assertEquals(document4.hashCode(),test4);
	 }
	 @Test
	 void testPutDocumentStoreAsBinary() {
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
	  URI uri1 =  URI.create("www.wrinkleintime.com");
	  URI uri2 =  URI.create("www.taleoftwocities.com");
	  URI uri3 =  URI.create("www.1984.com");
	  URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	  try {
	   documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
	   documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
	   documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
	   documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
	  } catch (java.io.IOException e) {
	   fail();
	  }
	  Document document1 = new DocumentImpl(uri1, bytes1);
	  Document document2 = new DocumentImpl(uri2, bytes2);
	  Document document3 = new DocumentImpl(uri3, bytes3);
	  Document document4 = new DocumentImpl(uri4, bytes4);
	  int test1 = documentStore.getDocument(uri1).hashCode();
	  int test2 = documentStore.getDocument(uri2).hashCode();
	  int test3 = documentStore.getDocument(uri3).hashCode();
	  int test4 = documentStore.getDocument(uri4).hashCode();
	  assertEquals(document1.hashCode(),test1);
	  assertEquals(document2.hashCode(),test2);
	  assertEquals(document3.hashCode(),test3);
	  assertEquals(document4.hashCode(),test4);
	 }
	 @Test
	 void testGetAndPutReturnValues() {
	  for (int i = 0; i < 7; i++) {
	   uriArray[i] = URI.create("www.google"+i+".com");
	  }
	  //first 7 docs in array are txt, next are bit, last are txt
	  for (int i = 0; i < 7; i++) {
	   docArray[i] = new DocumentImpl(uriArray[i], stringArray[i]);
	  }
	  for (int i = 0; i < 7; i++) {
	   docArray[i+7] = new DocumentImpl(uriArray[i], stringArray[i+7].getBytes());
	  }
	  for (int i = 0; i < 7; i++) {
	   docArray[i+14] = new DocumentImpl(uriArray[i], stringArray[i+14]);
	  }
	 DocumentStore documentStore = new DocumentStoreImpl();
	 try {
	  int testa1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[0].getBytes()), uriArray[0], DocumentStore.DocumentFormat.TXT);
	  int testa2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[1].getBytes()), uriArray[1], DocumentStore.DocumentFormat.TXT);
	  int testa3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[2].getBytes()), uriArray[2], DocumentStore.DocumentFormat.TXT);
	  int testa4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[3].getBytes()), uriArray[3], DocumentStore.DocumentFormat.TXT);
	  int testa5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[4].getBytes()), uriArray[4], DocumentStore.DocumentFormat.TXT);
	  int testa6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[5].getBytes()), uriArray[5], DocumentStore.DocumentFormat.TXT);
	  int testa7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[6].getBytes()), uriArray[6], DocumentStore.DocumentFormat.TXT);
	  assertEquals(testa1, 0);
	  assertEquals(testa2, 0);
	  assertEquals(testa3, 0);
	  assertEquals(testa4, 0);
	  assertEquals(testa5, 0);
	  assertEquals(testa6, 0);
	  assertEquals(testa7, 0);
	 } catch (java.io.IOException e) {
	  fail();
	 }
	 assertEquals(docArray[0], documentStore.getDocument(uriArray[0]));
	 assertEquals(docArray[1], documentStore.getDocument(uriArray[1]));
	 assertEquals(docArray[2], documentStore.getDocument(uriArray[2]));
	 assertEquals(docArray[3], documentStore.getDocument(uriArray[3]));
	 assertEquals(docArray[4], documentStore.getDocument(uriArray[4]));
	 assertEquals(docArray[5], documentStore.getDocument(uriArray[5]));
	 assertEquals(docArray[6], documentStore.getDocument(uriArray[6]));
	 
	 try {
	  int testb1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[7].getBytes()), uriArray[0], DocumentStore.DocumentFormat.BINARY);
	  int testb2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[8].getBytes()), uriArray[1], DocumentStore.DocumentFormat.BINARY);
	  int testb3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[9].getBytes()), uriArray[2], DocumentStore.DocumentFormat.BINARY);
	  int testb4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[10].getBytes()), uriArray[3], DocumentStore.DocumentFormat.BINARY);
	  int testb5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[11].getBytes()), uriArray[4], DocumentStore.DocumentFormat.BINARY);
	  int testb6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[12].getBytes()), uriArray[5], DocumentStore.DocumentFormat.BINARY);
	  int testb7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[13].getBytes()), uriArray[6], DocumentStore.DocumentFormat.BINARY);
	  assertEquals(testb1, docArray[0].hashCode());
	  assertEquals(testb2, docArray[1].hashCode());
	  assertEquals(testb3, docArray[2].hashCode());
	  assertEquals(testb4, docArray[3].hashCode());
	  assertEquals(testb5, docArray[4].hashCode());
	  assertEquals(testb6, docArray[5].hashCode());
	  assertEquals(testb7, docArray[6].hashCode());
	 } catch (java.io.IOException e) {
	  fail();
	 }

	 assertEquals(docArray[7], documentStore.getDocument(uriArray[0]));
	 assertEquals(docArray[8], documentStore.getDocument(uriArray[1]));
	 assertEquals(docArray[9], documentStore.getDocument(uriArray[2]));
	 assertEquals(docArray[10], documentStore.getDocument(uriArray[3]));
	 assertEquals(docArray[11], documentStore.getDocument(uriArray[4]));
	 assertEquals(docArray[12], documentStore.getDocument(uriArray[5]));
	 assertEquals(docArray[13], documentStore.getDocument(uriArray[6]));
	 
	 try {
	  int testc1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[14].getBytes()), uriArray[0], DocumentStore.DocumentFormat.TXT);
	  int testc2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[15].getBytes()), uriArray[1], DocumentStore.DocumentFormat.TXT);
	  int testc3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[16].getBytes()), uriArray[2], DocumentStore.DocumentFormat.TXT);
	  int testc4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[17].getBytes()), uriArray[3], DocumentStore.DocumentFormat.TXT);
	  int testc5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[18].getBytes()), uriArray[4], DocumentStore.DocumentFormat.TXT);
	  int testc6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[19].getBytes()), uriArray[5], DocumentStore.DocumentFormat.TXT);
	  int testc7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[20].getBytes()), uriArray[6], DocumentStore.DocumentFormat.TXT);
	  assertEquals(testc1, docArray[7].hashCode());
	  assertEquals(testc2, docArray[8].hashCode());
	  assertEquals(testc3, docArray[9].hashCode());
	  assertEquals(testc4, docArray[10].hashCode());
	  assertEquals(testc5, docArray[11].hashCode());
	  assertEquals(testc6, docArray[12].hashCode());
	  assertEquals(testc7, docArray[13].hashCode());
	 } catch (java.io.IOException e) {
	  fail();
	 }

	 assertEquals(docArray[14], documentStore.getDocument(uriArray[0]));
	 assertEquals(docArray[15], documentStore.getDocument(uriArray[1]));
	 assertEquals(docArray[16], documentStore.getDocument(uriArray[2]));
	 assertEquals(docArray[17], documentStore.getDocument(uriArray[3]));
	 assertEquals(docArray[18], documentStore.getDocument(uriArray[4]));
	 assertEquals(docArray[19], documentStore.getDocument(uriArray[5]));
	 assertEquals(docArray[20], documentStore.getDocument(uriArray[6]));
	}
	 @Test 
	 void testDelete() {
	   DocumentStore documentStore = new DocumentStoreImpl();
	  for (int i = 0; i < 21; i++) {
	   uriArray[i] = URI.create("www.google"+i+".com");
	  }
	  for (int i = 0; i < 21; i++) {
	   try {
	    documentStore.putDocument(new ByteArrayInputStream(stringArray[i].getBytes()), uriArray[i], DocumentStore.DocumentFormat.BINARY);
	   } catch (java.io.IOException e) {
	    fail();
	   }
	  }

	  boolean test1a = documentStore.deleteDocument(uriArray[16]);
	  boolean test1b = documentStore.deleteDocument(uriArray[16]);
	  boolean test1c = documentStore.deleteDocument(URI.create("www.notinstore.com"));
	  
	  assertEquals(test1a, true);
	  assertEquals(test1b, false);
	  assertEquals(test1c, false);
	  
	  boolean test2a = documentStore.deleteDocument(uriArray[8]);
	  boolean test2b = documentStore.deleteDocument(uriArray[8]);
	  boolean test2c = documentStore.deleteDocument(URI.create("www.obviouslyfake.com"));
	  
	  assertEquals(test2a, true);
	  assertEquals(test2b, false);
	  assertEquals(test2c, false);
	  
	  boolean test3a = documentStore.deleteDocument(uriArray[2]);
	  boolean test3b = documentStore.deleteDocument(uriArray[2]);
	  boolean test3c = documentStore.deleteDocument(URI.create("www.notinstore.net"));
	  
	  assertEquals(test3a, true);
	  assertEquals(test3b, false);
	  assertEquals(test3c, false);
	 }
	 @Test
	 void testThrowException() {
	  String string = "Not empty!";
	  String nullString = null;
	  byte[] bytes = string.getBytes();
	  byte[] nullBytes = null;
	  URI uriNotEmpty = URI.create("www.notempty.com");
	  URI uriEmpty = URI.create("");
	  assertThrows(IllegalArgumentException.class,
	             ()->{
	              new DocumentImpl(uriNotEmpty, nullBytes);
	             });
	  assertThrows(IllegalArgumentException.class,
	             ()->{
	              new DocumentImpl(uriNotEmpty, nullString);
	             });
	  assertThrows(IllegalArgumentException.class,
	             ()->{
	              new DocumentImpl(null, bytes);
	             });
	  assertThrows(IllegalArgumentException.class,
	             ()->{
	              new DocumentImpl(null, string);
	             });
	  assertThrows(IllegalArgumentException.class,
	             ()->{
	              new DocumentImpl(uriEmpty, string);
	             });
	 }
	














//public class UnitTests {
	
	@Test
	  public void docTest() throws URISyntaxException {
	    URI me1 = new URI("hello1");
	    URI me2 = new URI("hello2");
	    String s1 = "Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^";
	    byte[] b1 = { 1, 2, 4, 56, 7, 7, 6, 5, 43, 4, 6, 7, 8, 8, 8, 55, 52, 5, 2, 52, 75, 95, 25, 85, 74, 52, 52, 5, 67,
	        61 };
	    Document doc1 = new DocumentImpl(me1, s1);
	    Document doc2 = new DocumentImpl(me2, b1);
	    assertEquals("Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^", doc1.getDocumentTxt());
	    assertEquals(b1, doc2.getDocumentBinaryData());
	  }
	    @Test
	    void TestBasicTxtDocument() throws URISyntaxException {
	        URI uri = new URI ("Hello");
	        DocumentImpl text = new DocumentImpl(uri, "Hello");
	        assertEquals("Hello", text.getDocumentTxt());
	        assertEquals(null, text.getDocumentBinaryData());
	        assertEquals(uri, text.getKey());
	        assertEquals(true, text.equals(new DocumentImpl(uri, "Hello")));
	    }
	    @Test
	    void TestBasicBinaryDocument() throws URISyntaxException {
	        String str = "Hello";
	        URI uri = new URI ("Hello");
	        byte[] test = str.getBytes();
	        DocumentImpl binary = new DocumentImpl(uri, test);
	        assertEquals(null, binary.getDocumentTxt());
	        assertEquals(test, binary.getDocumentBinaryData());
	        assertEquals(uri, binary.getKey());
	        assertTrue(binary.equals(new DocumentImpl(uri, test)));
	    }

	    @Test
	    void illegal () throws URISyntaxException {
	        boolean test = false;
	        String str = "Hello";
	        URI uri = new URI ("Hello");
	        byte[] byteArray = str.getBytes();
	        String nullString = null;
	        URI nullUri = null;
	        byte[] nullArray = null;
	        String emptyString = "";
	        byte[] emptyArray = new byte[0];
	        URI emptyUri = new URI("");
	        try{
	            DocumentImpl testNullUriInString = new DocumentImpl(nullUri, str);
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestNullUriInByte = new DocumentImpl(nullUri, byteArray); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestNullStringInString = new DocumentImpl(uri, nullString); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestNullByteInByte = new DocumentImpl(uri, nullArray); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestEmptyStringInString = new DocumentImpl(uri, emptyString); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestEmptyByteInByte = new DocumentImpl(uri, emptyArray); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestEmptyUriInByte = new DocumentImpl(emptyUri, byteArray); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;
	        try{
	            DocumentImpl TestEmptyUriInString = new DocumentImpl(emptyUri, str); 
	        } catch (IllegalArgumentException e) {
	            test=true;
	        }
	        assertEquals(true, test);
	        test=false;

	    }
	    
	    @Test
	    void addNewToStore() throws URISyntaxException, IOException {
	        DocumentStoreImpl store = new DocumentStoreImpl();
	        String str = "Hello";
	        byte[] array = str.getBytes();
	        ByteArrayInputStream stream = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream2 = new ByteArrayInputStream(array);
	        URI uri = new URI("Hello");
	        assertEquals(0, store.putDocument(stream, uri, DocumentFormat.BINARY));
	        Document doc = new DocumentImpl(uri, stream2.readAllBytes());
	        assertEquals(doc, store.getDocument(uri));
	    }
	    @Test
	    void addOldToStore() throws URISyntaxException, IOException {
	        DocumentStoreImpl store = new DocumentStoreImpl();
	        String str = "Hello";
	        byte[] array = str.getBytes();
	        ByteArrayInputStream stream = new ByteArrayInputStream(array);
	        URI uri = new URI("Hello");
	        String str2 = "Hi";
	        byte[] array2 = str2.getBytes();
	        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2);
	        DocumentImpl doc = new DocumentImpl(uri, array);
	        store.putDocument(stream, uri, DocumentFormat.BINARY);
	        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentFormat.BINARY));
	        DocumentImpl doc2 = new DocumentImpl(uri, array2);
	        assertEquals(doc2, store.getDocument(uri));

	    }
	    @Test
	    void TestDeleteDocument() throws URISyntaxException, IOException {
	        DocumentStoreImpl store = new DocumentStoreImpl();
	        String str = "Hello";
	        byte[] array = str.getBytes();
	        ByteArrayInputStream stream = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream2 = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream3 = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream4 = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream5 = new ByteArrayInputStream(array);
	        ByteArrayInputStream stream6 = new ByteArrayInputStream(array);
	        URI uri = new URI("Hello");
	        URI uri1 = new URI("Hi");
	        assertEquals(0, store.putDocument(stream, uri, DocumentFormat.BINARY));
	        assertEquals(0, store.putDocument(stream6, uri1, DocumentFormat.BINARY));
	        Document doc = new DocumentImpl(uri, stream2.readAllBytes());
	        assertEquals(doc, store.getDocument(uri));
	        assertNotNull(store.getDocument(uri1));
	        assertTrue(store.deleteDocument(uri1));
	        assertFalse(store.deleteDocument(new URI("Pizza")));
	        assertNull(store.getDocument(uri1));
	        assertNotNull(store.getDocument(uri));
	        assertEquals(0, store.putDocument(stream3, uri1, DocumentFormat.BINARY));
	        Document doc2 = new DocumentImpl(uri, stream4.readAllBytes());
	        assertEquals(doc2, store.getDocument(uri));
	        assertEquals(doc2.hashCode(), store.putDocument(null, uri, DocumentFormat.BINARY));
	        assertNull(store.getDocument(uri));
	        assertNotNull(store.getDocument(uri1));
	    }
	    /*@Test
	    void testPutDocumentStoreAsText() {
	     DocumentStore documentStore = new DocumentStoreImpl();
	     String string1 = "It was a dark and stormy night";
	     String string2 = "It was the best of times, it was the worst of times";
	     String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
	     String string4 = "I am free, no matter what rules surround me.";
	     InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
	     InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
	     InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
	     InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
	     URI uri1 =  URI.create("www.wrinkleintime.com");
	     URI uri2 =  URI.create("www.taleoftwocities.com");
	     URI uri3 =  URI.create("www.1984.com");
	     URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	     try {
	      documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
	      documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
	      documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
	      documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
	     } catch (java.io.IOException e) {
	      fail();
	     }
	     Document document1 = new DocumentImpl(uri1, string1);
	     Document document2 = new DocumentImpl(uri2, string2);
	     Document document3 = new DocumentImpl(uri3, string3);
	     Document document4 = new DocumentImpl(uri4, string4);
	     int test1 = documentStore.getDocument(uri1).hashCode();
	     int test2 = documentStore.getDocument(uri2).hashCode();
	     int test3 = documentStore.getDocument(uri3).hashCode();
	     int test4 = documentStore.getDocument(uri4).hashCode();
	     assertEquals(document1.hashCode(),test1);
	     assertEquals(document2.hashCode(),test2);
	     assertEquals(document3.hashCode(),test3);
	     assertEquals(document4.hashCode(),test4);
	    }*/
	    /*@Test
	    void testPutDocumentStoreAsBinary() {
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
	     URI uri1 =  URI.create("www.wrinkleintime.com");
	     URI uri2 =  URI.create("www.taleoftwocities.com");
	     URI uri3 =  URI.create("www.1984.com");
	     URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	     try {
	      documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
	      documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
	      documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
	      documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
	     } catch (java.io.IOException e) {
	      fail();
	     }
	     Document document1 = new DocumentImpl(uri1, bytes1);
	     Document document2 = new DocumentImpl(uri2, bytes2);
	     Document document3 = new DocumentImpl(uri3, bytes3);
	     Document document4 = new DocumentImpl(uri4, bytes4);
	     int test1 = documentStore.getDocument(uri1).hashCode();
	     int test2 = documentStore.getDocument(uri2).hashCode();
	     int test3 = documentStore.getDocument(uri3).hashCode();
	     int test4 = documentStore.getDocument(uri4).hashCode();
	     assertEquals(document1.hashCode(),test1);
	     assertEquals(document2.hashCode(),test2);
	     assertEquals(document3.hashCode(),test3);
	     assertEquals(document4.hashCode(),test4);
	    }*/
	   /* @Test
	    void testThrowException() {
	     String string = "Not empty!";
	     String nullString = null;
	     byte[] bytes = string.getBytes();
	     byte[] nullBytes = null;
	     URI uriNotEmpty = URI.create("www.notempty.com");
	     URI uriEmpty = URI.create("");
	     assertThrows(IllegalArgumentException.class,
	                ()->{
	                 new DocumentImpl(uriNotEmpty, nullBytes);
	                });
	     assertThrows(IllegalArgumentException.class,
	                ()->{
	                 new DocumentImpl(uriNotEmpty, nullString);
	                });
	     assertThrows(IllegalArgumentException.class,
	                ()->{
	                 new DocumentImpl(null, bytes);
	                });
	     assertThrows(IllegalArgumentException.class,
	                ()->{
	                 new DocumentImpl(null, string);
	                });
	     assertThrows(IllegalArgumentException.class,
	                ()->{
	                 new DocumentImpl(uriEmpty, string);
	                });
	    }
	    */
	    @Test
	    void basicGetAndPut() {
	        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
	        table.put(1, 1);
	        assertEquals(1, (int)table.get(1));
	        assertEquals(1, (int)table.put(1, 2));
	        assertNull(table.put(2, 2));
	        assertEquals(2, (int)table.get(2));
	        assertNull(table.get(100));
	    }
	    @Test
	    void basicSameCell() {
	        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
	        table.put(0, 0);
	        table.put(5, 5);
	        assertEquals(0, (int)table.get(0));
	        assertEquals(5, (int)table.get(5));
	        assertEquals(5, (int)table.put(5, 10));
	        assertEquals(10, (int)table.get(5));
	        assertEquals(10, (int)table.put(5, 100));
	        assertNull(table.put(10, 1000));
	        assertEquals(0, (int)table.put(0, 1));
	        assertEquals(1, (int)table.get(0));
	        table.put(1, 1);
	        assertEquals(1, (int)table.put(1, 6));
	        assertEquals(6, (int)table.get(1));
	        assertEquals(100, (int)table.put(5, 5));
	    }
	    @Test
	    void testGetAndPut() {
	        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
	        for (int i=0; i<1000; i++) {
	            assertNull(table.put(i, i));
	        }
	        for (int i=0; i<1000; i++) {
	            assertEquals(i, (int)table.get(i));
	        }
	        for (int i=0; i<1000; i++) {
	            assertEquals(i, (int)table.put(i, i+1));
	        }
	        for (int i=0; i<100; i++) {
	            assertEquals(i+1, (int)table.get(i));
	        }
	    }
	    @Test
	  void hashTableImplSimplePutAndGet() {
	   HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	   hashTable.put(1,2);
	   hashTable.put(3,6);
	   hashTable.put(7,14);
	   int x = hashTable.get(1);
	   int y = hashTable.get(3);
	   int z = hashTable.get(7);
	   assertEquals(2, x);
	   assertEquals(6, y);
	   assertEquals(14, z);
	   
	   
	    
	  }
	  
	  @Test
	  void hashTableImplALotOfInfoTest() {
	   HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	   for (int i = 0; i<1000; i++) {
	    hashTable.put(i,2*i);
	   }
	   
	   int aa = hashTable.get(450);
	   assertEquals(900, aa);
	  }
	  
	  
	  @Test
	  void hashTableImplCollisionTest() {
	   HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	   hashTable.put(1, 9);
	   hashTable.put(6,12);
	   hashTable.put(11,22);
	   int a = hashTable.get(1);
	   int b = hashTable.get(6);
	   int c = hashTable.get(11);
	   assertEquals(9, a);
	   assertEquals(12, b);
	   assertEquals(22, c);
	  }
	  
	  @Test
	  void hashTableImplReplacementTest() {
	   HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	   hashTable.put(1,2);
	   int a = hashTable.put(1, 3);
	   assertEquals(2, a);
	   int b = hashTable.put(1, 4);
	   assertEquals(3,b);
	   int c = hashTable.put(1, 9);
	   assertEquals(4, c);
	  }
	  @Test
	  void hashTableDelNullPut() {
	   HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
	   
	   hashTable.put("Defied", (Integer)22345);
	   Integer test1a = hashTable.get("Defied");
	   assertEquals(test1a, (Integer)22345);
	   hashTable.put("Defied", null);
	   Integer test1b = hashTable.get("Defied");
	   assertEquals(test1b,null);
	   hashTable.put("Oakland", 87123);
	   
	   Integer test2a = hashTable.get("Oakland");
	   assertEquals(test2a, (Integer)87123);
	   hashTable.put("Oakland", null);
	   hashTable.get("Oakland");
	   Integer test2b = hashTable.get("Oakland");
	   assertEquals(test2b,null);
	   
	   hashTable.put("Sanguine", (Integer)4682);
	   Integer test3a = hashTable.get("Sanguine");
	   assertEquals(test3a, (Integer)4682);
	   hashTable.put("Sanguine", null);
	   hashTable.get("Sanguine");
	   Integer test3b = hashTable.get("Sanguine");
	   assertEquals(test3b,null);
	  }
	  @Test
	  public void hashTableImplReplacementTest2() {
	    HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
	    hashTable.put(1, 2);
	    int a = hashTable.put(1, 3);
	    assertEquals(2, a);
	    int b = hashTable.put(1, 4);
	    assertEquals(3, b);
	    int c = hashTable.put(1, 9);
	    assertEquals(4, c);
	  }
	  @Test
	  public void basicCollision() {
	    HashTable<Integer, String> hashTable = new HashTableImpl<Integer, String>();
	    hashTable.put(1, "Avi");
	    hashTable.put(5, "dinsky");
	    hashTable.put(6, "Radinsky");
	    hashTable.put(11, "gami");
	    assertEquals("gami", hashTable.put(11, "gthir"));
	    assertEquals("gthir", hashTable.get(11));
	    assertEquals("Avi", hashTable.get(1));
	    assertEquals("Radinsky", hashTable.get(6));
	  }
	  
	  @Test
	  void HashEqualButNotEqual() {
	   HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
	   
	   hashTable.put("tensada", 3521);
	   hashTable.put("friabili", 1253);
	   Integer test1a = hashTable.get("tensada");
	   assertEquals(test1a, (Integer)3521);
	   Integer test1b = hashTable.get("friabili");
	   assertEquals(test1b, (Integer)1253);
	   
	   hashTable.put("abyz", 8948);
	   hashTable.put("abzj", 84980);
	   Integer test2a = hashTable.get("abyz");
	   assertEquals(test2a, (Integer)8948);
	   Integer test2b = hashTable.get("abzj");
	   assertEquals(test2b, 84980);
	   
	   hashTable.put("Siblings", 27128);
	   hashTable.put("Teheran", 82172);
	   Integer test3a = hashTable.get("Siblings");
	   assertEquals(test3a, 27128);
	   Integer test3b = hashTable.get("Teheran");
	   assertEquals(test3b, (Integer)82172);
	   
	  }

	  @Test
	  public void testPutNullDeletion() throws IOException {
	      DocumentStore documentStore = new DocumentStoreImpl();
	      String string1 = "It was a dark and stormy night";
	      String string2 = "It was the best of times, it was the worst of times";
	      String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
	      String string4 = "I am free, no matter what rules surround me.";
	      byte[] bytes3 = string3.getBytes();
	      byte[] bytes4 = string4.getBytes();
	      InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
	      InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
	      InputStream inputStream3 = new ByteArrayInputStream(bytes3);
	      InputStream inputStream4 = new ByteArrayInputStream(bytes4);
	      URI uri1 =  URI.create("www.wrinkleintime.com");
	      URI uri2 =  URI.create("www.taleoftwocities.com");
	      URI uri3 =  URI.create("www.1984.com");
	      URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	      int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
	      int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
	      int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
	      int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
	      assertEquals(putTXT1,0);
	      assertEquals(putTXT2,0);
	      assertEquals(putBINARY1,0);
	      assertEquals(putBINARY2,0);
	      documentStore.putDocument(null,uri1,DocumentStore.DocumentFormat.TXT);
	      documentStore.putDocument(null,uri2,DocumentStore.DocumentFormat.TXT);
	      documentStore.putDocument(null,uri3,DocumentStore.DocumentFormat.BINARY);
	      documentStore.putDocument(null,uri4,DocumentStore.DocumentFormat.BINARY);
	      Document nullDoc1 = documentStore.getDocument(uri1);
	      Document nullDoc2 = documentStore.getDocument(uri2);
	      Document nullDoc3 = documentStore.getDocument(uri3);
	      Document nullDoc4 = documentStore.getDocument(uri4);
	      assertEquals(nullDoc1,null);
	      assertEquals(nullDoc2,null);
	      assertEquals(nullDoc3,null);
	      assertEquals(nullDoc4,null);
	  }
	  @Test
	  public void testSimplePutValues() throws IOException {
	      DocumentStore documentStore = new DocumentStoreImpl();
	      String string1 = "It was a dark and stormy night";
	      String string2 = "It was the best of times, it was the worst of times";
	      String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
	      String string4 = "I am free, no matter what rules surround me.";
	      byte[] bytes3 = string3.getBytes();
	      byte[] bytes4 = string4.getBytes();
	      InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
	      InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
	      InputStream inputStream3 = new ByteArrayInputStream(bytes3);
	      InputStream inputStream4 = new ByteArrayInputStream(bytes4);
	      URI uri1 =  URI.create("www.wrinkleintime.com");
	      URI uri2 =  URI.create("www.taleoftwocities.com");
	      URI uri3 =  URI.create("www.1984.com");
	      URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	      int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
	      int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
	      int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
	      int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
	      assertEquals(putTXT1,0);
	      assertEquals(putTXT2,0);
	      assertEquals(putBINARY1,0);
	      assertEquals(putBINARY2,0);
	  }
	  @Test
public void testCollisionPutValues() throws IOException {
    DocumentStore documentStore = new DocumentStoreImpl();
    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times, it was the worst of times";
    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
    String string4 = "I am free, no matter what rules surround me.";
    byte[] bytes3 = string3.getBytes();
    byte[] bytes4 = string4.getBytes();
    InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
    InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
    InputStream inputStream3 = new ByteArrayInputStream(bytes3);
    InputStream inputStream4 = new ByteArrayInputStream(bytes4);
    URI uri1 =  URI.create("www.wrinkleintime.com");
    URI uri2 =  URI.create("www.taleoftwocities.com");
    URI uri3 =  URI.create("www.1984.com");
    URI uri4 =  URI.create("www.themoonisaharshmistress.com");
    documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
    int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
    int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
    int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
    int test1 = documentStore.getDocument(uri1).hashCode();
    int test2 = documentStore.getDocument(uri2).hashCode();
    int test3 = documentStore.getDocument(uri3).hashCode();
    int test4 = documentStore.getDocument(uri4).hashCode();
    InputStream inputStream1b = new ByteArrayInputStream(string1.getBytes());
    InputStream inputStream2b = new ByteArrayInputStream(string2.getBytes());
    InputStream inputStream3b = new ByteArrayInputStream(bytes3);
    InputStream inputStream4b = new ByteArrayInputStream(bytes4);
    int putBINARY1Collision = documentStore.putDocument(inputStream1b,uri1,DocumentStore.DocumentFormat.BINARY);
    int putBINARY2Collision = documentStore.putDocument(inputStream2b,uri2,DocumentStore.DocumentFormat.BINARY);
    int putTXT1Collision = documentStore.putDocument(inputStream3b,uri3,DocumentStore.DocumentFormat.TXT);
    int putTXT2Collision = documentStore.putDocument(inputStream4b,uri4,DocumentStore.DocumentFormat.TXT);
    Document document1 = new DocumentImpl(uri1, string1.getBytes());
    Document document2 = new DocumentImpl(uri2, string2.getBytes());
    Document document3 = new DocumentImpl(uri3, string3);
    Document document4 = new DocumentImpl(uri4, string4);
    assertEquals(putBINARY1Collision,test1);
    assertEquals(putBINARY2Collision,test2);
    assertEquals(putTXT1Collision,test3);
    assertEquals(putTXT2Collision,test4);
}
	
	
	@Test
	public void DocumentStoreImplTest() throws URISyntaxException, IOException {
		String initialString = "string";
		String initialString1 = "string1";
		String initialString2 = "string2";
		String initialString3 = "string3";
		String initialString4 = "string4";
		String initialString5 = "string5";
		String initialString6 = "string6";
		String initialString7 = "string7";
		String initialString8 = "string8";
		String initialString9 = "string9";
		String initialString13 = "string13";
		String initialString11 = "string11";
		String initialString12 = "string12";
		
	    InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
	    InputStream targetStream1 = new ByteArrayInputStream(initialString1.getBytes());
	    InputStream targetStream2 = new ByteArrayInputStream(initialString2.getBytes());
	    InputStream targetStream3 = new ByteArrayInputStream(initialString3.getBytes());
	    InputStream targetStream4 = new ByteArrayInputStream(initialString4.getBytes());
	    InputStream targetStream5 = new ByteArrayInputStream(initialString5.getBytes());
	    InputStream targetStream6 = new ByteArrayInputStream(initialString6.getBytes());
	    InputStream targetStream7 = new ByteArrayInputStream(initialString7.getBytes());
	    InputStream targetStream8 = new ByteArrayInputStream(initialString8.getBytes());
	    InputStream targetStream9 = new ByteArrayInputStream(initialString9.getBytes());
	    InputStream targetStream13 = new ByteArrayInputStream(initialString13.getBytes());
	    InputStream targetStream11 = new ByteArrayInputStream(initialString11.getBytes());
	    InputStream targetStream12 = new ByteArrayInputStream(initialString12.getBytes());
	    
	    InputStream targetStream10 = new ByteArrayInputStream(initialString.getBytes());
	   
	    
	    DocumentStore documentStore = new DocumentStoreImpl();
		
	  	String byteTxt = "https://www.HashTableImplByte.org/"; 
	  	String stringTxt = "https://www.HashTableImplString.org/"; 
	  	URI byteUri = new URI(byteTxt); 
	  	URI stringUri = new URI(stringTxt); 
	  	
	  	String byteTxt1 = "https://www.HashTableImplByteOne.org/"; 
	  	String stringTxt1 = "https://www.HashTableImplStringOne.org/"; 
	  	URI byteUri1 = new URI(byteTxt1); 
	  	URI stringUri1 = new URI(stringTxt1);
	  	
	  	String byteTxt2 = "https://www.HashTableImplByteTwo.org/"; 
	  	String stringTxt2 = "https://www.HashTableImplStringTwo.org/"; 
	  	URI byteUri2 = new URI(byteTxt2); 
	  	URI stringUri2 = new URI(stringTxt2); 
	  	
	  	String byteTxt3 = "https://www.HashTableImplByteThree.org/"; 
	  	String stringTxt3 = "https://www.ThreeHashTableImplString.org/"; 
	  	URI byteUri3 = new URI(byteTxt3); 
	  	URI stringUri3 = new URI(stringTxt3); 
	  	
	  	String byteTxt4 = "https://www.HashTableImplByteFour.org/"; 
	  	String stringTxt4 = "https://www.HashTableImplStringFour.org/"; 
	  	URI byteUri4 = new URI(byteTxt4); 
	  	URI stringUri4 = new URI(stringTxt4); 
	  	
	  	String byteTxt5 = "https://www.HashTableImplByteFive.org/"; 
	  	String stringTxt5 = "https://www.HashTableImplStringFive.org/"; 
	  	URI byteUri5 = new URI(byteTxt5); 
	  	URI stringUri5 = new URI(stringTxt5); 
	  	
	  	String byteTxt6 = "https://www.SixHashTableImplByte.org/"; 
	  	String stringTxt6 = "https://www.SixHashTableImplString.org/"; 
	  	URI byteUri6 = new URI(byteTxt6); 
	  	URI stringUri6 = new URI(stringTxt6); 
	  	
	  	String byteTxt7 = "https://www.HashTableImplByteSeven.org/"; 
	  	String stringTxt7 = "https://www.HashTableImplStringSeven.org/"; 
	  	URI byteUri7 = new URI(byteTxt7); 
	  	URI stringUri7 = new URI(stringTxt7); 
	  	
	  	String byteTxt8 = "https://www.HashTableImplByteEight.org/"; 
	  	String stringTxt8 = "https://www.HashTableImplStringEight.org/"; 
	  	URI byteUri8 = new URI(byteTxt8); 
	  	URI stringUri8 = new URI(stringTxt8); 
	  	
	  	String byteTxt9 = "https://www.HashTableImplByteNine.org/"; 
	  	String stringTxt9 = "https://www.HashTableImplStringNine.org/"; 
	  	URI byteUri9 = new URI(byteTxt9); 
	  	URI stringUri9 = new URI(stringTxt9); 
	  	
	  	byte byteInput[] = {20,10,30,5};
	  	InputStream targetStreamBytes = new ByteArrayInputStream(byteInput);
	  	
	  	byte byteInput1[] = {20,10,30,5,6};
	  	InputStream targetStreamBytes1 = new ByteArrayInputStream(byteInput1);
	  	
	  	byte byteInput2[] = {20,10,30,5,7};
	  	InputStream targetStreamBytes2 = new ByteArrayInputStream(byteInput2);
	  	
	  	byte byteInput3[] = {20,10,30,5,8};
	  	InputStream targetStreamBytes3 = new ByteArrayInputStream(byteInput3);
	  	
	  	byte byteInput4[] = {20,10,30,5,9};
	  	InputStream targetStreamBytes4 = new ByteArrayInputStream(byteInput4);
	  	
	  	byte byteInput5[] = {20,10,30,5,5};
	  	InputStream targetStreamBytes5 = new ByteArrayInputStream(byteInput5);
	  	
	  	byte byteInput6[] = {20,10,30,5,4};
	  	InputStream targetStreamBytes6 = new ByteArrayInputStream(byteInput6);
	  	
	  	byte byteInput7[] = {20,10,30,5,3};
	  	InputStream targetStreamBytes7 = new ByteArrayInputStream(byteInput7);
	  	
	  	byte byteInput8[] = {20,10,30,5,2};
	  	InputStream targetStreamBytes8 = new ByteArrayInputStream(byteInput8);
	  	
	  	byte byteInput9[] = {20,10,30,5,1};
	  	InputStream targetStreamBytes9 = new ByteArrayInputStream(byteInput9);
	  	
	  	
	  	byte byteInput10[] = {20,10,30,5};
	  	InputStream targetStreamBytes10 = new ByteArrayInputStream(byteInput10);
	  	
	  	
	  	
	  	int x = documentStore.putDocument(targetStream, stringUri, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(x,0);
	  	int y = documentStore.putDocument(targetStreamBytes, byteUri, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(y,0);
	  	
	  	//int previousDocHashcode = documentStore.getDocument(stringUri).hashCode();
	  	
	  	int z = documentStore.putDocument(targetStream1, stringUri1, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(z, 0);
	  	
	  	//int previousDocHashcode1 = documentStore.getDocument(stringUri1).hashCode();
	  	
	  	int zz = documentStore.putDocument(targetStream2, stringUri2, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(0, zz);
	  	
	  	int zzzss = documentStore.putDocument(targetStream8, stringUri8, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(0, zzzss);
	  	
	  	int zzzq = documentStore.putDocument(targetStream3, stringUri3, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(0, zzzq);
	  	
	  	int zzzw = documentStore.putDocument(targetStream4, stringUri4, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(0, zzzw);
	  	
	  	int zzze = documentStore.putDocument(targetStream5, stringUri5, DocumentStore.DocumentFormat.TXT);
	  	assertEquals(0, zzze);
	  	
	  	//int zzzs = documentStore.putDocument(targetStream6, stringUri6, DocumentStore.DocumentFormat.TXT);
	  	//assertEquals(0, zzzs);
	  	
	  	int zzza = documentStore.putDocument(targetStream7, stringUri7, DocumentStore.DocumentFormat.TXT);
	  	//assertEquals(0, zzza);
	  	
	  	
	  	
	  	assertEquals(documentStore.getDocument(stringUri8).getDocumentTxt(), "string8");
	  	assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string7");
	  	//assertEquals(documentStore.getDocument(stringUri6).getDocumentTxt(), "string6");
	  	assertEquals(documentStore.getDocument(stringUri5).getDocumentTxt(), "string5");
	  	assertEquals(documentStore.getDocument(stringUri4).getDocumentTxt(), "string4");
	  	assertEquals(documentStore.getDocument(stringUri3).getDocumentTxt(), "string3");
	  	assertEquals(documentStore.getDocument(stringUri2).getDocumentTxt(), "string2");
	  	assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string1");
	  	assertEquals(documentStore.getDocument(stringUri).getDocumentTxt(), "string");
	  	
	  	/*
	  	//assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string7");
	  	//int qqqq = documentStore.getDocument(stringUri1).hashCode();
	  	int asdg = documentStore.putDocument(targetStream12, stringUri7, DocumentStore.DocumentFormat.TXT);
	  	//assertEquals(asdg, qqqq);  
	  	assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string12");
	  	
	  	int wwww = documentStore.getDocument(stringUri7).hashCode();
	  	assertEquals(documentStore.putDocument(targetStream11, stringUri7, DocumentStore.DocumentFormat.TXT), wwww);  
	  	
	  	assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string11");
	  	
	  	
	  	int wwwwq = documentStore.getDocument(stringUri7).hashCode();
	  	assertEquals(documentStore.putDocument(targetStream13, stringUri7, DocumentStore.DocumentFormat.TXT), wwwwq);
	  	assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string13");
	
	  	*/
	}
	  	
	  	
	  	
	  	/*
	  	
	  	int a = documentStore.putDocument(null, stringUri, DocumentStore.DocumentFormat.TXT);
	  	
	  	assertEquals(null, documentStore.getDocument(stringUri));
	  	
	  	assertEquals(0, documentStore.putDocument(targetStream2, stringUri, DocumentStore.DocumentFormat.TXT));
	  	
	  	
	  	
	  	//assertEquals(documentStore.getDocument(stringUri), targetStream); //?????
	  	//Document getDocumentExample = documentStore.getDocument(stringUri);
	  	//assertEquals(getDocumentExample.getDocumentTxt(), targetStream);
	  	//Document stringDocument = new DocumentImpl (byteUri, byteInput);
	  	//Document byteDocument = new DocumentImpl (stringUri, stringInput);
	}*/
	/*
	@Test
	   public void hashTableDelNullPut() {
	    HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
	    hashTable.put("Defied", 22345);
	    int test1a = hashTable.get("Defied");
	    assertEquals(test1a,22345);
	    hashTable.put("Defied", null);
	    Integer test1b = hashTable.get("Defied");
	    assertEquals(test1b,null);
	    //String Str = new String("Oakland");
	    //System.out.println(Str.hashCode() + "Oakland");
	    hashTable.put("Oakland", 87123);
	    int test2a = hashTable.get("Oakland");
	    assertEquals(test2a,87123);
	    hashTable.put("Oakland", 3);
	    hashTable.get("Oakland");
	    int test2b = hashTable.get("Oakland");
	    assertEquals(test2b,3);
	    hashTable.put("Oakland", 7);
	    int test2c = hashTable.get("Oakland");
	    assertEquals(test2c,7);
	    hashTable.put("Oakland", 9);
	    int test2d = hashTable.get("Oakland");
	    assertEquals(test2d,9);
	    hashTable.put("Oakland", 11);
	    int test2e = hashTable.get("Oakland");
	    assertEquals(test2e,11);
	    
	    //hashTable.put( 2060800114, 4682);
	    hashTable.put( "Sanguine", 4682);
	    int test3a = hashTable.get("Sanguine");
	    assertEquals(test3a,4682);
	    hashTable.put( "Sanguine", 3);
	    //hashTable.get("Sanguine");
	    int test3b = hashTable.get( "Sanguine");
	    assertEquals(test3b, 3);
	    hashTable.put("Sanguine", 5);
	    int test3c = hashTable.get("Sanguine");
	    assertEquals(test3c,5);
	    hashTable.put("Sanguine", 7);
	    int test3d = hashTable.get("Sanguine");
	    assertEquals(test3d,7);
	    hashTable.put("Sanguine", 8);
	    int test3e = hashTable.get("Sanguine");
	    assertEquals(test3e,8);
	    
	  //hashTable.put( 2060800114, 4682);
	    hashTable.put( "Sang", 4682);
	    int test4a = hashTable.get("Sang");
	    assertEquals(test4a,4682);
	    hashTable.put( "Sang", 3);
	    //hashTable.get("Sanguine");
	    int test4b = hashTable.get( "Sang");
	    assertEquals(test4b, 3);
	    hashTable.put("Sang", 5);
	    int test4c = hashTable.get("Sang");
	    assertEquals(test4c,5);
	    hashTable.put("Sang", 7);
	    int test4d = hashTable.get("Sang");
	    assertEquals(test4d,7);
	    hashTable.put("Sang", 8);
	    int test4e = hashTable.get("Sang");
	    assertEquals(test4e,8);
	    
	   }
	   @Test
	   public void HashEqualButNotEqual() {
	    HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
	    //hashTable.put("tensada", 3521);
	    hashTable.put("friabili", 1253);
	    //int test1a = hashTable.get("tensada");
	    //assertEquals(test1a, 3521);
	    int test1b = hashTable.get("friabili");
	    assertEquals(test1b, 1253);
	    hashTable.put("abyz", 8948);
	    hashTable.put("abzj", 84980);
	    int test2a = hashTable.get("abyz");
	    assertEquals(test2a, 8948);
	    int test2b = hashTable.get("abzj");
	    assertEquals(test2b, 84980);
	    hashTable.put("Siblings", 27128);
	    hashTable.put("Teheran", 82172);
	    int test3a = hashTable.get("Siblings");
	    assertEquals(test3a, 27128);
	    int test3b = hashTable.get("Teheran");
	    assertEquals(test3b, 82172);
	   }
	   
	   
	   @Test
	   void testPutDocumentStoreAsText() {
	    DocumentStore documentStore = new DocumentStoreImpl();
	    String string1 = "It was a dark and stormy night";
	    String string2 = "It was the best of times, it was the worst of times";
	    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
	    String string4 = "I am free, no matter what rules surround me.";
	    InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
	    InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
	    InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
	    InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
	    URI uri1 =  URI.create("www.wrinkleintime.com");
	    URI uri2 =  URI.create("www.taleoftwocities.com");
	    URI uri3 =  URI.create("www.1984.com");
	    URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	    try {
	     documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
	     documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
	     documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
	     documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
	    } catch (java.io.IOException e) {
	     fail();
	    }
	    Document document1 = new DocumentImpl(uri1, string1);
	    Document document2 = new DocumentImpl(uri2, string2);
	    Document document3 = new DocumentImpl(uri3, string3);
	    Document document4 = new DocumentImpl(uri4, string4);
	    int test1 = documentStore.getDocument(uri1).hashCode();
	    int test2 = documentStore.getDocument(uri2).hashCode();
	    int test3 = documentStore.getDocument(uri3).hashCode();
	    int test4 = documentStore.getDocument(uri4).hashCode();
	    assertEquals(document1.hashCode(),test1);
	    assertEquals(document2.hashCode(),test2);
	    assertEquals(document3.hashCode(),test3);
	    assertEquals(document4.hashCode(),test4);
	   }
	   @Test
	   void testPutDocumentStoreAsBinary() {
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
	    URI uri1 =  URI.create("www.wrinkleintime.com");
	    URI uri2 =  URI.create("www.taleoftwocities.com");
	    URI uri3 =  URI.create("www.1984.com");
	    URI uri4 =  URI.create("www.themoonisaharshmistress.com");
	    try {
	     documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
	     documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
	     documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
	     documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
	    } catch (java.io.IOException e) {
	     fail();
	    }
	    Document document1 = new DocumentImpl(uri1, bytes1);
	    Document document2 = new DocumentImpl(uri2, bytes2);
	    Document document3 = new DocumentImpl(uri3, bytes3);
	    Document document4 = new DocumentImpl(uri4, bytes4);
	    int test1 = documentStore.getDocument(uri1).hashCode();
	    int test2 = documentStore.getDocument(uri2).hashCode();
	    int test3 = documentStore.getDocument(uri3).hashCode();
	    int test4 = documentStore.getDocument(uri4).hashCode();
	    assertEquals(document1.hashCode(),test1);
	    assertEquals(document2.hashCode(),test2);
	    assertEquals(document3.hashCode(),test3);
	    assertEquals(document4.hashCode(),test4);
	   }
	   @Test
	   void testThrowException() {
	    String string = "Not empty!";
	    String nullString = null;
	    byte[] bytes = string.getBytes();
	    byte[] nullBytes = null;
	    URI uriNotEmpty = URI.create("www.notempty.com");
	    URI uriEmpty = URI.create("");
	    assertThrows(IllegalArgumentException.class,
	               ()->{
	                new DocumentImpl(uriNotEmpty, nullBytes);
	               });
	    assertThrows(IllegalArgumentException.class,
	               ()->{
	                new DocumentImpl(uriNotEmpty, nullString);
	               });
	    assertThrows(IllegalArgumentException.class,
	               ()->{
	                new DocumentImpl(null, bytes);
	               });
	    assertThrows(IllegalArgumentException.class,
	               ()->{
	                new DocumentImpl(null, string);
	               });
	    assertThrows(IllegalArgumentException.class,
	               ()->{
	                new DocumentImpl(uriEmpty, string);
	               });
	   }
	
	
	
  @Test
  public void hashTableImplSimplePutAndGet() {
	  HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	  hashTable.put(1,2);
	  hashTable.put(3,6);
	  hashTable.put(7,14);
	  int x = hashTable.get(1);
	  int y = hashTable.get(3);
	  int z = hashTable.get(7);
	  assertEquals(2, x);
	  assertEquals(6, y);
	  assertEquals(14, z);
	  
	  
    
  }
  
  @Test
  public void hashTableImplALotOfInfoTest() {
	  HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	  for (int i = 0; i<1000; i++) {
		  hashTable.put(i,2*i);
	  }
	  
	  int aa = hashTable.get(450);
	  assertEquals(900, aa);
  }
  
  
  @Test
  public void hashTableImplCollisionTest() {
	  HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	  hashTable.put(1, 9);
	  hashTable.put(6,12);
	  hashTable.put(11,22);
	  int a = hashTable.get(1);
	  int b = hashTable.get(6);
	  int c = hashTable.get(11);
	  assertEquals(9, a);
	  assertEquals(12, b);
	  assertEquals(22, c);
  }
  
  @Test
  public void hashTableImplReplacementTest() {
	  HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
	  hashTable.put(1,2);
	  int a = hashTable.put(1, 3);
	  assertEquals(2, a);
	  int b = hashTable.put(1, 4);
	  assertEquals(3,b);
	  int c = hashTable.put(1, 9);
	  assertEquals(4, c);
  }
  */
	
	
  @Test
  public void DocumentImplGetKeyTest() throws URISyntaxException {
	 
	  URI uri = new URI("https://www.google.com/");
	  String str = "heeelllllllllllllllllooooooooo";
	  byte[] myvar = "Any String you want".getBytes();
	  Document document = new DocumentImpl(uri, str);
	  assertEquals(document.getKey(), uri);
  }
  
  @Test
  public void DocumentImplgetDocumentTxtTest() throws URISyntaxException {
	  URI uri = new URI("https://www.google.com/");
	  String str = "heeelllllllllllllllllooooooooo";
	  byte[] myvar = "Any String you want".getBytes();
	  Document document = new DocumentImpl(uri, str);
	  assertEquals(document.getDocumentTxt(), str);
  }
  
  @Test
  public void DocumentImplgetDocumentBinaryData() throws URISyntaxException {
	  URI uri = new URI("https://www.google.com/");
	  String str = "heeelllllllllllllllllooooooooo";
	  byte[] myVar = "Any String you want".getBytes();
	  Document documentStrings = new DocumentImpl(uri, str);
	  Document documentBytes = new DocumentImpl(uri, myVar);
	  assertEquals(documentBytes.getDocumentBinaryData(), myVar);
  }
  
  @Test
  public void DocumentImplEquals() throws URISyntaxException {
	  URI uri = new URI("https://www.google.com/");
	  String str = "heeelllllllllllllllllooooooooo";
	  byte[] myVar = "Any String you want".getBytes();
	  Document documentStrings = new DocumentImpl(uri, str);
	  Document documentStrings2 = new DocumentImpl(uri, str);
	  Document documentBytes = new DocumentImpl(uri, myVar);
	  System.out.println(documentStrings.equals(documentStrings2));
	  assertEquals(documentBytes.getDocumentBinaryData(), myVar);
  }
  

@Test
public void undoTest() throws IOException {
    DocumentStore documentStore = new DocumentStoreImpl();
    Boolean test = false;
    try {
        documentStore.undo();
    } catch (IllegalStateException e) {
        test = true;
    }
    assertEquals(true, test);
    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times, it was the worst of times";
    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
    InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
    InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
    InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
    URI uri1 = URI.create("www.wrinkleintime.com");

    documentStore.putDocument(inputStream1, uri1, DocumentFormat.TXT);
    assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.putDocument(inputStream2, uri1, DocumentFormat.TXT);
    assertEquals(string2, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.undo();
    assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.undo();
    assertEquals(null, documentStore.getDocument(uri1));

    documentStore.putDocument(inputStream3, uri1, DocumentFormat.TXT);
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.deleteDocument(uri1);
    assertEquals(null, documentStore.getDocument(uri1));
    documentStore.undo();
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
}
@Test
public void testUndoSpecificUri() throws IOException {
    DocumentStore documentStore = new DocumentStoreImpl();

    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times, it was the worst of times";
    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
    String string4 = "I am free, no matter what rules surround me.";
    InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
    InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
    InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
    InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
    URI uri1 = URI.create("www.wrinkleintime.com");
    URI uri2 = URI.create("www.taleoftwocities.com");
    URI uri3 = URI.create("www.1984.com");

    documentStore.putDocument(inputStream1, uri1, DocumentFormat.TXT);
    assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.putDocument(inputStream2, uri2, DocumentFormat.TXT);
    assertEquals(string2, documentStore.getDocument(uri2).getDocumentTxt());
    documentStore.undo(uri1);
    assertEquals(null, documentStore.getDocument(uri1));
    assertEquals(string2, documentStore.getDocument(uri2).getDocumentTxt());
    documentStore.putDocument(inputStream3, uri1, DocumentFormat.TXT);
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.putDocument(inputStream4, uri1, DocumentFormat.TXT);
    assertEquals(string4, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.deleteDocument(uri1);
    assertEquals(null, documentStore.getDocument(uri1));
    documentStore.undo(uri2);
    assertEquals(null, documentStore.getDocument(uri2));
    documentStore.undo();
    assertEquals(string4, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.undo(uri1);
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());

    Boolean test = false;
    try {
        documentStore.undo(uri3);
    } catch (IllegalStateException e) {
        test = true;
    }
    assertEquals(true, test);
}
  
  
  
  

@Test
public void testPutDocumentWithNullArguments() throws Exception{
	

URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";

    //init possible values for doc2
    URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
    String txt2 = "Text for doc2. A plain old String.";


    DocumentStore store = new DocumentStoreImpl();
    try {
        store.putDocument(new ByteArrayInputStream(txt1.getBytes()), null, DocumentStore.DocumentFormat.TXT);
        fail("null URI should've thrown IllegalArgumentException");
    }catch(IllegalArgumentException e){}
    try {
        store.putDocument(new ByteArrayInputStream(txt1.getBytes()), uri1, null);
        fail("null format should've thrown IllegalArgumentException");
    }catch(IllegalArgumentException e){}
}


















@Test
    void testStackUndo() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1);
        ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri1 = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri1, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri1, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri1));
        store.undo(); assertEquals(null, store.getDocument(uri1));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }
    @Test
    void testStackUndoUri() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri1 = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri1, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri1, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri1));
        String str2 = "2";
        byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2);
        ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        URI uri2 = new URI("2");
        assertEquals(0, store.putDocument(stream2, uri2, DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri2, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri2));
        store.undo(uri1);
        assertEquals(null, store.getDocument(uri1));
        assertEquals(doc2, store.getDocument(uri2));
    }
    @Test
    void testStackUriPutOverwrite() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        store.undo();
        assertNotEquals(doc2, store.getDocument(uri));
        assertEquals(doc, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
    }
    @Test
    void testStackUriDeleteOverwrite() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri)); assertEquals(null, store.getDocument(uri));
        String str3 = "3"; byte[] array3 = str3.getBytes();
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3); ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri, DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri));
        store.undo(uri); assertEquals(null, store.getDocument(uri));
        store.undo(uri); assertEquals(doc2, store.getDocument(uri));
        store.undo(uri); assertEquals(doc, store.getDocument(uri));
        store.undo(uri); assertEquals(null, store.getDocument(uri));
    }
    @Test
    void testStackUriDeleteOverwriteNoParams() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri)); assertEquals(null, store.getDocument(uri));
        String str3 = "3"; byte[] array3 = str3.getBytes();
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3); ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri, DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
        store.undo(); assertEquals(doc2, store.getDocument(uri));
        store.undo(); assertEquals(doc, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
    }
    @Test
    void testStackUriDeleteOverwriteMultipleDocs() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri)); assertEquals(null, store.getDocument(uri));
        String str3 = "3"; byte[] array3 = str3.getBytes();
        URI uri2 = new URI("Hello");
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3); ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri2, DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri2, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri2));
        store.undo(uri); assertEquals(doc3, store.getDocument(uri2)); assertEquals(doc2, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri2));
        store.undo(); assertEquals(doc, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
    }
    @Test
public void undoTest1() throws IOException {
    DocumentStore documentStore = new DocumentStoreImpl();

    String string1 = "It was a dark and stormy night";
    String string2 = "It was the best of times, it was the worst of times";
    String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
    InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
    InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
    InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
    URI uri1 = URI.create("www.wrinkleintime.com");

    documentStore.putDocument(inputStream1, uri1, DocumentFormat.TXT);
    assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.putDocument(inputStream2, uri1, DocumentFormat.TXT);
    assertEquals(string2, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.undo();
    assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.undo();
    assertEquals(null, documentStore.getDocument(uri1));

    documentStore.putDocument(inputStream3, uri1, DocumentFormat.TXT);
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
    documentStore.deleteDocument(uri1);
    assertEquals(null, documentStore.getDocument(uri1));
    documentStore.undo();
    assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
}
    @Test
    void testThrowsException() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
        test = false;
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        URI uriFake = new URI("ThisIsAFake");
        try {
            store.undo(uriFake);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }
    @Test
    void testPointlessDeleteEmptyUndo() throws URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("Pizza");
        assertFalse(store.deleteDocument(uri));
        store.undo();
    }

   
    @Test
    void testPointlessPutEmptyUndo() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        String str1 = "1"; byte[] array1 = str1.getBytes();
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(null, uri, DocumentFormat.TXT));
        store.undo();
    }
    @Test
    void testPointlessPutFullUndo() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("http://edu.yu.cs/com1320/project/doc2"); 
        String str2 = "1"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array2); 
        ByteArrayInputStream stream11 = new ByteArrayInputStream(array2);     
        assertEquals(0, store.putDocument(stream1, uri, DocumentFormat.TXT));
        //assertEquals(0, store.putDocument(null, uri, DocumentFormat.TXT));
        //sertEquals(0, store.putDocument(null, uri, DocumentFormat.TXT));
        //assertNull(store.getDocument(uri));
        boolean test = false;
        try {
            store.undo(new URI("http://edu.yu.cs/com1320/project/doc1"));
        } catch (IllegalStateException e) {
            test = true;
        }
        //assertTrue(test);
        try{
     		store.undo(uri);
     	}catch (Exception e){
     		e.printStackTrace();
     	}
     	//store.undo(uri);
     	//store.undo(uri);
}


@Test
    void testPointlessPutFullUndo11() throws URISyntaxException, IOException {
    	/*String str2 = "1"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array2); 
        ByteArrayInputStream stream11 = new ByteArrayInputStream(array2);
        
        String str1 = "1";
        URI uri = new URI("1");
        store.putDocument(stream1, uri, DocumentFormat.BINARY);
        //assertNull(store.getDocument(uri));*/
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(null, uri, DocumentFormat.TXT));
        URI uri111 = new URI("1222222222222");
        boolean test = false;
        try {
            store.undo(uri111);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
       	
       	test = false;
        try {
            store.undo(new URI("Pizza"));
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);

        test = false;
        try {
            store.undo(new URI("Pizzassss"));
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);


        test = false;
        try {
            store.undo(new URI("Pizzaasdfa"));
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);


        test = false;
        try {
            store.undo(new URI("Pizfhfhfza"));
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
        //store.undo(uri);
    }

     @Test
    void testPointlessDeleteFullUndo() throws URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("Pizza");
        assertFalse(store.deleteDocument(uri));
        store.undo(uri);
    }

  
  
  
}

































