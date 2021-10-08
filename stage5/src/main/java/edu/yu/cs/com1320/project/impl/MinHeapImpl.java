package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;

import java.util.NoSuchElementException;

public class MinHeapImpl<E extends Comparable<E>> extends MinHeap<E> {

    public MinHeapImpl() {
        this.elements = (E[])new Comparable[5];
    }
    
    /*public void printOutArray(){
        for (E element : this.elements) {
            ////System.out.println(element);
            ////System.out.println(this.elements[0] + " 0000000000000000000000000000000000000000000");
        }
    }*/

    public void reHeapify(E element) {
        ////System.out.println(element + "      element reheapified");
        int arrayIndex = getArrayIndex(element);
        downHeap(arrayIndex);
        upHeap(arrayIndex);
    }

    protected int getArrayIndex(E element) {
        ////System.out.println(element + "          getArrayIndex");
        if (!isEmpty()) {
            for (int i = 0; i<this.elements.length;  i++){
                //if (this.elements[i] != null) {
                    ////System.out.println("in get array index    " + this.elements[i] + "   " + ((DocumentStoreImpl.MinHeapObject) this.elements[i]).getUri());
                    if (this.elements[i] != null && element.equals(this.elements[i])) {
                        ////System.out.println("true iiiiii in get array index    " + this.elements[i] + ((DocumentStoreImpl.MinHeapObject) this.elements[i]).getDocument().getDocumentTxt());
                        return i;
                    }
                //}
            }
        }
        throw new NoSuchElementException("the element isn't here");
    }

    protected void doubleArraySize() {
        E[] newEArray = (E[])new Comparable[this.elements.length*2];
        System.arraycopy(this.elements, 0, newEArray, 0, this.elements.length);
        this.elements = newEArray;
    }
}