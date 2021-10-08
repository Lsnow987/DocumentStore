package edu.yu.cs.com1320.project.stage5.impl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;

//import edu.yu.cs.com1320.project.impl.HashTableImpl;

public class DocumentStoreImpl implements DocumentStore {
    //private final HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
    private final BTreeImpl<URI, Document> bTree = new BTreeImpl();
    private DocumentPersistanceManager persistenceManager;
    private final StackImpl<Undoable> stackImplementation = new StackImpl<>();
    private final TrieImpl<URI> trieImplementation = new TrieImpl(); // need v parameters???
    private MinHeapImpl<MinHeapObject> minHeap = new MinHeapImpl<>();
    private Integer maxNumberofDocuments = null;
    private Integer maxNumberOFBytes = null;
    private int numberOfDocuments = 0;
    private int numberOfBytes = 0;
    private long firstNanoTime;
    private Set<URI> uriOnDiskSet = new HashSet<>();
    //boolean inTooMuchMemory = false;

    //deal with setting time of commandset 

    //public DocumentStoreImpl(){}

    public DocumentStoreImpl() {
        this.persistenceManager = new DocumentPersistanceManager(new File(System.getProperty("user.dir")));
        bTree.setPersistenceManager(this.persistenceManager);
    }
    public DocumentStoreImpl(File baseDir) {
        this.persistenceManager = new DocumentPersistanceManager(baseDir);
        bTree.setPersistenceManager(this.persistenceManager);
    }

    /**
     * the two document formats supported by this document store.
     * Note that TXT means plain text, i.e. a String.
     */
    // private enum DocumentFormat{
    // TXT,BINARY
    // };

    /**
     * //* @param input the document being put
     *
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the
     * !!!!!!!!!!!!!!!!!!!!!!!!!!String version of the previous doc.
     * If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     */

    private Set<URI> tooMuchMemory() {
        ////System.out.println("tooMuchMemory");
        ////System.out.println(maxNumberofDocuments + "      maxnumberofdocuments");
        ////System.out.println(numberOfDocuments + "      numberofdocuments");
        ////System.out.println(maxNumberOFBytes+ "         maxnumberofbytes");
        ////System.out.println(numberOfBytes + "         numberofbytes");
        //CommandSet setOfCommands = new CommandSet<>();
        Set<URI> uriRemovedSet = new HashSet<>();
        while ((maxNumberofDocuments != null && numberOfDocuments > maxNumberofDocuments) || (maxNumberOFBytes != null && numberOfBytes > maxNumberOFBytes)) {
            MinHeapObject m = minHeap.remove();//need delete oldest thing and then make sure enough memory
            URI uri = m.getUri();
            uriRemovedSet.add(uri);
            ////System.out.println( uri +"   too Much Memory");
            Document d = m.getDocument();
            int bytes = 0;
            //if(d!= null) { ????
                numberOfDocuments--;
                if (d.getDocumentBinaryData() != null) {
                    bytes = d.getDocumentBinaryData().length;
                    numberOfBytes -= bytes;
                } else if (d.getDocumentTxt() != null) {
                    bytes = d.getDocumentTxt().getBytes().length;
                    numberOfBytes -= bytes;
                }
            //}

            //////////System.out.println(bTree.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            //////////System.out.println(bTree.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            ////////System.out.println(document.getLastUseTime() +"    ERRRROROROROORORORORORORORORRO" + document);
            //////////System.out.println(bTree.get(document.getKey()) + "NOTNULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            //////////System.out.println("STACKOVERFLOWERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
            try {
                //////System.out.println("before move to disk");
                bTree.moveToDisk(uri);
                uriOnDiskSet.add(uri);
                //////System.out.println("AFTER move to disk");
            } catch (Exception e) {}
            /*Function<URI, Boolean> uriBoolean;
            int finalBytes = bytes;
            uriBoolean = uri1 -> {
                Document dUndo = (Document) bTree.get(uri);
                dUndo.setLastUseTime(System.nanoTime());
                minHeap.insert(new MinHeapObject(dUndo.getKey()));
                ////System.out.println(numberOfDocuments);
                numberOfDocuments++;
                ////System.out.println(numberOfBytes);
                numberOfBytes += finalBytes;
                ////System.out.println(numberOfBytes);
                ////System.out.println(numberOfDocuments);
                ////System.out.println(finalBytes + "   helloworld");
                uriOnDiskSet.remove(uri);
                tooMuchMemory();
                return true;
            };*/
            //GenericCommand diskCommand = new GenericCommand(uri, uriBoolean);
            //setOfCommands.addCommand(diskCommand);
            //////System.out.println("end of while in too much memory");
        }
        //}
        //inTooMuchMemory = false; - commented out due to while true!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //////////System.out.println("266666666666666666666666666666666666666666666666666666666666STACKOVERFLOWERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        //////System.out.println("end of too much memory");
        ////System.out.println(numberOfDocuments + "   helloworld2222");
        return uriRemovedSet;
    }


    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
        if (uri == null || uri.equals(URI.create("")) || format == null || uri.toString().endsWith(File.separator) || uri.toString().endsWith("/") || uri.toString().endsWith("\\"))
            throw new IllegalArgumentException("the arguments can't be null and the uri cant end with a slash");
        if (input == null) {
            Document documentToBeDeleted = (Document) ((Document) bTree.get(uri));
            this.deleteDocument(uri);
            if (documentToBeDeleted == null) {
                return 0;
            }
            return documentToBeDeleted.hashCode();
        }
        byte[] array = input.readAllBytes();
        //if(!putBelowMaxBytes(array, format)) return 0; //???????? what should i return
        Document previousValue = (Document) getDocument(uri);
        ////////System.out.println(previousValue + "  previousValue");
        Document document = putDocumentPrivate(array, format, uri, previousValue);

