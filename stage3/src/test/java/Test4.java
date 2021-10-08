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

import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import edu.yu.cs.com1320.project.stage3.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage3.impl.DocumentStoreImpl;

import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.*;
import edu.yu.cs.com1320.project.stage3.impl.*;







class Test4{

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