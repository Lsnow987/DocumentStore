package edu.yu.cs.com1320.project.stage1;
import edu.yu.cs.com1320.project.HashTable;

import java.net.URI;
import java.lang.*;
import java.net.*; 
import java.util.Objects;

public class DocumentImpl implements Document{

    private boolean isTxt;
    private String txt;
    private URI uri;
    private byte[] binaryData;

	public DocumentImpl(URI uri, String txt){
		if ( uri == null || txt == null ) {
			throw new IllegalArgumentException("one of the arguments is null");
		}
		this.uri = uri;
        this.txt = txt;
        isTxt = true;
	}

	public DocumentImpl(URI uri, byte[] binaryData){
		if ( uri == null || binaryData == null ) {
			throw new IllegalArgumentException("one of the arguments is null");
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
        return this.txt;
    }

    /**
     * @return content of binary data document
     */
    @Override
    public byte[] getDocumentBinaryData(){
        return this.binaryData;
    }

    /**
     * @return URI which uniquely identifies this document
     */
    @Override
    public URI getKey(){
        return this.uri;
    }



	@Override
    public int hashCode() {
        //how do i do this????
        if ( isTxt ) {
            return Objects.hash(uri, txt);
        }
        return Objects.hash(uri, binaryData);
    }



    @Override
    public boolean equals(Object o) {
        if(this.hashCode() == o.hashCode()){
            return true;
        }
        return false;
    }
}