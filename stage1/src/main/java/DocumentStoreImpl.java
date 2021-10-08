package edu.yu.cs.com1320.project.stage1;
import edu.yu.cs.com1320.project.HashTable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class DocumentStoreImpl implements DocumentStore{
    private HashTable hashTable = new HashTableImpl<URI, Document>();
	
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
		if ( input == null) {
            Document documentToBeDeleted = this.getDocument(uri);
            if(documentToBeDeleted == null){
                return 0;
            }
            this.deleteDocument(uri);
            return documentToBeDeleted.hashCode();
        }

        byte[] array = input.readAllBytes(); 

        Document document = null;
        if ( format == DocumentFormat.TXT ) {
            String s = new String(array);
            document = new DocumentImpl (uri, s); 
        }else{
            document = new DocumentImpl (uri, array);
        }
        
        Document previousValue = getDocument(uri);
        hashTable.put(uri, document);
        if ( previousValue == null) {
            return 0;
        }
        return previousValue.hashCode();
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri){
        return (Document) hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     to delete means putting a null value
     */
     @Override
    public boolean deleteDocument(URI uri){
        if ( getDocument(uri) == null ){
            return false;
        }
        hashTable.put(uri, null);
        return true;
    }
}


