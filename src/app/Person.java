package app;

/** Class Person will have all the information about a persons profile
 * and all the nescessary methods to access the data associated to the persons profile,
 * view profiles of friends, and make changes to the personal account as well as add a friend
 * to the linked list friends list inherent to the person object
 * 
 * @author Matthew Davenport
 * @author Victoria Nguyen
 * @author Isaac Kim
 * @author Marley Willyoung
 * @author Jahnae Reese
*/

import java.util.Scanner;

import app.UndirectedGraph.Node;


public class Person <K> {

    // A list of integers correlating to the profiles of friends
    public LList<String> friendsList = new LList<String>();

    // Profile information
    private String userName;
    private String firstName;
    private String lastName;
    private String bio;
    private String password;
    private String currentStatus;
    private int personID;

     

    // Default constructor
    public Person() {
        userName = null;
        firstName = null;
        lastName = null;
        bio = null;
        password = null;
        currentStatus = "ONLINE";
        friendsList = new LList <String>();
    }

    // Constructor with 5 arguments
    public Person(String uN, String fN, String lN, String bi, String pW, String cS) {
        userName = uN;
        firstName = fN;
        lastName = lN;
        bio = bi;
        password = pW;
        currentStatus = cS;
    }

    // Setters for profile
    public void setPersonID(int ID) {
        this.personID = ID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrentStatus(String status) {
        this.currentStatus = status;
    }

    // Getters for profile
    public int getPersonID() {
        return this.personID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBio() {
        return this.bio;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public LList getFriendsList() {
        return this.friendsList;
    }

    // Other Methods

    /* addFriend is a method that takes 3 parameters and returns nothing
     * will search for account specified by user
     * if user exists, will add the username to the linked list friendsList in Person object
     * 
     * @param friendUserName is the username given by the user to add to the users friend list
     * @param usernameUser is the username of the current user
     *          nescessary to be able to return back to the current account
     * @param hashtable: Hashtable of Person objects on current instance of the program
     * 
    */
    public static void addFriend(String friendUserName, String usernameUser, HashClass<Integer, Person> hashtable, UndirectedGraph<Integer, LList<Node>> hashlist) {
        // ADDS INTO LINKED LIST THE FRIEND'S USERNAME
        //will also add edges between the two vertices within the graph
        int choice;
        Scanner userInp = new Scanner(System.in);
        int friendID = HashClass.validateKey(friendUserName);
        Person<Object> tempFriend = HashClass.hashArray[friendID];
        Person<Object> currentProfile = HashClass.getValue(usernameUser);
        friendUserName = tempFriend.getUserName();
        String friendToAdd = null;

        if (tempFriend.getFirstName() == null) {
            System.out.println();
            System.out.println("No profile under username " + "'" + friendUserName
                    + "'" + " exists in BookFace.");
            
        } else {
            if(currentProfile.friendsList.contains(friendUserName)) {
                System.out.println("This user is already on your friends list!");
                System.out.println("Would you like to add a different friend, or return to your profile? ");
                
                do {
                    System.out.println("'1' to add another friend");
                    System.out.println("'2' to return to your profile");
                    while(!userInp.hasNextInt()) {
                        System.out.println("Please enter a valid integer input of 1 or 2");
                        userInp.next();
                    }
                    choice = userInp.nextInt();
                } while(choice < 1 || choice > 2);

                if(choice == 1) {
                    System.out.println("Enter the username of the friend you would like to add");
                    friendToAdd = userInp.nextLine();
                    addFriend(friendToAdd, usernameUser, hashtable, hashlist);
                }
                else {
                    displayProfileUser(usernameUser, hashtable, hashlist);
                }
            }
            else {
                Node current = HashClass.hashList[currentProfile.getPersonID()];
                Node toAdd = HashClass.hashList[tempFriend.getPersonID()];
                currentProfile.getFriendsList().add(friendUserName);
                System.out.println();
                System.out.println(tempFriend.getFirstName() + " has been added!");
                UndirectedGraph.addEdge(current, toAdd);
            }
        }

        // validate user input
        do {
            System.out.println();
            System.out.println("Press '1' to search for a profile to add");
            System.out.println("      '2' to quit back to your own profile");
            System.out.println("      '0' to exit BookFace");
            System.out.print("Please enter a valid choice: ");
            while(!userInp.hasNextInt()) {
                System.out.println();
                System.out.println("Press '1' to search for a profile to add");
                System.out.println("      '2' to quit back to your own profile");
                System.out.println("      '0' to exit BookFace");
                System.out.print("Please enter a valid choice: ");
                userInp.next();
            }
            choice = userInp.nextInt();
        } while (choice < 0 || choice > 2);
        

        if (choice == 1) {
            System.out.println();
            System.out.print("Enter the username of the profile you are searching to add: ");
            String usernameToSearch = userInp.next();
            userInp.nextLine();
            friendID = HashClass.validateKey(usernameToSearch);
            tempFriend = HashClass.hashArray[friendID];
            usernameToSearch = tempFriend.getUserName();
            addFriend(usernameToSearch, usernameUser, hashtable, hashlist);
        }

        else if (choice == 2) {
            displayProfileUser(usernameUser, hashtable, hashlist);
        }

        else if (choice == 0) {
            userInp.close();
            System.exit(50);
        }

        userInp.close();
        System.exit(50);

    }

    
    
    /* removeFriend removes a freind from the friendsList of the current user
     * will search for username in profile specified by user
     * if user exists, will remove the username from the linked list friendsList in Person object
     *
     * @param usernameUser is the username of the current user
     *          nescessary to be able to return back to the current account
    */
    public static void removeFriend(String usernameUser) {
        // REMOVES A FRIEND 'S USERNAME FROM THE LINKED LIST.
        int choice;
        String friendToRemove;
        boolean friendExists = false;
        Scanner userInp = new Scanner(System.in);
        
        Person<Object> currentProfile = HashClass.getValue(usernameUser);
        
        System.out.print("Enter the username of the friend that you would like to remove: ");
        
        friendToRemove = userInp.next();
        userInp.nextLine();
        friendExists = currentProfile.getFriendsList().contains(friendToRemove);
        if(friendExists) {
            System.out.println("You have chosen to remove " + friendToRemove + ". Are you sure you would"
            + "like to remove this friend from your friends list?");
        }
        else if (!friendExists) {
            System.out.println("ERROR: That username is not on your friends list!");
            
        }
        
        Node currentNode = HashClass.hashList[currentProfile.getPersonID()];
        Node nodeToRemove = HashClass.hashList[HashClass.validateKey(friendToRemove)];

        do {
            if (currentProfile.getFriendsList().isEmpty()) {
                System.out.println();
                System.out.println("NOTE: You have no friends to remove! \n");
                System.out.println("Press '2' to search again.");
                System.out.println("      '3' to return to your profile.");
                System.out.print("Please enter a valid choice: ");
            }
            else 
            {
                System.out.println();
                System.out.println("Press '1' to proceed.");
                System.out.println("      '2' to search again.");
                System.out.println("      '3' to return to your profile.");
                System.out.print("Please enter a valid choice: ");

            }
            while(!userInp.hasNextInt()) {
                System.out.print("Use a number associated with one of the choices displayed!: ");
                userInp.next();
            }
            choice = userInp.nextInt();
        } while(choice < 1 || choice > 3 );
        
        if (currentProfile.getFriendsList().isEmpty()) {
            System.out.println();
            System.out.println("You have no friends to remove! \n");
            return;
        }
        if (choice == 1) {
            currentProfile.getFriendsList().removeEntry(friendToRemove);
            UndirectedGraph.removeEdge(currentNode, nodeToRemove); 
        }

        if (choice == 2) {
            removeFriend(usernameUser);
        }

        if (choice == 3) {
            return;
        }
        return;
        
    }


    /* lookupProfile is a method that takes 3 parameters and returns nothing
     * will search for account specified by user
     * if user exists, will allow the user to view profile, add profile as friend, or return 
     *      back to profile of the user
     * 
     * @param usernameToFind is the username given by the user to look for on the network
     * @param usernameUser is the username of the current user
     *          nescessary to be able to return back to the current account
     * @param hashtable: Hashtable of Person objects on current instance of the program
     * 
    */

    public static void lookupProfile(int ID, String usernameUser, HashClass<Integer, Person> hashtable, UndirectedGraph<Integer, LList<Node>> hashlist) {
        // ONLY SHOW NAME/USERNAME AND BIO OF PROFILE BEING SEARCHED
        // CALLS DISPLAYPROFILEFRIEND TO VIEW FRIENDS PROFILE
        int choice;
        Scanner userInp = new Scanner(System.in);
        int lookupID = 0;

        
        Person<Object> tempFriend = hashtable.hashArray[ID];
        Person<Object> user = HashClass.getValue(usernameUser);

        if (tempFriend.getFirstName() == null) {
            System.out.println("No profile under username " + tempFriend.getUserName()
                    + " exists in BookFace.\n");

            // VALIDATE USER INPUT
            do {
                System.out.println();
                System.out.println("Press '1' to search for a profile to add");
                System.out.println("      '2' to quit back to your own profile");
                System.out.println("      '0' to exit BookFace");
                System.out.print("Please enter a valid choice: ");
                
                while (!userInp.hasNextInt()) {
                    System.out.println();
                    System.out.println("Press '1' to search for a profile to add");
                    System.out.println("      '2' to quit back to your own profile");
                    System.out.println("      '0' to exit BookFace");
                    System.out.print("Please enter a valid choice: ");
                    userInp.next();
                }
                choice = userInp.nextInt();
            }  while (choice < 0 || choice > 2); 


            if (choice == 1) {
                System.out.print("Enter the username of the profile you are searching to add: ");
                String usernameToSearch = userInp.nextLine();
                addFriend(usernameToSearch, usernameUser, hashtable, hashlist);
            }

            else if (choice == 2) {
                displayProfileUser(usernameUser, hashtable, hashlist);
            }

            else if (choice == 0) {
                System.exit(50);
            }


            System.exit(100);

        }

        else  {
            System.out.println();
            System.out.println("Found " + tempFriend.getFirstName() + "! what would you like to do?");

            System.out.println();

            //validate user input
            do {
                System.out.println("Press '1' to search for a profile to add");
                System.out.println("      '2' to quit back to your own profile");
                System.out.println("      '3' to add friend");
                System.out.println("      '0' to exit BookFace");
                System.out.print("Please enter a valid choice: ");
                
                while (!userInp.hasNextInt()) {
                    System.out.println();
                    System.out.println("Press '1' to search for a profile to add");
                    System.out.println("      '2' to quit back to your own profile");
                    System.out.println("      '3' to add friend");
                    System.out.println("      '0' to exit BookFace");
                    System.out.print("Please enter a valid choice: ");
                    userInp.next();
                }
                choice = userInp.nextInt();
                System.out.println();
            }  while (choice < 0 || choice > 3); 

            if (choice == 1) {
                System.out.print("Enter the username of the profile you are searching to add: ");
                String usernameToSearch = userInp.nextLine();
                lookupID = HashClass.validateKey(usernameToSearch);
                lookupProfile(lookupID, usernameUser, hashtable, hashlist);
            }

            else if (choice == 2) {
                displayProfileUser(usernameUser, hashtable, hashlist);
            }

            else if (choice == 3) {
                addFriend(tempFriend.getUserName(), usernameUser, hashtable, hashlist);
            }

            else if (choice == 0) {
                System.exit(50);
            }


            System.exit(100);

        }

    }

    
    /* displayProfileUser is a method that takes 2 parameters and returns nothing
     * will open account of user that was either registered or logged in to
     * Will allow user to call all other methods in profile, or change their 
     * information on their profile
     * 
     * 
     * @param usernameUser is the username of the current user
     *          nescessary to be able to return back to the current account
     * @param hashtable: Hashtable of Person objects on current instance of the program
     * 
     * if profile is deleted, program will revert back to either register or login again
    */
    public static void displayProfileUser(String username, HashClass<Integer, Person> hashtable, UndirectedGraph<Integer, LList<Node>> hashlist) {
        int correctID = HashClass.validateKey(username);
        Person<Object> profile = HashClass.hashArray[correctID];
        Scanner input = new Scanner(System.in);

        int choice = 0; //hold user choice for menu options
        String stringInput; //hold user choice for selecting profiles to add, lookup etc.

        //do while loop to continuously iterate through menu options a user defined amount of times
        do {
            //display all user information in their profile
            System.out.println("\n" + "\n");
            System.out.println("----------------------------------------------" + "\n");
            System.err.println(
                    profile.getFirstName() + " " + profile.getLastName() + " - " + profile.getCurrentStatus() + "\n");
            System.out.println(profile.getBio() + "\n");
            System.out.println("----------------------------------------------" + "\n");
            // DISPLAYS THE PERSON'S FRIEND LIST
            System.out.println("~~~~~~~~~");
            System.out.println("FRIENDS: ");
            System.out.println("~~~~~~~~~");

            //if friends list is not empty, display friends list
            if (!(profile.getFriendsList().isEmpty())) {
                for (int i = 1; i < profile.getFriendsList().getLength() + 1; i++) {
                    String currentFriend = (String) profile.friendsList.getEntry(i);
                    System.out.println("\t\t  " + currentFriend + "\n");
                    //System.out.println(profile.getFriendsList().getEntry(i) + "\n");
                }
            }
            else
            {
                System.out.println("\t\t- No friends - " + "\n");
            } 

            //display to user choices for what to do in their profile
            System.out.println("----------------------------------------------" + "\n");
            
            do {      
                System.out.println();
                System.out.println("Press \t'1' to SEARCH for a friend \n\t'2' to CHANGE your bio, \n"
                    + " \t'3' to CHANGE your status \n\t'4' to REMOVE a friend, \n"
                    + " \t'5' to ADD a friend, \n\t'6' to LOOK at a friend's profile, \n"
                    + " \t'7' to DELETE your profile \n\t'8' to VIEW recommended friends"
                    + " \t'9' to EXIT the app.");
                    System.out.println();
                    System.out.print("Please enter a valid choice: ");
                while(!input.hasNextInt()){
                    System.out.println();
                    System.out.print("Please enter a valid input of 1 - 8: ");
                    input.next();
                
                }
                choice = input.nextInt();
            } while(choice < 0 || choice > 8);
            System.out.println();
            
            //take user selected choice and edit selected portion of account
            if (choice == 1) {
                System.out.println("----------------------------------------------" + "\n");
                System.out.print("Enter your friend's username: ");
                stringInput = input.next();
            
                input.nextLine();
                int ID = HashClass.validateKey(stringInput);
                lookupProfile(ID, username, hashtable, hashlist);
                
            } else if (choice == 2) {
                //System.out.println("inside change bio if statement");
                System.out.println("----------------------------------------------" + "\n");
                System.out.println("Enter your new bio: " + "\n");
                stringInput = input.next(); 
                stringInput += input.nextLine();
                
                System.out.println(stringInput);
                profile.setBio(stringInput);

                System.out.println("----------------------------------------------" + "\n");
                System.out.println("Your new bio is: " + "\n");
                System.out.println(profile.getBio() + "\n");

            } else if (choice == 3){
                
                //validate user input
                do {
                    System.out.println("----------------------------------------------" + "\n");
                    System.out.println("STATUS OPTIONS: " + "\n");
                    System.out.println("Press '1' Online");
                    System.out.println("      '2' Offline");
                    System.out.println("      '3' Busy");
                    

                    while(!input.hasNextInt()) {
                        System.out.println("Invalid choice.");
                        System.out.println("Please enter '1'");
                        System.out.println("             '2'");
                        System.out.println("             '3'");
                        input.next();
                    }
                    choice = input.nextInt();                    
                } while(choice < 1 || choice > 3);
                
                if (choice == 1) {
                    profile.setCurrentStatus("ONLINE");
                }
                else if (choice == 2) {
                    profile.setCurrentStatus("OFFLINE");
                } 
                else if (choice ==3) {
                    profile.setCurrentStatus("BUSY");
                }
                
            } else if (choice == 4) {
                removeFriend(username);
            } else if (choice == 5) {
                
                System.out.print("Enter the username of the friend you would like to add: ");

                String usernameToAdd = input.next();
                input.nextLine();
                addFriend(usernameToAdd, username, hashtable, hashlist);
                System.out.println();
                
            } else if (choice == 6) {
                System.out.print("Enter the username of the profile of the friend you would like to view: ");
                String usernameToFind = input.next();
                input.nextLine();
                displayProfileFriend(usernameToFind, username, hashtable);
                
            } else if (choice == 7) {
                System.out.println("Are you sure you want to permanently delete your profile?");
                
                
                //validate user input
                do {
                    System.out.println("Choose '1' to PROCEED");
                    System.out.print("         '2' to CANCEL and return to the homepage: ");
                    while (!input.hasNextInt()) {
                        System.out.println("Invalid choice.");
                        System.out.println("Please enter '1'");
                        System.out.println("             '2'");
                        System.out.println("             '3'");
                        input.next();
                    }
                    choice = input.nextInt();
                } while (choice < 1 || choice > 2);

                if (choice == 1) {
                    int keyToDelete = HashClass.createKey(username);
                    Person<Object> personDeleted = HashClass.removeFromHashTable(keyToDelete, profile);
                    System.out.println("The profile deleted was " + personDeleted.getFirstName() + " " + personDeleted.getLastName());
                    return;
                }
            } else if (choice == 8) {
                recommendFriends(correctID, hashlist);
            }
            /*
             * else if (choice == 8 ) { code for viewing your recommended friends utilized
             * undirected graph }
             */
        } while (choice != 9);

        if (choice == 7) {
            System.exit(50);
        }
        System.exit(100);

    }

    /**
     * displayProfileFriend takes 3 parameters and returns nothing Will display the
     * profile of a friend, giving no access to change any data, and view a
     * restricted amount of data of the friend profile
     * 
     * @param usernameFriend username of the friend to look up
     * @param usernameUser   username of the current user
     * @param hashtable      hashtable containing all profiles on the network
     */
    public static void displayProfileFriend(String usernameFriend, String usernameUser,
            HashClass<Integer, Person> hashtable) {
        // ACCESS TO GETTERS ONLY. NOT SETTERS
        int choice = 0; // choice of user to continue to menu of choice
        Person<Object> friendProfile = HashClass.getValue(usernameFriend); // retrieve Person object of friend to look
                                                                           // up
        Scanner userChoice = new Scanner(System.in);

        System.out.println("\n" + "\n");
        System.out.println("----------------------------------------------" + "\n");
        System.err.println(friendProfile.getFirstName() + " " + friendProfile.getLastName() + " - "
                + friendProfile.getCurrentStatus() + "\n");
        System.out.println("----------------------------------------------" + "\n");
        System.out.println(friendProfile.getBio() + "\n");
        // DISPLAYS THE PERSON'S FRIEND LIST
        System.out.println("~~~~~~~~~");
        System.out.println("FRIENDS: ");
        System.out.println("~~~~~~~~~");

        // if the friends list of the Person object is not empty, display their friends
        // list
        if (!(friendProfile.getFriendsList().isEmpty())) {
            for (int i = 1; i < friendProfile.getFriendsList().getLength() + 1; i++) {
                String currentFriend = (String) friendProfile.friendsList.getEntry(i);
                System.out.println(currentFriend);
            }
        } else // if Person objects friends list is empty, display message
        {
            System.out.println("No friends found.  :(" + "\n");
        }

        // allow user to go back to profile, or quit the program
        System.out.println("----------------------------------------------" + "\n");

        do {
            System.out.println("Press 1 to go back to your own profile");
            System.out.println("Press 0 to quit BookFace");
            while (!userChoice.hasNextInt()) {
                System.out.println("Enter a valid integer option: ");
                userChoice.next();
            }
            userChoice.nextInt();
        } while (choice < 0 || choice > 1);

        /*
         * if (choice == 1) { displayProfileUser(usernameUser, hashtable); } else if
         * (choice == 0) { System.exit(50); }
         */

        // end if

    }

    public static void recommendFriends(int currentID, UndirectedGraph<Integer, LList<Node>> hashlist) {
        Node currentNode = HashClass.hashList[currentID];

        LList<String> recommendedFriends = UndirectedGraph.bfs(currentNode);

        System.out.println("RECOMMENDED FRIENDS: ");
        for(int i = 1; i <= recommendedFriends.getLength(); i++) {
            String usernameToDisplay = recommendedFriends.getEntry(i);
            System.out.println();
            System.out.println(usernameToDisplay);
            System.out.println();
            System.out.println();
        }
    }
}