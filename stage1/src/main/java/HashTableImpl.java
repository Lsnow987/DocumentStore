package edu.yu.cs.com1320.project.stage1;
import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key,Value>{
	
	Node<Key,Value>[] nodeArray = new Node[5]; 
	//do i use .equals?????
	//when creating something new do i say hashTableImpl or just Hashtable???????????????

	/**
	* @param k the key whose value should be returned
	* @return the value that is stored in the HashTable for k, or null if there is no such key in the table
	*/
	@Override
	public Value get(Key k){
		int index = k.hashCode()%5;
		Node nodeInNodeArray = nodeArray[index];
		while ( nodeInNodeArray != null ){
			if ( k.equals( nodeInNodeArray.getKey() ) ){
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
		Node newNode = new Node(k,v);
		Node nodeInNodeArray = nodeArray[newNode.getKey().hashCode()%5];

		Value previousValue = null;
		//Node collisionNode = nodeInNodeArray.getNode()
		if( nodeInNodeArray != null && nodeInNodeArray.getKey().equals(k) ){// check if in third positiom
			previousValue = (Value) ( nodeInNodeArray.getValue() );
		}

		while( nodeInNodeArray.getNode() != null ){
			if( nodeInNodeArray.getKey().equals(k) ){
				previousValue = (Value) ( nodeInNodeArray.getValue() );
				break;
			}
			nodeInNodeArray = nodeInNodeArray.getNode();
		}
		
		if ( nodeInNodeArray == null ){
			nodeArray[newNode.getKey().hashCode()%5] = newNode; 
		}else{
			nodeInNodeArray.setNode(newNode);
		}
		return previousValue;
	}


	private class Node<Key, Value>{
		private Key k;
		private Value v;
		private Node<Key,Value> node;
    	private Node(Key k, Value v) {
        	this.k = k; 
        	this.v = v;
        	this.node = null;
    	}

    	private Value getValue(){
    		return this.v;
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


