package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore{
    private final HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
	private final StackImpl<Undoable> stackImplementation = new StackImpl<>();
    private final TrieImpl<Document> trieImplementation = new TrieImpl(); // need v parameters???
    
	public DocumentStoreImpl(){}

	/**
     * the two document formats supported by this document store.
     * Note that TXT means plain text, i.e. a String.
     */
   // private enum DocumentFormat{
       // TXT,BINARY
   // };
    /**
     * @param input the document being put
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the 
     !!!!!!!!!!!!!!!!!!!!!!!!!!String version of the previous doc. 
     If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     */
    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException{
        URI uriEmpty = URI.create("");
        if ( uri == null || uri.equals(uriEmpty) || format == null ) throw new IllegalArgumentException("the arguments can't be null");
		if ( input == null) {
            Document documentToBeDeleted = this.getDocument(uri);
            this.deleteDocument(uri);
            if(documentToBeDeleted == null){
               return 0;
            }
            return documentToBeDeleted.hashCode();
        }
        Document document = putDocumentPrivate(input, format, uri);
        Document previousValue = getDocument(uri);
        putDocumentCommand(uri, previousValue);
        hashTable.put(uri, document);
        if ( previousValue == null) {
            return 0;
        }
        return previousValue.hashCode();
    }

    private Document putDocumentPrivate(InputStream input, DocumentFormat format, URI uri) throws IOException{
        byte[] array = input.readAllBytes();
        DocumentImpl document;
        if ( format == DocumentFormat.TXT  ) {
            String s = new String(array);
            document = new DocumentImpl (uri, s);

            if (hashTable.get(uri) != null) {
                for (String wordString : hashTable.get(uri).getWords()) {
                    trieImplementation.delete(wordString, hashTable.get(uri));
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, document);
            }
        }else{
            document = new DocumentImpl (uri, array);
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
        return hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     to delete means putting a null value
     */
    @Override
    public boolean deleteDocument(URI uri){
        URI uriEmpty = URI.create("");

        if (uri == null || uri.equals(uriEmpty)) throw new IllegalArgumentException("the uri can't be empty");
        String initialString = "";
        //InputStream targetStream;
        byte[] array = null;
        if (getDocument(uri) == null){
            Function<URI, Boolean> uriBooleanDoesNothing = uri1 -> true;
            GenericCommand emptyDeleteCommand = new GenericCommand(uri, uriBooleanDoesNothing);
            stackImplementation.push(emptyDeleteCommand);
            return false;
        }else{
            try {
			    deleteDocumentSetterAndCommand(initialString, array, uri);
		    } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    }
        }
        for (String s : getDocument(uri).getWords()) {
            trieImplementation.delete(s, getDocument(uri));
        }
        hashTable.put(uri, null);
        return true;
    }

    private void deleteDocumentSetterAndCommand(String initialString, byte[] array, URI uri) throws IOException{
        if(getDocument(uri).getDocumentTxt() != null){
            initialString = getDocument(uri).getDocumentTxt();
        }else{
            array = getDocument(uri).getDocumentBinaryData();
        }
        final String finalString = initialString;
        final byte[] arrayFinal = array;
        Function<URI, Boolean>  uriBoolean = uri1 -> {

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
                trieImplementation.delete(wordString, getDocument(uri));
            }
        }

        hashTable.put(uri, null);
        return true;
    }

    private boolean putDocumentUndoVersion(byte[] array, URI uri, String s) throws IOException{
        URI uriEmpty = URI.create("");
        if ( uri == null || uri.equals(uriEmpty) ) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        Document document;
        if (array == null) {
            document = new DocumentImpl (uri, s);
            if (hashTable.get(uri) != null) {
                for (String wordString : hashTable.get(uri).getWords()) {
                    trieImplementation.delete(wordString, document);
                }
            }
            for (String wordString : document.getWords()) {
                trieImplementation.put(wordString, document);
            }
        }else{
            document = new DocumentImpl (uri, array);
        }
        hashTable.put(uri, document);
        return true;
    }













    /**
     * Retrieve all documents whose text contains the given keyword.
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword) {
        //need trieimpl instance
        return trieImplementation.getAllSorted(keyword, (d1, d2) -> {
            if (d1.wordCount(keyword) < d2.wordCount(keyword)) {
                return 1;
            } else if (d1.wordCount(keyword) > d2.wordCount(keyword)) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    /**
     * Retrieve all documents whose text starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE INSENSITIVE.
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix){ // for each document for the first word and then the count of that word
        return trieImplementation.getAllWithPrefixSorted(keywordPrefix, (d1, d2) -> {
            int prefixInD1 = 0;
            int prefixInD2 = 0;
            int prefixLength = keywordPrefix.length();
            if(d1 != null) {
                for (String s : d1.getWords()) {
                    if (s.substring(0, prefixLength).equals(keywordPrefix)) {
                        prefixInD1 += d1.wordCount(s);
                    }
                }
            }
            if(d2 != null) {
                for (String s : d2.getWords()) {
                    if (s.substring(0, prefixLength).equals(keywordPrefix)) {
                        prefixInD2 += d2.wordCount(s);
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
                trieImplementation.delete(wordString, d);
            }
        }
        Set<URI> uriDeletedSet = new HashSet<>();
        for (Object object : documentDeletedSet) {
            Document d = (Document) object;
            uriDeletedSet.add(d.getKey());
        }
        deleteAllPrivate(uriDeletedSet);
        return uriDeletedSet;
    }
    private void deleteAllPrivate(Set<URI> uriDeletedSet){
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        for (URI uri : uriDeletedSet) {
            final String finalString = getDocument(uri).getDocumentTxt();
            Function<URI, Boolean>  uriBoolean = uri1 -> {
                try {
                    return this.putDocumentUndoVersion(null, uri, finalString);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                    return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object uriObject : uriDeletedSet) {
            URI uri = (URI) uriObject;
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
                 trieImplementation.delete(wordString, d);
             } 
        }
        deleteAllPrefixPrivate(uriDeletedSet);
        return uriDeletedSet;
    }


    private void deleteAllPrefixPrivate(Set<URI> uriDeletedSet){
        CommandSet<Undoable> setOfCommands = new CommandSet<>();
        for (URI uri : uriDeletedSet) {
            final String finalString = getDocument(uri).getDocumentTxt();
            Function<URI, Boolean>  uriBoolean = uri1 -> {
                try {
                    return this.putDocumentUndoVersion(null, uri, finalString);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                    return true;
            };
            GenericCommand<Undoable> deleteCommand = new GenericCommand(uri, uriBoolean);
            setOfCommands.addCommand(deleteCommand);
        }
        stackImplementation.push(setOfCommands);
        for (Object object : uriDeletedSet) {
            URI d = (URI) object;
            hashTable.put(d, null);
        }
    }
}


