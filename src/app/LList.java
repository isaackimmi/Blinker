package app;
/*
    @author Isaac Kim
    04/30/2020 (Lists - Exercise 3 - Linked List)

    Simple implementation of an LList class. 
*/

public class LList<T> implements ListInterface<T> {
    private Node firstNode; // Reference to first node of chain
    private int numberOfEntries;

    // Default ConstructorL
    public LList() {
        initializeDataFields();
    }

    // Initializes the class's data fields to indicate an empty list.
    private void initializeDataFields() {
        firstNode = null;
        numberOfEntries = 0;
    } // end initializeDataFields3

    // Returns a reference to the node at a given position.
    // Precondition: The chain is not empty;
    // 1 <= givenPosition <= numberOfEntries.
    private Node getNodeAt(int givenPosition) {
        assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= numberOfEntries);
        Node currentNode = firstNode;

        // Traverse the chain to locate the desired node
        // (skipped if givenPosition is 1)
        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();

        assert currentNode != null;

        return currentNode;
    } // end getNodeAt

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

    // Adds an entry to the end of the list.
    @Override
    public void add(T newEntry) {
        Node newNode = new Node(newEntry);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            Node lastNode = getNodeAt(numberOfEntries);
            lastNode.setNextNode(newNode);
        }
        numberOfEntries++;
    }

    // Adds an entry at the client-specified position.
    @Override
    public void add(int newPosition, T newEntry) {
        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            Node newNode = new Node(newEntry);

            if (newPosition == 1) {
                newNode.setNextNode(firstNode);
                firstNode = newNode;
            } else {
                Node nodeBefore = getNodeAt(newPosition - 1);
                Node nodeAfter = nodeBefore.getNextNode();
                newNode.setNextNode(nodeAfter);
                nodeBefore.setNextNode(newNode);
            }
            numberOfEntries++;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
        }
    }

    // Removes an entry at a client-specified position.
    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            if (givenPosition == 1) {
                result = firstNode.getData();
                firstNode = firstNode.getNextNode();
            } else {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                Node nodeToRemove = nodeBefore.getNextNode();
                result = nodeToRemove.getData();
                Node nodeAfter = nodeToRemove.getNextNode();
                nodeBefore.setNextNode(nodeAfter);

            }
            numberOfEntries--;
            return result;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
        }
    }

    // Clears the entire linked list.
    @Override
    public void clear() {
        initializeDataFields();

    }

    // Replaces an entry with a client-specified data intput.
    @Override
    public T replace(int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            Node desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.getData();
            desiredNode.setData(newEntry);
            return originalEntry;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
    }

    // Returns an an entry at a client-specified position.
    @Override
    public T getEntry(int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            return getNodeAt(givenPosition).getData();
        } else
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");

    }

    // Checks if the linked list contains a client-specified entry.
    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.getData())) {
                found = true;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        return found;
    }

    // OUR OWN REMOVE METHOD!
    // remove friend from list based on what the user inputs
    public T removeEntry(T anEntry) {
        
        int position = 0;
        boolean found = false;
        Node currentNode = firstNode;
        Node nodeBefore;
        Node nodeAfter;
        Node nodeToRemove;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.getData())) {
                found = true;
                if(currentNode == firstNode) {
                    firstNode = currentNode.getNextNode();
                    currentNode = null;
                    numberOfEntries--;
                    return anEntry;
                    
                }
            } else {
                currentNode = currentNode.getNextNode();
            }
            position++;
        }
        if(found == true) {
            nodeToRemove = currentNode;
            nodeBefore = getNodeAt(position - 1);
            nodeAfter = nodeToRemove.getNextNode();
            nodeBefore.setNextNode(nodeAfter);
            numberOfEntries--;
            return anEntry;
        }
        else {
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
        }

    }

    // Returns the length of the linked list.
    @Override
    public int getLength() {
        return numberOfEntries;
    }

    // Checks if the linked list is empty.
    @Override
    public boolean isEmpty() {
        boolean result;
        if (numberOfEntries == 0) {
            assert firstNode == null;
            result = true;
        } else {
            assert firstNode != null;
            result = false;
        }
        return result;
    }

    // Creates an array with the entries in the linked list.
    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];

        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null)) {
            result[index] = currentNode.getData();
            currentNode = currentNode.getNextNode();
            index++;
        }
        return result;
    }
}