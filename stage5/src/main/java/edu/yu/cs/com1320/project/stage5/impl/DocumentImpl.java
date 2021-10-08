package edu.yu.cs.com1320.project.stage5.impl;
import com.google.gson.internal.LinkedTreeMap;
import edu.yu.cs.com1320.project.stage5.Document;

import java.net.URI;
import java.lang.*;

//import edu.yu.cs.com1320.project.stage4.Document;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DocumentImpl implements Document{

    private boolean isTxt;
    private String txt;
    private URI uri;
    private byte[] binaryData;
    private LinkedTreeMap<String, Integer> wordCount = new LinkedTreeMap<>();
    private HashMap<DocumentImpl, Long> documentToTime = new HashMap<>();

    public DocumentImpl(URI uri, String txt, Map map){


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
        ////////System.out.println(txt + "       TTTTTTTTTTTTTXXXXXXXXXXXXXXXTTTTTTTTTTTTTTTTTTTTTTTTT");
        isTxt = true;
        setWordMap(map);
        setLastUseTime(System.nanoTime());
    }
    public DocumentImpl(URI uri, byte[] binaryData, Map map){


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
        setLastUseTime(System.nanoTime());
        setWordMap(map); //what to do if binary data?????
    }



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
        ////////System.out.println(txt + "       TTTTTTTTTTTTTXXXXXXXXXXXXXXXTTTTTTTTTTTTTTTTTTTTTTTTT");
        isTxt = true;
        documentWordCountMap(txt);
        setLastUseTime(System.nanoTime());
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
        setLastUseTime(System.nanoTime());
	}



	/**
     * @return content of text document
     */

    public String getDocumentTxt(){
        setLastUseTime(System.nanoTime());
        if (!isTxt){
            return null;
        }
        return this.txt;
    }

    /**
     * @return content of binary data document
     */

    public byte[] getDocumentBinaryData(){
        setLastUseTime(System.nanoTime());
        if (isTxt){
            return null;
        }
        return this.binaryData;
    }

    /**
     * @return URI which uniquely identifies this document
     */

    public URI getKey(){
        //setLastUseTime(System.nanoTime());
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
        setLastUseTime(System.nanoTime());
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
    public Set<String> getWords(){
        setLastUseTime(System.nanoTime());
        return (Set<String>) wordCount.keySet();
    }




     /**
     * return the last time this document was used, via put/get or via a search result
     * (for stage 4 of project)
     */
    public long getLastUseTime(){
        if (documentToTime==null){
            documentToTime = new HashMap<DocumentImpl, Long>();
        }
        return documentToTime.get(this); //what to return when not in the map????????
    }

    public void setLastUseTime(long timeInNanoseconds){
        if (documentToTime==null){
            documentToTime = new HashMap<DocumentImpl, Long>();
        }
        ////System.out.println("hi caplan document impl");
        ////System.out.println(this.getKey());
        documentToTime.put(this, timeInNanoseconds);
    }

    @Override
    public int compareTo(Document d){
        ////System.out.println(d);
        if (d== null || this.getLastUseTime() > d.getLastUseTime()){
            return 1;
        }else if(this.getLastUseTime() < d.getLastUseTime()){
            return -1;
        }else if (this.getLastUseTime() == d.getLastUseTime()){
            return 0;
        }
        return 999999;
    }



     /**
     * @return a copy of the word to count map so it can be serialized
     */
    public Map<String,Integer> getWordMap(){
        //LinkedTreeMap<String,Integer> copyOfWordCountMap = new LinkedTreeMap<String, Integer>(wordCount);
        return wordCount;
    }

    /**
     * This must set the word to count map during deserialization
     * @param wordMap
     */
    public void setWordMap(Map<String,Integer> wordMap){
        this.wordCount = (LinkedTreeMap<String, Integer>) wordMap;
    }
}