package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key,Value>{

	public HashTableImpl(){};
	private Node<Key,Value>[] nodeArray = new Node[5]; 
	private double numberOfItemsInArray = 0;
	//do i use .equals?????
	//when creating something new do i say hashTableImpl or just Hashtable???????????????

	/**
	* @param k the key whose value should be returned
	* @return the value that is stored in the HashTable for k, or null if there is no such key in the table
	*/
	@Override
	public Value get(Key k){
		if (k == null){
			throw new IllegalArgumentException("the key can't be null");
		}
		int index = k.hashCode()%5;
		if (index < 0){
			index*=-1;
		}
		Node nodeInNodeArray = nodeArray[index];
		//System.out.println("before null while" + k);
		while ( nodeInNodeArray != null ){
			//System.out.println("in null while" + k);
			if ( k.equals( nodeInNodeArray.getKey() ) ){
				//System.out.println("in if" +k);
				return (Value) nodeInNodeArray.getValue();
			}
			nodeInNodeArray = nodeInNodeArray.getNode();
		}
		return null;

		/*for (Node n : nodeArray) {
			if ( n.getKey() == k ) {
				return (Value)( n.getValue() );
			}else if ( n.getNode() != null ){
				this.get( (Key) ( n.getNode().getKey() ) );
			}
		}
		return null;*/
	}








/**
	* @param k the key at which to store the value
	* @param v the value to store
	* @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
	*/
	@Override
	public Value put(Key k, Value v){
		if ( numberOfItemsInArray/nodeArray.length >= .75){ 
			Node<Key,Value>[] newNodeArray = new Node[nodeArray.length*2];
			Node<Key,Value>[] copyNodeArray = new Node[nodeArray.length];
			System.arraycopy(nodeArray, 0, copyNodeArray, 0, nodeArray.length);
			nodeArray = newNodeArray;
			for (Node node : copyNodeArray) {
				while( node != null /*&& node.getNode() != null*/ ){ //checking if keys had stored a value in any of the nodes and saving that stored value
					this.put( (Key) node.getKey(), (Value) node.getValue());
					node = node.getNode();
				}
			}
		}
		if (k == null) throw new IllegalArgumentException("the key can't be null");
		int hashCode = k.hashCode()%5;
		if (hashCode < 0) hashCode*=-1;
		putPrivate(hashCode);
	}


	private Value putPrivate(int hashCode){
		Node nodeInNodeArray = nodeArray[hashCode];
		Value previousValue = null;
		if ( nodeInNodeArray == null ){
			nodeArray[hashCode] = new Node(k,v); 
			numberOfItemsInArray++;
			return previousValue;
		}
		if( nodeInNodeArray.getKey().equals(k) ){ //checking if this key had stored a value and saving that stored value
			return (Value) ( nodeInNodeArray.setValue(v) ); //previousValue
		}
		if(nodeInNodeArray.getNode() == null){
			nodeInNodeArray.setNode(new Node(k,v));
			numberOfItemsInArray++;
			return previousValue;
		}
		while( nodeInNodeArray != null && nodeInNodeArray.getNode() != null ){ //checking if keys had stored a value in any of the nodes and saving that stored value
			if( nodeInNodeArray.getKey().equals(k)){
				return (Value) ( nodeInNodeArray.setValue(v) ); //previousValue
			}
			nodeInNodeArray = nodeInNodeArray.getNode();
		}
		if( nodeInNodeArray.getKey().equals(k) ){ //checking if this key had stored a value and saving that stored value
			return (Value) ( nodeInNodeArray.setValue(v) ); //previousValue
		}
		nodeInNodeArray.setNode(new Node(k,v));
		numberOfItemsInArray++;
		return previousValue;
	}

		//System.out.println(nodeInNodeArray.getValue() + "nodeInNodeArray666666666");
		//System.out.println(v +"newValue66666666666");
		//System.out.println(previousValue + "previousValue666666666");




	/**
	* @param k the key at which to store the value
	* @param v the value to store
	* @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
	*
	@Override
	public Value put(Key k, Value v){
		Node newNode = new Node(k,v);
		//nodeInNodeArray.setValue(v);
		//Node copyNode = new Node(newNode.getKey(), newNode.getValue());
		//System.out.println(copyNode.getValue());
		Node nodeInNodeArray = nodeArray[newNode.getKey().hashCode()%5];
		//Node nodeInNodeArrayFinal = nodeArray[newNode.getKey().hashCode()%5];

		
		
		if( nodeInNodeArray != null && nodeInNodeArray.getKey().equals(k) ){ //checking if this key had stored a value and saving that stored value
			previousValue = (Value) ( nodeInNodeArray.getValue() );
			//nodeInNodeArray.setValue(v);
			//System.out.println(copyNode.getValue() + "aaaa");
		}
		
		if ( nodeInNodeArray == null ){
			nodeArray[newNode.getKey().hashCode()%5] = newNode; 
			//System.out.println(copyNode.getValue() + "bbbbbb");

			return previousValue;
		}

		while( nodeInNodeArray.getNode() != null ){
			if( nodeInNodeArray.getKey().equals(k)){
				previousValue = (Value) ( nodeInNodeArray.getValue() );
				//System.out.println(copyNode.getValue() + "cccccc");
				break;
			}
			nodeInNodeArray = nodeInNodeArray.getNode();
			//System.out.println(copyNode.getValue() + "ddddddddd");
		}
		nodeInNodeArray.setNode(newNode);
		//System.out.println(v);
		//System.out.println(copyNode.getValue() +"eeeeeeee");
		//nodeInNodeArrayFinal.setValue(copyNode.getValue());
		return previousValue;
	}*/

	private class Node<Key, Value>{
		private Key k;
		private Value v;
		private Node<Key,Value> node;
    	private Node(Key k, Value v) {
    		if (k == null){
				throw new IllegalArgumentException("the key can't be null");
			}
        	this.k = k; 
        	this.v = v;
        	this.node = null;
    	}

    	private Value getValue(){
    		return this.v;
    	} 

    	private Value setValue(Value value){
    		Value previous = this.v;
    		this.v = value;
    		return previous;
    	}

    	private Key getKey(){
    		return this.k;
    	} 

    	private void setNode(Node n){
    		this.node = n;
    	}

    	private Node getNode(){
    		return this.node;
    	} 


    }
}


