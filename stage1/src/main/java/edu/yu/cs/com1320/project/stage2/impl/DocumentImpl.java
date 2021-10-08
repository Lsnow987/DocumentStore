//package edu.yu.cs.com1320.project.stage1.impl;
package edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;
import java.net.URI;
import java.lang.*;
import java.net.*; 
import java.util.Objects;
import edu.yu.cs.com1320.project.stage1.Document;
import java.util.Arrays;

public class DocumentImpl implements Document{

    private boolean isTxt;
    private String txt;
    private URI uri;
    private byte[] binaryData;

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
}