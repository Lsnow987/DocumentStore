package edu.yu.cs.com1320.project.impl;
import java.util.List;
import java.util.*; 
import java.util.function.Supplier; 
import edu.yu.cs.com1320.project.Stack;


/**
 * @param <T>
 */
public class StackImpl<T> implements Stack<T>{
    //private Node<T>[] nodeArray = new Node[1];
    private int listSize = 0;
    //private T head;
    //private Node<T> elementNode;
    //private Node<T> headNode = null;
    LinkedList<T> stack = new LinkedList<T>(); 
    public StackImpl(){};

    /**
     * @param element object to add to the Stack
     */
    public void push(T element){
    	listSize++;
    	//Node<T> previousHeadNode = headNode;
    	//headNode = new Node(element);
    	//elementNode.
    	//head = element;
    	//Node<T> elementNode = new Node<T>(head);
    	if(previousHeadNode != null) {
    		//headNode.setNode(previousHeadNode);
    	   stack.addFirst(element);
    	}
    }

    /**
     * removes and returns element at the top of the stack
     * @return element at the top of the stack, null if the stack is empty
     */
    public T pop(){
        /*if (headNode ==null){
            return null;
        }*/
        listSize--;
    	//T returnValue = headNode.getKey();
    	//headNode = headNode.setNode(null);
    	//return returnValue;
        return stack.pollFirst();
    }

    /**
     *
     * @return the element at the top of the stack without removing it
     */
    public T peek(){
        /*if (headNode == null){
            return null;
        }*/
    	//return headNode.getKey();
    	return stack.peekFirst();
    	//return stack.element();
    }

    /**
     *
     * @return how many elements are currently in the stack
     */
    public int size(){
		return listSize;
    }








    /*private class Node<T>{
        private T k;
        //private Value v;
        private Node node;
        private Node(T k) {
            if (k == null){
                throw new IllegalArgumentException("the key can't be null");
            }
            this.k = k; 
            //this.v = v;
            this.node = null;
        }

        /*private Value getValue(){
            return this.v;
        } */

        /*private Value setValue(Value value){
            Value previous = this.v;
            this.v = value;
            return previous;
        }*//*
        private T setKey(T element) {
        	T previous = this.k;
        	this.k = element;
        	return previous;
        }

        private T getKey(){
            return this.k;
        } 

        private Node setNode(Node n){
        	Node previous = this.node;
            this.node = n;
            return previous;
        }

        private Node getNode(){
            return this.node;
        } 


    }*/
}