        document.setLastUseTime(System.nanoTime());
        bTree.put(uri, document);
        if (previousValue == null || uriOnDiskSet.contains(uri)) {
            numberOfDocuments++;
            minHeap.insert(new MinHeapObject(document.getKey()));
        } else {
            minHeap.reHeapify(new MinHeapObject(uri));
            //try {
                /*previousValue.setLastUseTime(Integer.MIN_VALUE);
                System.out.println(previousValue.getKey());
                System.out.println(previousValue.getLastUseTime());

                MinHeapObject m = minHeap.remove();
                MinHeapObject m1 = minHeap.remove();
            MinHeapObject m2 = minHeap.remove();
            MinHeapObject m3 = minHeap.remove();
            MinHeapObject m3 = minHeap.remove();
              //System.out.println(previousValue.getLastUseTime());
                System.out.println(m.getUri().toString() + "   plsgod");
                System.out.println(m1.getUri().toString() + "   plsgod");
            System.out.println(m2.getUri().toString() + "   plsgod");
            System.out.println(m3.getUri().toString() + "   plsgod");
            //}catch(Exception e){}*/
        }


        GenericCommand gc = putDocumentCommand(uri, previousValue, tooMuchMemory());
        stackImplementation.push(gc);
        //CommandSet cs = tooMuchMemory();
         ///////////////////////
        //try{
        //minHeap.reHeapify(document);
        //////////System.out.println(document + "      reheapified");
        //}catch(NoSuchElementException e){
        //////////System.out.println(document + "          inserted");
        //}
        tooMuchMemory();
        if (previousValue == null) return 0;
        return previousValue.hashCode();
    }

    /*private void newCommand(GenericCommand gc, Set<URI>, Document d) {
        int bytes= 0;
        if (d.getDocumentBinaryData() != null) {
            bytes = d.getDocumentBinaryData().length;
        } else {
            bytes = d.getDocumentTxt().getBytes().length;
        }
        /*if (cs.size() != 0  && (maxNumberofDocuments == null || maxNumberofDocuments != 0) && (maxNumberOFBytes == null || bytes<= maxNumberOFBytes)) {
            //try{
            cs.addCommand(gc);
            //}catch(IllegalArgumentException e){}
            stackImplementation.push(cs);
        } else {
            stackImplementation.push(gc);
        }*/
    //}

    /*private boolean putBelowMaxBytes(byte[] array, DocumentFormat format) throws IOException{
        //////////System.out.println("putBelowMaxBytes");
        if ( format == DocumentFormat.TXT  ) {
            String s = new String(array);
            if(maxNumberOFBytes != null && s.getBytes().length >= maxNumberOFBytes){
                //////////System.out.println("putBelowMaxBytes...........555555555555555555");
                return false;
            }
        }else{
            if(maxNumberOFBytes != null && array.length >= maxNumberOFBytes){
                //////////System.out.println("putBelowMaxBytes1111111111111111111111");
                return false;
            }
        }
        return true;
    }*/

    private Document putDocumentPrivate(byte[] array, DocumentFormat format, URI uri, Document previousValue) throws IOException {
        Document document;
        if (format == DocumentFormat.TXT) {
            String s = new String(array);
            //////////System.out.println(s + "         sssssssssssssssss" + s.getBytes().length + array.length);
            //////////System.out.println(numberOfBytes + "    numberofbytes before");
            numberOfBytes += s.getBytes().length;
            //////////System.out.println(numberOfBytes + "    numberofbytes after");
            document = (Document) new DocumentImpl(uri, s);

            if (((Document) bTree.get(uri)) != null) {
                for (String wordString : ((Document) ((Document) bTree.get(uri))).getWords()) {
                    trieImplementation.delete(wordString, uri);
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, uri);
            }
        } else {
            numberOfBytes += array.length;
            document = (Document) new DocumentImpl(uri, array);
        }
        if (previousValue != null && previousValue.getDocumentBinaryData() != null) {
            numberOfBytes -= previousValue.getDocumentBinaryData().length;
        } else if (previousValue != null && previousValue.getDocumentTxt() != null) {
            numberOfBytes -= previousValue.getDocumentTxt().getBytes().length;
        }
        return document;
    }

    private GenericCommand putDocumentCommand(URI uri, Document previousValue, Set<URI> uriSet) {
        Function<URI, Boolean> uriBoolean;
        long nanoTime = System.nanoTime();
        if (uriSet != null) {
            for (URI u : uriSet) {
                Document d = getDocument(u);
                if(d!= null) d.setLastUseTime(nanoTime);
            }
        }
        if (previousValue == null) {
            uriBoolean = uri1 -> this.deleteDocumentUndoVersion(uri);
        } else if (previousValue.getDocumentBinaryData() == null) {
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
        } else {
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
        //stackImplementation.push(putCommand);
        return putCommand;
    }


    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri) {
        ////System.out.println("before too much memory");
        tooMuchMemory();
        URI uriEmpty = URI.create("");
        if (uri == null || uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        uriOnDiskSet.remove(uri);
        Document documentToReturn = (Document) bTree.get(uri);
        if (documentToReturn != null) {
            //////////System.out.println( ((Document)bTree.get(uri)) + "      get document set nano time");
            documentToReturn.setLastUseTime(System.nanoTime());
            //////System.out.println("get document reheapify");
            try{
                minHeap.reHeapify(new MinHeapObject(uri));
            }catch(Exception e){
                numberOfDocuments++;
                int x = 0;
                int y = 0;
                if (documentToReturn.getDocumentTxt() != null) {
                    String initialString = documentToReturn.getDocumentTxt();
                    x =  initialString.getBytes().length;
                    numberOfBytes += x;
                } else {
                    byte[] array = documentToReturn.getDocumentBinaryData();
                    y = array.length;
                    numberOfBytes += y;
                }
               /*if ( ( maxNumberofDocuments != null && numberOfDocuments > maxNumberofDocuments) || (maxNumberOFBytes != null && numberOfBytes > maxNumberOFBytes)){
                   try {
                       persistenceManager.serialize(uri, (Document) bTree.get(uri));
                       numberOfBytes-=(y+x);
                       numberOfDocuments--;
                       ////System.out.println("pls god - document store impl");
                       return documentToReturn;
                   } catch (IOException ioException) {
                       ioException.printStackTrace();
                   }
               }*/
                minHeap.insert(new MinHeapObject(uri));
                ////System.out.println("22222before too much memory");
                tooMuchMemory();
            }

        }
        return documentToReturn;
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     * to delete means putting a null value
     */
    @Override
    public boolean deleteDocument(URI uri) {
        //////////System.out.println(getDocument(uri) + "      wrong uri???");

        if (uri == null || uri.equals(URI.create(""))) throw new IllegalArgumentException("the uri can't be empty");
        String initialString = "";
        //InputStream targetStream;
        byte[] array = null;
        if (((Document) bTree.get(uri)) == null) {
            Function<URI, Boolean> uriBooleanDoesNothing = uri1 -> true;
            GenericCommand emptyDeleteCommand = new GenericCommand(uri, uriBooleanDoesNothing);
            stackImplementation.push(emptyDeleteCommand);
            return false;
        } else {
            //////////System.out.println(numberOfDocuments + "    numberOfDocuments in delete before --");
            numberOfDocuments--;
            //////////System.out.println(numberOfDocuments + "    numberOfDocuments in delete AFTER --");
            try {
                deleteDocumentSetterAndCommand(initialString, array, uri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (String s : ((Document) ((Document) bTree.get(uri))).getWords()) {
            trieImplementation.delete(s, uri);
        }
        //////////System.out.println(firstNanoTime + "    firstNanoTime    " + System.nanoTime());
        //////////System.out.println(getDocument(uri) + "         deleted document");
        ((Document) ((Document) bTree.get(uri))).setLastUseTime(Integer.MIN_VALUE);
        //////System.out.println(((Document) ((Document) bTree.get(uri))).getLastUseTime() + "last use tike   " + uri);
        ////////System.out.println(((Document)((Document)bTree.get(uri))).getLastUseTime() +"    22222222ERRRROROROROORORORORORORORORRO" + ((Document)bTree.get(uri)));
        //////System.out.println("last use time");
        try {
            minHeap.reHeapify(new MinHeapObject(uri));
            minHeap.remove();
        }catch(NoSuchElementException e){}
        try {
            bTree.put(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void deleteDocumentSetterAndCommand(String initialString, byte[] array, URI uri) throws IOException {
        if (((Document) (((Document) bTree.get(uri)))).getDocumentTxt() != null) {
            initialString = ((Document) ((Document) bTree.get(uri))).getDocumentTxt();
            numberOfBytes -= initialString.getBytes().length;
        } else {
            array = ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData();
            numberOfBytes -= array.length;
        }
        final String finalString = initialString;
        final byte[] arrayFinal = array;
        Document insertDoc = (Document) ((Document) bTree.get(uri));
        Function<URI, Boolean> uriBoolean = uri1 -> {
            //////////System.out.println("insert doc deleteDocumentSetterAndCommand");
            //minHeap.insert(insertDoc);
            //insertDoc.setLastUseTime(System.nanoTime());
            try {
                if (finalString.equals("")) {
                    return this.putDocumentUndoVersion(arrayFinal, uri, null);
                } else {
                    return this.putDocumentUndoVersion(null, uri, finalString);
                }
            } catch (IOException e) {
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
     *
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException {
        if (stackImplementation.size() == 0) {
            throw new IllegalStateException("the command stack is empty");
        }
        Undoable c = stackImplementation.pop();
        c.undo();
        tooMuchMemory();
    }


    /**
     * undo the last put or delete that was done with the given URI as its key
     *
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */


    public void undo(URI uri) throws IllegalStateException {
        StackImpl<Undoable> stackUndoImplementation = new StackImpl<>();
        Undoable c = stackImplementation.pop();
        GenericCommand genericCommandFromSet = null;
        if (c == null) throw new IllegalStateException("there are no actions on the command stack");
        boolean commandSetShouldLeaveStack = false;
        while (c instanceof CommandSet || !c.equals(new GenericCommand<>(uri, null)) /*&& stackImplementation.peek()!=null*/) {
            if (c instanceof CommandSet) {
                if (((CommandSet<URI>) c).size() <= 1) commandSetShouldLeaveStack = true;
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

            if (stackImplementation.peek() != null && !c.equals(new GenericCommand(uri, null))) {
                c = stackImplementation.pop();
            } else {
                break;
            }
        }
        undoSpecificUriPrivate(c, genericCommandFromSet, uri, stackUndoImplementation);
    }

    private void undoSpecificUriPrivate(Undoable c, GenericCommand genericCommandFromSet, URI uri, StackImpl<Undoable> stackUndoImplementation) {
        if (c instanceof GenericCommand && c.equals(new GenericCommand(uri, null))) {
            c.undo();
        }
        if (genericCommandFromSet != null) {
            ((CommandSet<URI>) c).undo(uri/*genericCommandFromSet*/);
        }
        while (stackUndoImplementation != null && stackUndoImplementation.peek() != null) {
            Undoable reverseCommand = stackUndoImplementation.pop();
            stackImplementation.push(reverseCommand);
        }
        if (!c.equals(new GenericCommand<URI>(uri, null)) && genericCommandFromSet == null) {
            throw new IllegalStateException("there are no actions on the command stack for the given URI");
        }
    }


    private boolean deleteDocumentUndoVersion(URI uri) {
        URI uriEmpty = URI.create("");
        if (uri == null || uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        if (((Document) bTree.get(uri)) != null) {
            for (String wordString : ((Document) ((Document) bTree.get(uri))).getWords()) {
                trieImplementation.delete(wordString, uri);
            }
            if (!uriOnDiskSet.contains(uri)) {
                numberOfDocuments--;

                if (((Document) ((Document) bTree.get(uri))).getDocumentBinaryData() != null) {
                    numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData().length;
                } else if (((Document) ((Document) bTree.get(uri))).getDocumentTxt() != null) {
                    numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentTxt().getBytes().length;
                }
            }
        }
        ((Document) bTree.get(uri)).setLastUseTime(Integer.MIN_VALUE);
        ////////System.out.println(((Document)((Document)bTree.get(uri))).getLastUseTime() +"    333333333ERRRROROROROORORORORORORORORRO" + ((Document)bTree.get(uri)));
        //try{
        boolean x = true;
        try {
            minHeap.reHeapify(new MinHeapObject(uri));
        }catch(Exception e){
            x= false;
        }
        if(x) minHeap.remove();
        //}catch(NoSuchElementException e){};
        try {
            bTree.put(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean putDocumentUndoVersion(byte[] array, URI uri, String s) throws IOException {
        if (uri == null || uri.equals(URI.create(""))) throw new IllegalArgumentException("the uri can't be empty");
        Document previousValue = ((Document) bTree.get(uri));
        Document document;
        if (array == null) {
            document = (Document) new DocumentImpl(uri, s);
            numberOfBytes += s.getBytes().length;
            if (((Document) bTree.get(uri)) != null) {
                for (String wordString : ((Document) ((Document) bTree.get(uri))).getWords()) {
                    trieImplementation.delete(wordString, uri);
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, uri);
            }
        } else {
            numberOfBytes += array.length;
            document = (Document) new DocumentImpl(uri, array);
        }
        if (((Document) bTree.get(uri)) != null && ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData() != null) {
            numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData().length;
        } else if (((Document) bTree.get(uri)) != null && ((Document) ((Document) bTree.get(uri))).getDocumentTxt() != null) {
            numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentTxt().getBytes().length;
        }
        //////System.out.println(previousValue + "     return putDocumentUndoVersionPrivate(array, uri, previousValue, document);");
        return putDocumentUndoVersionPrivate(array, uri, previousValue, document);
    }

    private boolean putDocumentUndoVersionPrivate(byte[] array, URI uri, Document previousValue, Document document) {
        if (((Document) bTree.get(uri)) == null) numberOfDocuments++;
        bTree.put(uri, document);
        if (previousValue == null) {
            minHeap.insert(new MinHeapObject(uri));
            document.setLastUseTime(System.nanoTime());
            minHeap.reHeapify(new MinHeapObject(uri));
            //previousValue.setLastUseTime(Integer.MIN_VALUE);
            //////System.out.println(document.getLastUseTime() +"    444444444ERRRROROROROORORORORORORORORRO" + document + document.getDocumentTxt());
            //minHeap.reHeapify(previousValue);
            //minHeap.remove();
        }
        ////////System.out.println(document + "       you are hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        document.setLastUseTime(System.nanoTime());
        ////////System.out.println(document + "     " + document.getLastUseTime());
        //////System.out.println(new MinHeapObject(uri).getDocument().getDocumentTxt() + "is the document null???");
        if (previousValue != null) minHeap.reHeapify(new MinHeapObject(uri));
        tooMuchMemory();
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
     *
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword) {
        //need trieimpl instance
        List<URI> searchList = trieImplementation.getAllSorted(keyword, (d1, d2) -> {
            if (((Document) bTree.get(d1)).wordCount(keyword) < ((Document) bTree.get(d2)).wordCount(keyword)) {
                return 1;
            } else if (((Document) bTree.get(d1)).wordCount(keyword) > ((Document) bTree.get(d2)).wordCount(keyword)) {
                return -1;
            } else {
                return 0;
            }
        });
        List<Document> documentList = new ArrayList<>();
        for (URI u : searchList) {

            Document d = (Document) getDocument(u);
            documentList.add(d);
        }
        setTimeSearch(documentList);
        return documentList;
    }

    private void setTimeSearch(List<Document> searchList) {
        long time = System.nanoTime();
        for (Document d : searchList) {
            d.setLastUseTime(time);
            minHeap.reHeapify(new MinHeapObject(d.getKey()));
        }
    }

    /**
     * Retrieve all documents whose text starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE INSENSITIVE.
     *
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix) { // for each document for the first word and then the count of that word
        List<URI> searchList = trieImplementation.getAllWithPrefixSorted(keywordPrefix, (d1, d2) -> {
            int prefixInD1 = 0;
            int prefixInD2 = 0;
            int prefixLength = keywordPrefix.length();
            if (d1 != null) {
                for (String s : ((Document) bTree.get(d1)).getWords()) {
                    if (s.length() >= prefixLength) {
                        if (s.substring(0, prefixLength).equals(keywordPrefix))
                            prefixInD1 += ((Document) bTree.get(d1)).wordCount(s);
                    }
                }
            }
            if (d2 != null) {
                for (String s : ((Document) bTree.get(d2)).getWords()) {
                    if (s.length() >= prefixLength) {
                        if (s.substring(0, prefixLength).equals(keywordPrefix))
                            prefixInD2 += ((Document) bTree.get(d2)).wordCount(s);
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
        List<Document> documentList = new ArrayList<>();
        for (URI u : searchList) {
            Document d = (Document) getDocument(u);
            documentList.add(d);
        }
        setTimeSearch(documentList);
        return documentList;
    }

    /**
     * Completely remove any trace of any document which contains the given keyword
     *
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAll(String keyword) {
        Set<URI> uriDeletedSet = trieImplementation.deleteAll(keyword); //need to delete commandSet if fully emptied //and need to figure out where they were stored in commandset in order to put them back
        for (Object object : uriDeletedSet) {
            URI u = (URI) object;
            for (String wordString : ((Document) bTree.get(u)).getWords()) {
                trieImplementation.delete(wordString, u);
            }
        }
        Set<Document> documentDeletedSet = new HashSet<>();
        for (Object object : uriDeletedSet) {
            URI uri = (URI) object;
            documentDeletedSet.add((Document) bTree.get(uri));
        }
        for (Document d : documentDeletedSet) {
            //////////System.out.println(d + "       documentDeletedSet from deleteall");
            d.setLastUseTime(Integer.MIN_VALUE);
            ////////System.out.println(d.getLastUseTime() +"    5555555555ERRRROROROROORORORORORORORORRO" + d);
            //////System.out.println(d.getKey() + "d.getKey in delete all");
            try {
                minHeap.reHeapify(new MinHeapObject(d.getKey()));
                minHeap.remove();
            }catch(NoSuchElementException e){}
        }
        try {
            deleteAllPrivate(uriDeletedSet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uriDeletedSet;
    }

    private void deleteAllPrivate(Set<URI> uriDeletedSet) throws IOException {
        //////////System.out.println("deleteAllprivate");
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        //long time = System.nanoTime();
        for (URI uri : uriDeletedSet) {
            final String finalString = ((Document) ((Document) bTree.get(uri))).getDocumentTxt();
            Document deletedDocument = ((Document) bTree.get(uri));
            Function<URI, Boolean> uriBoolean = uri1 -> {
                //deletedDocument.setLastUseTime(time);
                //minHeap.insert(deletedDocument);
                try {
                    return this.putDocumentUndoVersion(null, uri, finalString);
                } catch (IOException e) {
                }
                return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object uriObject : uriDeletedSet) {
            URI uri = (URI) uriObject;
            if (!uriOnDiskSet.contains(uri)) {
                if (((Document) bTree.get(uri)) != null && ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData() != null) {
                    numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentBinaryData().length;
                } else if (((Document) bTree.get(uri)) != null && ((Document) ((Document) bTree.get(uri))).getDocumentTxt() != null) {
                    numberOfBytes -= ((Document) ((Document) bTree.get(uri))).getDocumentTxt().getBytes().length;
                }
                numberOfDocuments--;
            }
            try {
                bTree.put(uri, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE INSENSITIVE.
     *
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        Set<URI> uriDeletedSet = trieImplementation.deleteAllWithPrefix(keywordPrefix);
        Set<Document> documentDeletedSet = new HashSet<>();
        for (Object object : uriDeletedSet) {
            URI uri = (URI) object;
            documentDeletedSet.add((Document) bTree.get(uri));
            for (String wordString : ((Document) bTree.get(uri)).getWords()) {
                trieImplementation.delete(wordString, uri);
            }
        }
        for (Document d : documentDeletedSet) {
            d.setLastUseTime(Integer.MIN_VALUE);
            ////////System.out.println(d.getLastUseTime() +"    66666666666SERRRROROROROORORORORORORORORRO" + d);
            try {
                minHeap.reHeapify(new MinHeapObject(d.getKey()));
                minHeap.remove();
            }catch(NoSuchElementException e){}
        }
        try {
            deleteAllPrefixPrivate(uriDeletedSet);
        } catch (IOException e) {
        }
        ;
        return uriDeletedSet;
    }


    private void deleteAllPrefixPrivate(Set<URI> uriDeletedSet) throws IOException {
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        //long time = System.nanoTime();
        for (URI uri : uriDeletedSet) {
            Document deletedDocument = ((Document) bTree.get(uri));
            final String finalString = ((Document) ((Document) bTree.get(uri))).getDocumentTxt();
            Function<URI, Boolean> uriBoolean = uri1 -> {
                //deletedDocument.setLastUseTime(time);
                //minHeap.insert(deletedDocument);
                try {
                    return this.putDocumentUndoVersion(null, uri, finalString);
                } catch (IOException e) {
                }
                ;
                return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object object : uriDeletedSet) {
            URI d = (URI) object;
            if (!uriOnDiskSet.contains(d)) {
                if (bTree.get(d) != null && ((Document) bTree.get(d)).getDocumentBinaryData() != null) {
                    numberOfBytes -= ((Document) bTree.get(d)).getDocumentBinaryData().length;
                } else if (bTree.get(d) != null && ((Document) bTree.get(d)).getDocumentTxt() != null) {
                    numberOfBytes -= ((Document) bTree.get(d)).getDocumentTxt().getBytes().length;
                }
                numberOfDocuments--;
            }
            try {
                bTree.put(d, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * set maximum number of documents that may be stored
     *
     * @param limit
     */
    public void setMaxDocumentCount(int limit) {
        //////////System.out.println("MAXDOCUMENTCOUNT");
        maxNumberofDocuments = limit;
        tooMuchMemory();
    }

    /**
     * set maximum number of bytes of memory that may be used by all the documents in memory combined
     *
     * @param limit
     */
    public void setMaxDocumentBytes(int limit) {
        maxNumberOFBytes = limit;
        tooMuchMemory();
    }

    public class MinHeapObject implements Comparable<MinHeapObject> {
        private URI u;
        private MinHeapObject(URI u){
            this. u = u;
        }

        public URI getUri(){
            return this.u;
        }

        public Document getDocument(){
            return (Document)bTree.get(u);
        }


        @Override
        public int compareTo(DocumentStoreImpl.MinHeapObject o) {
            URI otherUri = o.getUri(); //will this bring them all back to ,emory????
            Document d1 = (Document)bTree.get(u);
            Document d2 = (Document) bTree.get(otherUri);
            ////////System.out.println( d1 +  "  notnullnot    "  + d2 );
            if(d1 == null){
                return -1;
            }
            return d1.compareTo(d2);
        }
        @Override
        public boolean equals(Object o) {

            // If the object is compared with itself then return true
            if (o == this) {
                return true;
            }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
            if (!(o instanceof MinHeapObject)) {
                return false;
            }

            // typecast o to Complex so that we can compare data members
            MinHeapObject c = (MinHeapObject) o;

            // Compare the data members and return accordingly
            if (!c.getUri().equals(this.getUri())){
                return false;
            }
            if(c.getDocument() == null || this.getDocument() == null){
                return false;
            }
            if (!c.getDocument().equals(this.getDocument())){
                return false;
            }
            return true;
        }
    }
}


