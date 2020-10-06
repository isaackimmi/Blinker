package app;



/**LoginMenu holds method main. Will create Person's to be added to the hashtable based on user registering accounts
 * Can also login to pre made accounts
 * Will call displayUserProfile in Person.java once profile has been created or logged in to. 
 * Loops continously based on user's choice 
 * 
 * @author Isaac Kim
 *
 */
import java.util.Scanner;

import app.UndirectedGraph.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.Random;

public class LoginMenu {

    public static void main(String[] args) throws IOException{
		
        
		@SuppressWarnings("rawtypes")
		//creates a hashtable to store Person Objects. Person Objects will have a Linked Lists of strings that hold a friends list
		//Friends list will access those friends based on the key asssociated with the friend's username by going into the hashtable
		HashClass<Integer, Person> hashtable = new HashClass<Integer, Person>(1000);
		UndirectedGraph<Integer, LList<Node>> hashlist = new UndirectedGraph<Integer, LList<Node>>();
        
        //Create 5 test accounts to test functionality of the program
		Person<Object> testPerson1 = new Person<Object>("qwertyui", "John", "Tavner", "I like to fish", "password", "ONLINE");
		Person<Object> testPerson2 = new Person<Object>("qwertyiu", "Cristiano", "Ronaldo", "I like soccer.", "password", "BUSY");
		Person<Object> testPerson3 = new Person<Object>("poiuytre", "Thanos", "DidNothingWrong", "Perfectly balanced like as things should be", "password", "OFFLINE");
		Person<Object> testPerson4 = new Person<Object>("pepepepe", "iron", "man", "I like potts.", "password", "ONLINE");
		Person<Object> testPerson5 = new Person<Object>("lkjhgfds", "Darth", "Vader", "Come to the dark side", "password", "BUSY");

		//create 5 keys based on usernames of the test accounts
		int person1ID = HashClass.createKey(testPerson1.getUserName());
		int person2ID = HashClass.createKey(testPerson2.getUserName());
		int person3ID = HashClass.createKey(testPerson3.getUserName());
		int person4ID = HashClass.createKey(testPerson4.getUserName());
		int person5ID = HashClass.createKey(testPerson5.getUserName());

		//Now add all 5 test users to the hashtable in the position based on ID
		boolean person1created = HashClass.addToHashTable(person1ID, testPerson1);
        boolean person2created = HashClass.addToHashTable(person2ID, testPerson2);
        boolean person3created = HashClass.addToHashTable(person3ID, testPerson3);
		boolean person4created = HashClass.addToHashTable(person4ID, testPerson4);
		boolean person5created = HashClass.addToHashTable(person5ID, testPerson5);

		Node person1 = new Node (person1ID);
		Node person2 = new Node (person2ID);
		Node person3 = new Node (person3ID);
		Node person4 = new Node (person4ID);
		Node person5 = new Node (person5ID);
		
		
		// adding the nodes to the hashgraph
		UndirectedGraph.addVertex(person1);
		UndirectedGraph.addVertex(person2);
		UndirectedGraph.addVertex(person3);
		UndirectedGraph.addVertex(person4);
		UndirectedGraph.addVertex(person5);

		// adding edges between pre-loaded profiles
		//person 1
		UndirectedGraph.addEdge(person1, person2);
		UndirectedGraph.addEdge(person1, person5);
		//person 2
		UndirectedGraph.addEdge(person2, person4);
		UndirectedGraph.addEdge(person2, person3);
		//person 3
		UndirectedGraph.addEdge(person3, person1);
		//person 4
		UndirectedGraph.addEdge(person4, person1);
		UndirectedGraph.addEdge(person4, person2);

		// adding friends that weren't added yet
		person1.addNeighbors(person1, person2);
		person1.addNeighbors(person1, person5);

		person2.addNeighbors(person2, person4);
		person2.addNeighbors(person2, person3);

		person3.addNeighbors(person3, person1);

		person4.addNeighbors(person4, person1);
		person4.addNeighbors(person4, person2);

		// testing if edges and vertices were added correctly
		System.out.println(hashlist.getNumberOfEdges());
		System.out.println(hashlist.getNumberOfVertices());
		

		/**
		 * create graph of all profiles 
		 * 
		 * Hachclass<Integer, List<T>> 
		 * create a hashtable IDENTICAL to the hashtable above.
		 * WE DONT need new hash values
		 * add RONALDO to the hashtable 
		 * then, add JOHN + THANOS as his edges.
		 * 
		 * 
		 * 
		 */



        int loginOption = 0;	//user choice to either  login or register an account
		String usernameInp = null; //input given by user to login username
		String passwordInp = null; //input given by user to validate account password
		Scanner userInp = new Scanner(System.in); 
		Boolean quit = false; 
		String loginUser = null;

		/*
		try {
            File userAndPass = new File("UsernamesAndPasswords.txt");
            Scanner fileReader = new Scanner(userAndPass);
        } catch (FileNotFoundException e) {
            System.out.println("Error creating file");
            e.printStackTrace();
		}*/

		int choice = 0; // choice to hold user input to either loop through bookface to register new account/login
						// or quit program

		//do while loop for iterating through the whole program after deleting an account
        do {
			System.out.println("       -------------------------------");
    		System.out.println("       |                             |");
    		System.out.println("       |     Welcome to BookFace!    |");
    		System.out.println("       |                             |");
			System.out.println("       -------------------------------");
			
			System.out.println();

		//making sure user does not enter in a sentence
		//CHECKING VALIDATION OF INPUT
		do {
			System.out.println("Please press: '1' for login");
			System.out.println("              '2' for create account");
			System.out.println("----------------------------------------------");
			while(!userInp.hasNextInt()) {
				System.out.println("Please enter a valid input");
				System.out.println("A valid input would be '1' for login");
				System.out.println("                       '2' for create account"); 
				userInp.next();
			}
			loginOption = userInp.nextInt();
		} while(loginOption < 1 || loginOption > 2);
		 
        
        // LOGGING A USER INTO THE APP
        if(loginOption == 1) {
			Person<Object> currentPerson = new Person<Object>();
			System.out.print("Username: ");
            usernameInp = userInp.next();
            userInp.nextLine();
			System.out.print("\n");
			
			//validate userinput username is within parameters
            while(usernameInp.length() != 8) {
				System.out.println("Invalid Username");
                usernameInp = userInp.next();
                userInp.nextLine();
			}
			

		    System.out.print("Password: ");
		    passwordInp = userInp.nextLine();
            System.out.print("\n");

			currentPerson = HashClass.getValue(usernameInp);//current person has person object that is paired to that username

			loginUser = currentPerson.getUserName();

			//validate user has entered correct password
			while(!passwordInp.equals(currentPerson.getPassword())) {
				System.out.println("Incorrect password!");
				System.out.print("Enter password: ");
            	passwordInp = userInp.next();
            	userInp.nextLine();
				System.out.print("\n");

				
			}
			System.out.println();
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Thank you for logging in to BookFace!"); 
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			
        }
        
		// REGISTERING A NEW USER FOR THE APP
		else  { // else call register to register account 
            System.out.println("Please create an 8 character username: ");
			usernameInp = userInp.next();
			userInp.nextLine();

		
			while(usernameInp.length() != 8) {
				System.out.print("Please enter a valid 8 character username: ");
                usernameInp = userInp.next();
                userInp.nextLine();
			}

			//Take all nescessary user input to create a new profile
		    System.out.print("Please create a Password: ");
		    passwordInp = userInp.nextLine();
            System.out.print("What is your first name?: ");
            String firstName = userInp.nextLine();
            System.out.print("What is your last name?: ");
            String lastName = userInp.nextLine();
            System.out.print("Create a small bio to be displayed on your profile: ");
            String bio = userInp.nextLine();
        
            //create 4 digit user ID using a hash function
            int personID = 0;
			personID = HashClass.createKey(usernameInp);
			
			//Load all user data into a new Person object
            Person<Object> tempPerson = new Person<Object>();
            tempPerson.setUserName(usernameInp);
            tempPerson.setPassword(passwordInp);
            tempPerson.setFirstName(firstName);
            tempPerson.setLastName(lastName);
			tempPerson.setBio(bio);
			
			Node register = new Node(personID);
			
			//add new person object to the hashtable at the index associated with the created key based on the username
			HashClass.addToHashTable(personID, tempPerson);
			UndirectedGraph.addVertex(register);

			//Login for a newly registered account
			Person<Object> currentPerson = new Person<Object>();
            System.out.print("Enter username: ");
            usernameInp = userInp.next();
            userInp.nextLine();
			System.out.print("\n");
			//validate
            while(usernameInp.length() != 8) {
				System.out.println("Invalid Username");
                usernameInp = userInp.next();
                userInp.nextLine();
			}
			//enter password
		    System.out.print("Enter password: ");
            passwordInp = userInp.next();
            userInp.nextLine();
			System.out.print("\n");
			//load the Person from the hashtable that correlates to the username given by the user
            currentPerson = HashClass.getValue(usernameInp);
		
			//validate that the username and password given by the user matches the profile inside the hashtable
            while (!passwordInp.equals(currentPerson.getPassword())) {
				System.out.println("Incorrect password!");
				System.out.print("Enter password: ");
            	passwordInp = userInp.next();
            	userInp.nextLine();
				System.out.print("\n");
				                
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Thank you for logging in to BookFace!");   
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		} //end of if else

		
			//displayProfileUser of the profile that was logged into by the user
			Person.displayProfileUser(usernameInp, hashtable, hashlist);

			//AFTER USER DELETES REGISTERED PROFILE. Allow user to either create a new account, or  
			//to quit program
			
			//validate data type and user input
			do {
				System.out.println("Would you like to register for BookFace?");
				System.out.println("Press '1' to register another account.");
				System.out.println("      '0' to exit bookface");
				while(!userInp.hasNextInt()) {
					System.out.println("Enter a valid choice of '1' or '0'");
					userInp.next();
				}
				choice = userInp.nextInt();
			} while(choice < 0 || choice > 1); 

		//loop if user would like to register a new account
		} while (choice != 0);
        userInp.close();
	}
	
	
}
	
