package edu.yu.cs.com1320.project.stage4.impl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;

public class DocumentStoreImpl implements DocumentStore{
    private final HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
	private final StackImpl<Undoable> stackImplementation = new StackImpl<>();
    private final TrieImpl<Document> trieImplementation = new TrieImpl(); // need v parameters???
    private MinHeapImpl<Document> minHeap = new MinHeapImpl<>();
    private Integer maxNumberofDocuments = null;
    private Integer maxNumberOFBytes = null;
    private int numberOfDocuments = 0;
    private int numberOfBytes = 0;
    private long firstNanoTime;
    boolean inTooMuchMemory = false;

    //deal with setting time of commandset 
    
	public DocumentStoreImpl(){}

	/**
     * the two document formats supported by this document store.
     * Note that TXT means plain text, i.e. a String.
     */
   // private enum DocumentFormat{
       // TXT,BINARY
   // };
    /**
     //* @param input the document being put
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the
     !!!!!!!!!!!!!!!!!!!!!!!!!!String version of the previous doc. 
     If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     */

    private void tooMuchMemory(){
        /*//System.out.println("tooMuchMemory");
        //System.out.println(maxNumberofDocuments + "      maxnumberofdocuments");
        //System.out.println(numberOfDocuments + "      numberofdocuments");
        //System.out.println(maxNumberOFBytes+ "         maxnumberofbytes");
        //System.out.println(numberOfBytes + "         numberofbytes");*/
        while ((maxNumberofDocuments!= null && numberOfDocuments > maxNumberofDocuments) || (maxNumberOFBytes!= null && numberOfBytes > maxNumberOFBytes)){
            Document document = minHeap.remove();//need delete oldest thing and then make sure enough memory
            minHeap.insert(document);
            ////System.out.println(hashTable.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            document.setLastUseTime(Integer.MIN_VALUE);
            ////System.out.println(hashTable.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            minHeap.reHeapify(document);
            //System.out.println(document.getLastUseTime() +"    ERRRROROROROORORORORORORORORRO" + document);
            ////System.out.println(hashTable.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            ////System.out.println("STACKOVERFLOWERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
            while(hashTable.get(document.getKey()) != null){
                ////System.out.println("222222222222222222222222222222222222222STACKOVERFLOWERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                ////System.out.println(document + "    document in too much memory");
                URI uri = document.getKey();
                inTooMuchMemory = true;
                undo(uri);
                /*if (hashTable.get(uri) != null){
                    hashTable.get(uri).setLastUseTime(Integer.MIN_VALUE);
                    try{
                        minHeap.reHeapify(hashTable.get(uri));
                    }catch(NoSuchElementException e){};
                }*/
            }
            inTooMuchMemory = false;
            ////System.out.println("266666666666666666666666666666666666666666666666666666666666STACKOVERFLOWERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        }
    }



    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException{
        if ( uri == null || uri.equals(URI.create("")) || format == null ) throw new IllegalArgumentException("the arguments can't be null");
		if ( input == null) {
            Document documentToBeDeleted = hashTable.get(uri);
            this.deleteDocument(uri);
            if(documentToBeDeleted == null){
               return 0;
            }
            return documentToBeDeleted.hashCode();
        }
        byte[] array = input.readAllBytes();
        //if(!putBelowMaxBytes(array, format)) return 0; //???????? what should i return
        Document previousValue = hashTable.get(uri);
        Document document = putDocumentPrivate(array, format, uri, previousValue);
        putDocumentCommand(uri, previousValue);
        if ( previousValue == null ) {
            numberOfDocuments++;
        }else{
            ////System.out.println(previousValue + "      PREVIOUSVALUEEEEEEEEEEEEEEEEEEEEE");
            previousValue.setLastUseTime(Integer.MIN_VALUE);
            //System.out.println(document.getLastUseTime() +"    11111ERRRROROROROORORORORORORORORRO" + document);
            minHeap.reHeapify(previousValue);
            minHeap.remove();
        }
        document.setLastUseTime(System.nanoTime());
        minHeap.insert(document);
        tooMuchMemory();
        hashTable.put(uri, document); ///////////////////////
        //try{
            //minHeap.reHeapify(document);
            ////System.out.println(document + "      reheapified");
        //}catch(NoSuchElementException e){
            ////System.out.println(document + "          inserted");
        //}
        if ( previousValue == null) return 0;
        return previousValue.hashCode();
    }

    /*private boolean putBelowMaxBytes(byte[] array, DocumentFormat format) throws IOException{
        ////System.out.println("putBelowMaxBytes");
        if ( format == DocumentFormat.TXT  ) {
            String s = new String(array);
            if(maxNumberOFBytes != null && s.getBytes().length >= maxNumberOFBytes){
                ////System.out.println("putBelowMaxBytes...........555555555555555555");
                return false;
            }
        }else{
            if(maxNumberOFBytes != null && array.length >= maxNumberOFBytes){
                ////System.out.println("putBelowMaxBytes1111111111111111111111");
                return false;
            }
        }
        return true;
    }*/

    private Document putDocumentPrivate(byte[] array, DocumentFormat format, URI uri, Document previousValue) throws IOException{
        Document document;
        if ( format == DocumentFormat.TXT  ) {
            String s = new String(array);
            ////System.out.println(s + "         sssssssssssssssss" + s.getBytes().length + array.length);
            ////System.out.println(numberOfBytes + "    numberofbytes before");
            numberOfBytes += s.getBytes().length;
            ////System.out.println(numberOfBytes + "    numberofbytes after");
            document = (Document) new DocumentImpl (uri, s);

            if (hashTable.get(uri) != null) {
                for (String wordString : hashTable.get(uri).getWords()) {
                    trieImplementation.delete(wordString, hashTable.get(uri));
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, document);
            }
        }else{
            numberOfBytes += array.length;
            document = (Document) new DocumentImpl (uri, array);
        }
        if(previousValue != null && previousValue.getDocumentBinaryData() != null){
            numberOfBytes -= previousValue.getDocumentBinaryData().length;
        }else if (previousValue != null && previousValue.getDocumentTxt() != null){
            numberOfBytes -= previousValue.getDocumentTxt().getBytes().length;
        }
        return document;
    }

    private void putDocumentCommand(URI uri, Document previousValue){
        Function<URI, Boolean> uriBoolean;
        if (previousValue==null){
            uriBoolean = uri1 -> this.deleteDocumentUndoVersion(uri);
        }else if (previousValue.getDocumentBinaryData() == null){
            final String previousTxt = previousValue.getDocumentTxt();
            uriBoolean = uri1 -> {
                try {
                    this.putDocumentUndoVersion(null, uri, previousTxt);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            };
        }else{
            uriBoolean = uri1 -> {
                final byte[] previousArray = previousValue.getDocumentBinaryData();
                try {
                    this.putDocumentUndoVersion(previousArray, uri, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            };
        }
        GenericCommand putCommand = new GenericCommand(uri, uriBoolean);
        stackImplementation.push(putCommand);
    }



    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri){
        URI uriEmpty = URI.create("");
        if (uri == null || uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        if(hashTable.get(uri) != null) {
            ////System.out.println( hashTable.get(uri) + "      get document set nano time");
            hashTable.get(uri).setLastUseTime(System.nanoTime());
            minHeap.reHeapify(hashTable.get(uri));
        }
        return hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     to delete means putting a null value
     */
    @Override
    public boolean deleteDocument(URI uri){
        ////System.out.println(getDocument(uri) + "      wrong uri???");

        if (uri == null || uri.equals(URI.create(""))) throw new IllegalArgumentException("the uri can't be empty");
        String initialString = "";
        //InputStream targetStream;
        byte[] array = null;
        if (hashTable.get(uri) == null){
            Function<URI, Boolean> uriBooleanDoesNothing = uri1 -> true;
            GenericCommand emptyDeleteCommand = new GenericCommand(uri, uriBooleanDoesNothing);
            stackImplementation.push(emptyDeleteCommand);
            return false;
        }else{
            ////System.out.println(numberOfDocuments + "    numberOfDocuments in delete before --");
            numberOfDocuments--;
            ////System.out.println(numberOfDocuments + "    numberOfDocuments in delete AFTER --");
            try {
			    deleteDocumentSetterAndCommand(initialString, array, uri);
		    } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    }
        }
        for (String s : hashTable.get(uri).getWords()) {
            trieImplementation.delete(s, hashTable.get(uri));
        }
        ////System.out.println(firstNanoTime + "    firstNanoTime    " + System.nanoTime());
        ////System.out.println(getDocument(uri) + "         deleted document");
        hashTable.get(uri).setLastUseTime(Integer.MIN_VALUE);
        //System.out.println(hashTable.get(uri).getLastUseTime() +"    22222222ERRRROROROROORORORORORORORORRO" + hashTable.get(uri));
        minHeap.reHeapify(hashTable.get(uri));
        minHeap.remove();
        hashTable.put(uri, null);
        return true;
    }

    private void deleteDocumentSetterAndCommand(String initialString, byte[] array, URI uri) throws IOException{
        if(hashTable.get(uri).getDocumentTxt() != null){
            initialString = hashTable.get(uri).getDocumentTxt();
            numberOfBytes -= initialString.getBytes().length;
        }else{
            array = hashTable.get(uri).getDocumentBinaryData();
            numberOfBytes -= array.length;
        }
        final String finalString = initialString;
        final byte[] arrayFinal = array;
        Document insertDoc = hashTable.get(uri);
        Function<URI, Boolean>  uriBoolean = uri1 -> {
            ////System.out.println("insert doc deleteDocumentSetterAndCommand");
            //minHeap.insert(insertDoc);
            //insertDoc.setLastUseTime(System.nanoTime());
        	try {
	            if(finalString.equals("")){
					return this.putDocumentUndoVersion(arrayFinal, uri, null);
				}else{
					return this.putDocumentUndoVersion(null, uri, finalString);
				}
        	}catch (IOException e) {
			// TODO Auto-generated catch block
			    e.printStackTrace();
			}
        	return true;
        };
        GenericCommand deleteCommand = new GenericCommand(uri, uriBoolean);
        stackImplementation.push(deleteCommand);
    }


    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException{
        if (stackImplementation.size() == 0){
            throw new IllegalStateException("the command stack is empty");
        }
        Undoable c = stackImplementation.pop();
        c.undo();
    }



    /**
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */




    public void undo(URI uri) throws IllegalStateException{
        StackImpl<Undoable> stackUndoImplementation = new StackImpl<>();
        Undoable c = stackImplementation.pop();
        GenericCommand genericCommandFromSet = null;
        if(c==null) throw new IllegalStateException("there are no actions on the command stack");
        boolean commandSetShouldLeaveStack= false;
        while(c instanceof CommandSet || !c.equals(new GenericCommand<>(uri, null)) /*&& stackImplementation.peek()!=null*/) {
            if (c instanceof CommandSet){
                if (((CommandSet<URI>) c).size() <=1 ) commandSetShouldLeaveStack = true;
                for (Object o : (CommandSet) c) {
                    GenericCommand gC = (GenericCommand) o;
                    if (gC.equals(new GenericCommand(uri, null))) {
                        genericCommandFromSet = gC;
                        break;
                    }
                }
                if (genericCommandFromSet != null && commandSetShouldLeaveStack) break;
            }
            stackUndoImplementation.push(c);
            if (genericCommandFromSet != null) break;

            if( stackImplementation.peek()!=null && !c.equals(new GenericCommand(uri,null))){
        	   c = stackImplementation.pop();
            }else{
                break;
            }
        }        
        undoSpecificUriPrivate(c, genericCommandFromSet, uri, stackUndoImplementation);
    }
    
    private void undoSpecificUriPrivate(Undoable c, GenericCommand genericCommandFromSet, URI uri,  StackImpl<Undoable> stackUndoImplementation){
        if (c instanceof GenericCommand && c.equals(new GenericCommand(uri,null))){
             c.undo();
        }
        if(genericCommandFromSet != null){
            ((CommandSet<URI>) c).undo(uri/*genericCommandFromSet*/);
        }
        while(stackUndoImplementation != null && stackUndoImplementation.peek() != null){
            Undoable reverseCommand = stackUndoImplementation.pop();
            stackImplementation.push(reverseCommand);
        }
        if (!c.equals(new GenericCommand<URI>(uri,null)) && genericCommandFromSet == null){
            throw new IllegalStateException("there are no actions on the command stack for the given URI");
        }
    }



    private boolean deleteDocumentUndoVersion(URI uri){
        URI uriEmpty = URI.create("");
        if (uri == null || uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        if (hashTable.get(uri) != null) {
            for (String wordString : hashTable.get(uri).getWords()) {
                trieImplementation.delete(wordString, hashTable.get(uri));
            }
            numberOfDocuments--;

            if(hashTable.get(uri).getDocumentBinaryData() != null){
                numberOfBytes -= hashTable.get(uri).getDocumentBinaryData().length;
            }else if (hashTable.get(uri).getDocumentTxt() != null){
                numberOfBytes -= hashTable.get(uri).getDocumentTxt().getBytes().length;
            }
        }
        hashTable.get(uri).setLastUseTime(Integer.MIN_VALUE);
        //System.out.println(hashTable.get(uri).getLastUseTime() +"    333333333ERRRROROROROORORORORORORORORRO" + hashTable.get(uri));
        //try{
        minHeap.reHeapify(hashTable.get(uri));
        minHeap.remove();
        //}catch(NoSuchElementException e){};
        hashTable.put(uri, null);
        return true;
    }

    private boolean putDocumentUndoVersion(byte[] array, URI uri, String s) throws IOException{
        if ( uri == null || uri.equals(URI.create("")) ) throw new IllegalArgumentException("the uri can't be empty");
        Document previousValue = hashTable.get(uri);
        Document document;
        if (array == null) {
            document = (Document) new DocumentImpl (uri, s);
            numberOfBytes += s.getBytes().length;
            if (hashTable.get(uri) != null) {
                for (String wordString : hashTable.get(uri).getWords()) {
                    trieImplementation.delete(wordString, document);
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, document);
            }
        }else{
            numberOfBytes += array.length;
            document = (Document) new DocumentImpl (uri, array);
        }
        if(hashTable.get(uri) != null && hashTable.get(uri).getDocumentBinaryData() != null){
            numberOfBytes -= hashTable.get(uri).getDocumentBinaryData().length;
        }else if (hashTable.get(uri) != null && hashTable.get(uri).getDocumentTxt() != null){
            numberOfBytes -= hashTable.get(uri).getDocumentTxt().getBytes().length;
        }
       return putDocumentUndoVersionPrivate(array, uri, previousValue, document);
    }

    private boolean putDocumentUndoVersionPrivate(byte[] array, URI uri, Document previousValue, Document document){
        if (hashTable.get(uri) == null) numberOfDocuments++;
        if (previousValue!= null) {
            previousValue.setLastUseTime(Integer.MIN_VALUE);
            //System.out.println(document.getLastUseTime() +"    444444444ERRRROROROROORORORORORORORORRO" + document);
            minHeap.reHeapify(previousValue);
            minHeap.remove();
        }
        //System.out.println(document + "       you are hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        document.setLastUseTime(System.nanoTime());
        //System.out.println(document + "     " + document.getLastUseTime());
        minHeap.insert(document);
        if(!inTooMuchMemory) tooMuchMemory();
        hashTable.put(uri, document);
        return true;
    }












    /*private boolean putBelowMaxBytes(byte[] array, String string){
        if ( array == null ) {
            if(maxNumberOFBytes != null && string.getBytes().length >= maxNumberOFBytes){
                return false;
            }
        }else{
            if(maxNumberOFBytes != null && array.length >= maxNumberOFBytes){
                return false;
            }
        }
        return true;
    }*/













    /**
     * Retrieve all documents whose text contains the given keyword.
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword) {
        //need trieimpl instance
        List<Document> searchList = trieImplementation.getAllSorted(keyword, (d1, d2) -> {
            if (d1.wordCount(keyword) < d2.wordCount(keyword)) {
                return 1;
            } else if (d1.wordCount(keyword) > d2.wordCount(keyword)) {
                return -1;
            } else {
                return 0;
            }
        });
        setTimeSearch(searchList);
        return searchList;
    }
    private void setTimeSearch(List<Document> searchList){
        long time = System.nanoTime();
        for (Document d : searchList) {
            d.setLastUseTime(time);
            minHeap.reHeapify(d);
        }
    }

    /**
     * Retrieve all documents whose text starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix){ // for each document for the first word and then the count of that word
        List<Document> searchList = trieImplementation.getAllWithPrefixSorted(keywordPrefix, (d1, d2) -> {
            int prefixInD1 = 0;
            int prefixInD2 = 0;
            int prefixLength = keywordPrefix.length();
            if(d1 != null) {
                for (String s : d1.getWords()) {
                    if (s.length() >= prefixLength){
                        if (s.substring(0, prefixLength).equals(keywordPrefix)) prefixInD1 += d1.wordCount(s);
                    }
                }
            }
            if(d2 != null) {
                for (String s : d2.getWords()) {
                    if (s.length() >= prefixLength) {
                        if (s.substring(0, prefixLength).equals(keywordPrefix)) prefixInD2 += d2.wordCount(s);
                    }
                }
            }
            //return Integer.compare(prefixInD2, prefixInD1);
            if (prefixInD1 < prefixInD2) {
                return 1;
            } else if (prefixInD1 > prefixInD2) {
                return -1;
            } else {
                return 0;
            }
        });
        setTimeSearch(searchList);
        return searchList;
    }

    /**
     * Completely remove any trace of any document which contains the given keyword
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAll(String keyword){
        Set<Document> documentDeletedSet = trieImplementation.deleteAll(keyword); //need to delete commandSet if fully emptied //and need to figure out where they were stored in commandset in order to put them back
        for (Object object : documentDeletedSet) {
            DocumentImpl d = (DocumentImpl) object;
            for (String wordString : d.getWords()) {
                trieImplementation.delete(wordString, (Document) d);
            }
        }
        Set<URI> uriDeletedSet = new HashSet<>();
        for (Object object : documentDeletedSet) {
            Document d = (Document) object;
            uriDeletedSet.add(d.getKey());
        }
        try{
            deleteAllPrivate(uriDeletedSet);
        }catch(IOException e){
            e.printStackTrace();
        }
        for (Document d : documentDeletedSet) {
            ////System.out.println(d + "       documentDeletedSet from deleteall");
            d.setLastUseTime(Integer.MIN_VALUE);
            //System.out.println(d.getLastUseTime() +"    5555555555ERRRROROROROORORORORORORORORRO" + d);
            minHeap.reHeapify(d);
            minHeap.remove();
        }
        return uriDeletedSet;
    }

    private void deleteAllPrivate(Set<URI> uriDeletedSet) throws IOException{
        ////System.out.println("deleteAllprivate");
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        //long time = System.nanoTime();
        for (URI uri : uriDeletedSet) {
            final String finalString = hashTable.get(uri).getDocumentTxt();
            Document deletedDocument = hashTable.get(uri);
            Function<URI, Boolean>  uriBoolean = uri1 -> {
                //deletedDocument.setLastUseTime(time);
                //minHeap.insert(deletedDocument);
                try{
                    return this.putDocumentUndoVersion(null, uri, finalString);
                }catch(IOException e){}
                return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object uriObject : uriDeletedSet) {
            URI uri = (URI) uriObject;
            if(hashTable.get(uri) != null && hashTable.get(uri).getDocumentBinaryData() != null){
                numberOfBytes -= hashTable.get(uri).getDocumentBinaryData().length;
            }else if (hashTable.get(uri) != null && hashTable.get(uri).getDocumentTxt() != null){
                numberOfBytes -= hashTable.get(uri).getDocumentTxt().getBytes().length;
            }
            numberOfDocuments--;
            hashTable.put(uri, null);
        }
    }
    

    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE INSENSITIVE.
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefix(String keywordPrefix){
        Set<Document> documentDeletedSet = trieImplementation.deleteAllWithPrefix(keywordPrefix);
        Set<URI> uriDeletedSet = new HashSet<>();
        for (Object object : documentDeletedSet) {

            DocumentImpl d = (DocumentImpl) object;
            uriDeletedSet.add(d.getKey());
            for (String wordString : d.getWords()) {
                 trieImplementation.delete(wordString, (Document) d);
             } 
        }
        for (Document d : documentDeletedSet) {
            d.setLastUseTime(Integer.MIN_VALUE);
            //System.out.println(d.getLastUseTime() +"    66666666666SERRRROROROROORORORORORORORORRO" + d);
            minHeap.reHeapify(d);
            minHeap.remove();
        }
        try{
            deleteAllPrefixPrivate(uriDeletedSet);
        }catch(IOException e){};
        return uriDeletedSet;
    }


    private void deleteAllPrefixPrivate(Set<URI> uriDeletedSet) throws IOException{
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        //long time = System.nanoTime();
        for (URI uri : uriDeletedSet) {
            Document deletedDocument = hashTable.get(uri);
            final String finalString = hashTable.get(uri).getDocumentTxt();
            Function<URI, Boolean>  uriBoolean = uri1 -> {
                //deletedDocument.setLastUseTime(time);
                //minHeap.insert(deletedDocument);
                try{
                    return this.putDocumentUndoVersion(null, uri, finalString);
                }catch(IOException e){};
                return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object object : uriDeletedSet) {
            URI d = (URI) object;
            if(hashTable.get(d) != null && hashTable.get(d).getDocumentBinaryData() != null){
                numberOfBytes -= hashTable.get(d).getDocumentBinaryData().length;
            }else if (hashTable.get(d) != null && hashTable.get(d).getDocumentTxt() != null){
                numberOfBytes -= hashTable.get(d).getDocumentTxt().getBytes().length;
            }
            numberOfDocuments--;
            hashTable.put(d, null);
        }
    }






     /**
     * set maximum number of documents that may be stored
     * @param limit
     */
    public void setMaxDocumentCount(int limit){
        ////System.out.println("MAXDOCUMENTCOUNT");
        maxNumberofDocuments = limit;
        tooMuchMemory();
    }

    /**
     * set maximum number of bytes of memory that may be used by all the documents in memory combined
     * @param limit
     */
    public void setMaxDocumentBytes(int limit){
        maxNumberOFBytes = limit;
        tooMuchMemory();
    }
}


