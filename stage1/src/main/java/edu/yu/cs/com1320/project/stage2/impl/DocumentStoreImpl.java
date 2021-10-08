//package edu.yu.cs.com1320.project.stage1.impl;
package edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Function;

import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;

public class DocumentStoreImpl implements DocumentStore{
    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<URI, Document>();
	private StackImpl<Command> stackImplementation = new StackImpl<Command>();
	public DocumentStoreImpl(){};

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
        if ( uri == null || uri.equals(uriEmpty) ) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
		if ( input == null) {
            Document documentToBeDeleted = this.getDocument(uri);
            this.deleteDocument(uri);
            if(documentToBeDeleted == null){
               return 0;
            }
            return documentToBeDeleted.hashCode();
        }
        byte[] array = input.readAllBytes(); 
        Document document = null;
        if ( format == DocumentFormat.TXT  ) {
            String s = new String(array);
            document = new DocumentImpl (uri, s); 
        }else{
            document = new DocumentImpl (uri, array);
        } 
        Document previousValue = getDocument(uri);
        putDocumentCommand(uri, previousValue, format, array);
        hashTable.put(uri, document);
        if ( previousValue == null) {
            return 0;
        }
        return previousValue.hashCode();
    }



    private void putDocumentCommand(URI uri, Document previousValue, DocumentFormat format, byte[] array){
        Function<URI, Boolean> uriBoolean;
        getDocument(uri);
        if (previousValue==null){
            uriBoolean = uri1 -> {
                return this.deleteDocumentUndoVersion(uri);
            };
        }else if (format == DocumentFormat.TXT){
            uriBoolean = uri1 -> {
                try {
                    this.putDocumentUndoVersion(null, uri, new String(array));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }; 
        }else{
            uriBoolean = uri1 -> {
                try {
                    this.putDocumentUndoVersion(array, uri, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }; 
        }
        Command putCommand = new Command(uri, uriBoolean);
        stackImplementation.push(putCommand);
    }



    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri){
        URI uriEmpty = URI.create("");
        if (uri == null || uri == uriEmpty) {
            throw new IllegalArgumentException("the uri can't be empty");
        }

        //System.out.println(hashTable.get(uri));
        return (Document) hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     to delete means putting a null value
     */
     @Override
    public boolean deleteDocument(URI uri){

        URI uriEmpty = URI.create("");
        
        if (uri == null || uri == uriEmpty) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        String initialString = "";
        //InputStream targetStream;
        byte[] array = null;
        if (getDocument(uri) == null){
            Function<URI, Boolean> uriBooleanDoesNothing = uri1 -> {
                return true;
            };
            Command emptyDeleteCommand = new Command(uri, uriBooleanDoesNothing);
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
        
        //if ( getDocument(uri) == null ){
         //   return false;
        //}
        hashTable.put(uri, null);
        return true;
    }

    private void deleteDocumentSetterAndCommand(String initialString, byte[] array, URI uri) throws IOException{
        if(getDocument(uri).getDocumentTxt() != null){
            initialString = getDocument(uri).getDocumentTxt();
            //targetStream = new ByteArrayInputStream(initialString.getBytes());
        }else{
            array = getDocument(uri).getDocumentBinaryData();
            //targetStream = new ByteArrayInputStream(array);
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
        Command deleteCommand = new Command(uri, uriBoolean);
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
        Command c = stackImplementation.pop();
        c.undo();
        //this.deleteDocumentUndoVersion(c.getUri());
    }



    /**
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    public void undo(URI uri) throws IllegalStateException{ //is this legal?
        StackImpl<Command> stackUndoImplementation = new StackImpl<Command>();
        
        Command c = stackImplementation.pop();
        stackUndoImplementation.push(c);
        while(!c.getUri().equals(uri) && stackImplementation.peek()!=null) {
        	stackUndoImplementation.push(c);
        	c = stackImplementation.pop();
        }
        if (!c.getUri().equals(uri)){
            throw new IllegalStateException("there are no actions on the command stack for the given URI");
        }
        c.undo();
        while(stackUndoImplementation.peek() != null){
            Command reverseCommand = stackUndoImplementation.pop();
            stackImplementation.push(reverseCommand);
        }
        //if c is a put command do private delete so undo not added to stack, 
        //if delete do private put

    }



    private boolean deleteDocumentUndoVersion(URI uri){

        URI uriEmpty = URI.create("");
        if (uri == null || uri == uriEmpty) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        hashTable.put(uri, null);
        return true;
    }
   
    private boolean putDocumentUndoVersion(byte[] array, URI uri, String s) throws IOException{
        URI uriEmpty = URI.create("");
        if ( uri == null || uri.equals(uriEmpty) ) {
            throw new IllegalArgumentException("the uri can't be empty");
        }
        /*if ( input == null) {
            Document documentToBeDeleted = this.getDocument(uri);
            if(documentToBeDeleted == null){
                return 0;
            }
            this.deleteDocument(uri);
            return documentToBeDeleted.hashCode();
        }*/
        //byte[] array = input.readAllBytes(); 
        Document document = null;
        //if ( format == DocumentFormat.TXT  ) {
        if (array == null) {
            //String s = new String(array);
            document = new DocumentImpl (uri, s); 
        }else{
            document = new DocumentImpl (uri, array);
        } 
        hashTable.put(uri, document);
        return true;
    }


}


