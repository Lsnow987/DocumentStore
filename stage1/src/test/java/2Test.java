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
import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.stage1.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage1.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage1.impl.DocumentStoreImpl;

import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.stage1.impl.*;




	class Test2 {
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
	}