package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.*;
import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.stage5.impl.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class  BTreeImpl<Key extends Comparable<Key>, Value> implements BTree {
    //max children per B-tree node = MAX-1 (must be an even number and greater than 2)
    private final int MAX = 4;
    private Node root; //root of the B-tree
    private Node leftMostExternalNode;
    private int height; //height of the B-tree
    private int n; //number of k-Value pairs in the B-tree
    PersistenceManager pm;

    //B-tree node data type
    private static final class Node
    {
        private int entryCount; // number of entries
        private final Entry[] entries = new Entry[4]; // the array of children
        private Node next;
        private Node previous;

        // create a node with k entries
        private Node(int k)
        {
            this.entryCount = k;
        }

        private void setNext(Node next)
        {
            this.next = next;
        }
        private Node getNext()
        {
            return this.next;
        }
        private void setPrevious(Node previous)
        {
            this.previous = previous;
        }
        private Node getPrevious()
        {
            return this.previous;
        }
        private Entry[] getEntries()
        {
            return Arrays.copyOf(this.entries, this.entryCount);
        }

    }

    //internal nodes: only use k and child
    //external nodes: only use k and Value
    private static class Entry
    {
        private Comparable k;
        private Object v;
        private Node child;

        private Entry(Comparable k, Object v, Node child)
        {
            this.k = k;
            this.v = v;
            this.child = child;
        }
        private Object getValue()
        {
            return this.v;
        }
        private Comparable getKey()
        {
            return this.k;
        }
    }

    public BTreeImpl()
    {
        this.root = new Node(0);
        this.leftMostExternalNode = this.root;
        //this.put( ,Long.MIN_VALUE);
    }



   /* @Override
    public Object get(Comparable k) {
        return null;
    }

    @Override
    public Object put(Comparable k, Object v) {
        return null;
    }

    */
    @Override
    public void moveToDisk(Comparable k) throws Exception {
        Object x = get(k);
        this.put(k, null);
        pm.serialize(k, x);
    }

    @Override
    public void setPersistenceManager(PersistenceManager pm) {
        this.pm = pm;
    }













    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false}
     *         otherwise
     */

    /**
     * returns a list of all the entries in the Btree, ordered by k
     * @return
     */
    private ArrayList<Entry> getOrderedEntries()
    {
        Node current = this.leftMostExternalNode;
        ArrayList<Entry> entries = new ArrayList<>();
        while(current != null)
        {
            for(Entry e : current.getEntries())
            {
                if(e.v != null)
                {
                    entries.add(e);
                }
            }
            current = current.getNext();
        }
        return entries;
    }

    private Entry getMinEntry()
    {
        Node current = this.leftMostExternalNode;
        while(current != null)
        {
            for(Entry e : current.getEntries())
            {
                if(e.v != null)
                {
                    return e;
                }
            }
        }
        return null;
    }

    private Entry getMaxEntry()
    {
        ArrayList<Entry> entries = this.getOrderedEntries();
        return entries.get(entries.size()-1);
    }

    /**
     * Returns the Value associated with the given k.
     *
     * @param k the k
     * @return the Value associated with the given k if the k is in the
     *         symbol table and {@code null} if the k is not in the symbol
     *         table
     * @throws IllegalArgumentException if {@code k} is {@code null}
     */
    @Override
    public Object get(Comparable k) {
        {
            if (k == null) {
                throw new IllegalArgumentException("argument to get() is null");
            }
            ////System.out.println("you have succesfully made it into get     " + k);
            Entry entry = this.get(this.root, k, this.height);
            if (entry != null && entry.v != null) {
                return entry.v;
            }
            try {
                this.put(k ,pm.deserialize(k));
                ////System.out.println(k + "    we did not caught an exception");
            } catch (Exception e) {
                ////System.out.println(k + "    we caught an exception");
            }
            Entry entry1 = this.get(this.root, k, this.height);
            if (entry1 != null) {
                //////System.out.println("btree??");
                return entry1.v;
            }
            return null;
        }
    }

    private Entry get(Node currentNode, Comparable k, int height)
    {
        Entry[] entries = currentNode.entries;

        //current node is external (i.e. height == 0)
        if (height == 0)
        {
            for (int j = 0; j < currentNode.entryCount; j++)
            {
                if(isEqual(k, entries[j].k))
                {
                    //found desired k. Return its Value
                    return entries[j];
                }
            }
            //didn't find the k
            return null;
        }

        //current node is internal (height > 0)
        else
        {
            for (int j = 0; j < currentNode.entryCount; j++)
            {
                //if (we are at the last k in this node OR the k we
                //are looking for is less than the next k, i.e. the
                //desired k must be in the subtree below the current entry),
                //then recurse into the current entry’s child
                if (j + 1 == currentNode.entryCount || less(k, entries[j + 1].k))
                {
                    return this.get(entries[j].child, k, height - 1);
                }
            }
            //didn't find the k
            return null;
        }
    }

    /**
     * Inserts the k-Value pair into the symbol table, overwriting the old
     * Value with the new Value if the k is already in the symbol table. If
     * the Value is {@code null}, this effectively deletes the k from the
     * symbol table.
     *
     * @param k the k
     * @param v the Value
     * @throws IllegalArgumentException if {@code k} is {@code null}
     */
    @Override
    public Object put(Comparable k, Object v) {
        {

            //////System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "btree put");
            ////System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + "btree put");
            ////System.out.println(Thread.currentThread().getStackTrace()[3].getMethodName() + "btree put");
            if (k == null) {
                throw new IllegalArgumentException("argument k to put() is null");
            }
            //////System.out.println("btreeput");
            //if the k already exists in the b-tree, simply replace the Value

            try {
                //////System.out.println("hi caplan 2222 btreeimpl put");
                this.put(this.root, k, pm.deserialize(k), this.height);
            } catch (Exception e) {}

            Entry alreadyThere = this.get(this.root, k, this.height);
            if (alreadyThere != null) {
                alreadyThere.v = v;
                return v;
            }

            Node newNode = this.put(this.root, k, v, this.height);
            this.n++;
            if (newNode == null) {
                return null; //??????????????????????????????????????????????????????????
            }

            //split the root:
            //Create a new node to be the root.
            //Set the old root to be new root's first entry.
            //Set the node returned from the call to put to be new root's second entry
            Node newRoot = new Node(2);
            newRoot.entries[0] = new Entry(this.root.entries[0].k, null, this.root);
            newRoot.entries[1] = new Entry(newNode.entries[0].k, null, newNode);
            this.root = newRoot;
            //a split at the root always increases the tree height by 1
            this.height++;
        }
        return v;
    }

        /**
         *
         * @param currentNode
         * @param k
         * @param v
         * @param height
         * @return null if no new node was created (i.e. just added a new Entry into an existing node). If a new node was created due to the need to split, returns the new node
         */
        private Node put (Node currentNode, Comparable k, Object v,int height)
        {
            int j;
            Entry newEntry = new Entry(k, v, null);

            //external node
            if (height == 0) {
                //find index in currentNode’s entry[] to insert new entry
                //we look for k < entry.k since we want to leave j
                //pointing to the slot to insert the new entry, hence we want to find
                //the first entry in the current node that k is LESS THAN
                for (j = 0; j < currentNode.entryCount; j++) {
                    if (less(k, currentNode.entries[j].k)) {
                        break;
                    }
                }
            }

            // internal node
            else {
                //find index in node entry array to insert the new entry
                for (j = 0; j < currentNode.entryCount; j++) {
                    //if (we are at the last k in this node OR the k we
                    //are looking for is less than the next k, i.e. the
                    //desired k must be added to the subtree below the current entry),
                    //then do a recursive call to put on the current entry’s child
                    if ((j + 1 == currentNode.entryCount) || less(k, currentNode.entries[j + 1].k)) {
                        //increment j (j++) after the call so that a new entry created by a split
                        //will be inserted in the next slot
                        Node newNode = this.put(currentNode.entries[j++].child, k, v, height - 1);
                        if (newNode == null) {
                            return null;
                        }
                        //if the call to put returned a node, it means I need to add a new entry to
                        //the current node
                        newEntry.k = newNode.entries[0].k;
                        newEntry.v = null;
                        newEntry.child = newNode;
                        break;
                    }
                }
            }
            //shift entries over one place to make room for new entry
            for (int i = currentNode.entryCount; i > j; i--) {
                currentNode.entries[i] = currentNode.entries[i - 1];
            }
            //add new entry
            currentNode.entries[j] = newEntry;
            currentNode.entryCount++;
            if (currentNode.entryCount < this.MAX) {
                //no structural changes needed in the tree
                //so just return null
                return null;
            } else {
                //will have to create new entry in the parent due
                //to the split, so return the new node, which is
                //the node for which the new entry will be created
                return this.split(currentNode, height);
            }
        }

        /**
         * split node in half
         * @param currentNode
         * @return new node
         */
        private Node split (Node currentNode,int height)
        {
            Node newNode = new Node(this.MAX / 2);
            //by changing currentNode.entryCount, we will treat any Value
            //at index higher than the new currentNode.entryCount as if
            //it doesn't exist
            currentNode.entryCount = this.MAX / 2;
            //copy top half of h into t
            for (int j = 0; j < this.MAX / 2; j++) {
                newNode.entries[j] = currentNode.entries[this.MAX / 2 + j];
            }
            //external node
            if (height == 0) {
                newNode.setNext(currentNode.getNext());
                newNode.setPrevious(currentNode);
                currentNode.setNext(newNode);
            }
            return newNode;
        }

    // comparison functions - make Comparable instead of k to avoid casts
    private boolean less(Comparable k1, Comparable k2)
    {
        return k1.compareTo(k2) < 0;
    }

    private boolean isEqual(Comparable k1, Comparable k2)
    {
        return k1.compareTo(k2) == 0;
    }
    /**
     * @return the number of key-value pairs in this symbol table
     */
    private int size()
    {
        return this.n;
    }

    /**
     * @return the height of this B-tree
     */
    private int height()
    {
        return this.height;
    }

}