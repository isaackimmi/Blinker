/*
    @author Isaac Kim
    05/06/2020 (Queues - Exercise 1 - LinkedQueue)

    Simple implementation of a LinkedQueue.
*/

// This file was created as a Java project on VS Code which is why 
// "package app;" is commented out.
package app;


public final class LinkedQueue<T> {
    private Node firstNode;
    private Node lastNode;

    // Default Constructor
    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
    }

    // enqueue: adds an entry to the back of the queue.
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);

        if (isEmpty()) {
            firstNode = newNode;
        } else
            lastNode.setNextNode(newNode);

        lastNode = newNode;
    }

    // dequeue: removes the first entry of the queue.
    public T dequeue() {
        T front = getFront();
        assert firstNode != null;

        firstNode.setData(null);

        firstNode = firstNode.getNextNode();

        if (firstNode == null) {
            lastNode = null;
        }
        return front;
    }

    // getFront: returns the first entry of the linked queue.
    public T getFront() {
        if (isEmpty()) {
            throw new EmptyQueueException();
        } else
            return firstNode.getData();

    }

    // isEmpty: checks if the linked queue is empty.
    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    }

    // clear: removes/clears all the entries in the linked queue.
    public void clear() {
        firstNode = null;
        lastNode = null;

    }

    // Node class
    private class Node {
        private T data;
        private Node next;

        // Constructor
        private Node(T data) {
            this(data, null);
        }

        // Overloaded Constructor
        private Node(T nodeData, Node nodePtr) {
            data = nodeData;
            next = nodePtr;
        }

        // Setters
        public void setData(T input) {
            data = input;
        }

        public void setNextNode(Node nodePtr) {
            next = nodePtr;
        }

        // Getters
        public T getData() {
            return data;
        }

        public Node getNextNode() {
            return next;
        }
    }

}