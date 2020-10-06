package app;

import app.UndirectedGraph.Node;

/**
 * Class HashClass will be a class containing all the nescessary methods to
 * create a hashtable to be used to store Person objects based on username given
 * 
 * @author Matthew Davenport
 * @author Isaac Kim
 * @author Marley Willyoung
 * @author Victoria Nguyen
 * @author Jahnae Reese
 * 
 *
 */
public class HashClass<K, V> {
    class HashElement<K, V> implements Comparable<HashElement<K, V>>{
        K key;
        V value;

        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        } 

        public int compareTo(HashElement<K, V> o) {
            return (((Comparable<V>)this.value).compareTo(o.value));
        }  
    }
    
    static int PRIME = 7;
    static int numElements;
    static int tableSize;
    double maxLoadFactor;
    static Person[] hashArray;
    static Node[] hashList;


    public int getNumElements()
    {
        return numElements;
    }

    public HashClass(int tableSize) {
        this.tableSize = tableSize;
        maxLoadFactor = 0.75;
        numElements = 0;
        hashArray = new Person[tableSize];
        // store in table
        for (int i = 0; i < tableSize; i++) {
            hashArray[i] = new Person<>();
        }
    }

    public HashClass() {
        this.tableSize = 1000;
        maxLoadFactor = 0.75;
        numElements = 0;
        hashList = new Node[tableSize];
        // store in table
        for (int i = 0; i < tableSize; i++) {
            hashList[i] = new Node();
        }
    }
    // HASH FUNCTION

    public static int createKey(String userName) {
        int hash = 0;
        for (int i = 0; i < userName.length(); i++) {
            hash = hash * 31 + userName.charAt(i);
        }

        
        hash = hash & 0x7FFFFFFF;
        hash = hash % tableSize;

        return hash; // result from addition of ASCII characters
    }

    public static int hashTwo(int hash) {
        return (PRIME - (hash % PRIME));
    }

    public static Person<Object> getValue(String usernameInp) {
        Person<Object> profileSearch = new Person<>();
        int userKey = createKey(usernameInp);
        profileSearch = hashArray[userKey];

        if (usernameInp != profileSearch.getUserName()) {
            userKey = validateKey(usernameInp);
            profileSearch = hashArray[userKey];
            return profileSearch;
        }

        
        if (profileSearch == null)
            System.out.println("No profile exists under that username");

        return profileSearch;//returning person object
    }


    public static boolean addToHashTable(int ID, Person<Object> tempPerson) {
        int tempID = createKey(tempPerson.getUserName());
        if (hashArray[tempID].getUserName() != tempPerson.getUserName() && hashArray[ID].getUserName() != null) {
            int hashT = hashTwo(ID);
            hashArray[hashT] = tempPerson;
            tempPerson.setPersonID(hashT);
            numElements++;
            return true;
            
        }
        else
        {
            //int hash = createKey(tempPerson.getUserName());
            hashArray[ID] = tempPerson;
            tempPerson.setPersonID(ID);
            numElements++;
            return true;
        }
    }

    public static Person<Object> removeFromHashTable(int ID, Person<Object> personToDelete) {
        ID = validateKey(personToDelete.getUserName());
        
        personToDelete = hashArray[ID];
        hashArray[ID] = null;

        return personToDelete;        

    }
 
   
    public static int validateKey(String stringInput) {
        int ID = createKey(stringInput);
        Person<Object> temp = hashArray[ID]; 
        if (!stringInput.equals(temp.getUserName())) {
            ID = HashClass.hashTwo(ID);   
        }
        return ID;

    } 
    /**
	 * Checks if the profile being searched exists
	 * 
	 * @param String stringInput is a username being searched
	 * @return boolean value true if hashList is not empty
	 */
	public <T> boolean containsKey(int personID) {
		return hashList[personID] != null;
	}
    
    public boolean isEmpty() {
        return numElements != 0;
    }

    public LList<Node> get(int personID) {
        LList<Node> profileSearch = new LList<Node>();
        
        profileSearch = hashList[personID].getNeighbours();
        
        if (profileSearch == null)
            System.out.println("This person has no friends.");

        return profileSearch;//returning list of friends 
    }

}


    
    
