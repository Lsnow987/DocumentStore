package edu.yu.cs.com1320.project.stage3.impl;

import java.net.URI;
import java.lang.*;

import edu.yu.cs.com1320.project.stage3.Document;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class DocumentImpl implements Document{

    private boolean isTxt;
    private String txt;
    private URI uri;
    private byte[] binaryData;
    private HashMap<String, Integer> wordCount = new HashMap<>();

	public DocumentImpl(URI uri, String txt){

        URI uriEmpty = URI.create("");

		if ( uri == null ) {
			throw new IllegalArgumentException("the uri is null");
		}

        if (uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri is empty");
        }

        if ( txt == null || txt == "") {
            throw new IllegalArgumentException("there is no txt in the document");
        }
		this.uri = uri;
        this.txt = txt;
        isTxt = true;
        documentWordCountMap(txt);
	}

    private void documentWordCountMap(String text){ //could create another map from document to worcount 
        String noCharacterText = text.replaceAll( "[^a-zA-Z0-9\\s]", "");
        String lowercaseTxt = noCharacterText.toLowerCase();
        String[] splitString = lowercaseTxt.split("\\s+");

        //take out unnecessary characters
        for (String s : splitString) {
            int intCount = wordCount.getOrDefault(s, 0);
            wordCount.put(s, intCount + 1);
        }
    }

	public DocumentImpl(URI uri, byte[] binaryData){

        URI uriEmpty = URI.create("");

		if ( uri == null ) {
            throw new IllegalArgumentException("the uri is null");
        }

        if (uri.equals(uriEmpty)) {
            throw new IllegalArgumentException("the uri is empty");
        }

        if ( binaryData == null || binaryData.length == 0){
            throw new IllegalArgumentException("there is no txt in the document");
        }
		this.uri = uri;
		this.binaryData = binaryData;
        isTxt = false;
	}



	/**
     * @return content of text document
     */
    @Override
    public String getDocumentTxt(){
        if (!isTxt){
            return null;
        }
        return this.txt;
    }

    /**
     * @return content of binary data document
     */
    @Override
    public byte[] getDocumentBinaryData(){
        if (isTxt){
            return null;
        }
        return this.binaryData;
    }

    /**
     * @return URI which uniquely identifies this document
     */
    @Override
    public URI getKey(){
        return this.uri;
    }



	/*@Override
    public int hashCode() {
        //how do i do this????
        if ( isTxt ) {
            return Objects.hash(uri, txt);
        }
        return Objects.hash(uri, binaryData);
    }*/
    
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(binaryData);
        return result;
    }



    @Override
    public boolean equals(Object o) {
        if(this.hashCode() == o.hashCode()){
            return true;
        }
        return false;
    }




    /**
     * how many times does the given word appear in the document?
     * @param word
     * @return the number of times the given words appears in the document. If it's a binary document, return 0.
     */
    public int wordCount(String word){
        if (!isTxt) {
            return 0;
        }
        String noCharacterText = word.replaceAll( "[^a-zA-Z0-9\\s]", "");
        String lowercaseTxt = noCharacterText.toLowerCase();
        return wordCount.getOrDefault(lowercaseTxt, 0);
    }



    /**
     * @return all the words that appear in the document
     */
    @Override
    public Set<String> getWords(){
        return (Set<String>) wordCount.keySet();
    }
}