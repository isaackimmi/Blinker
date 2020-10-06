package app;
import java.util.Iterator;
/**
   A class that implements the ADT undirected graph.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/

/**
 * Create hashtable <hashkey, list of friends> 
 * breadth first traversal
 * objects will be added in but edges only created when adding friends
 * 
 * add/remove methods 
 * 
 */
public class UndirectedGraph<T, V>
{

	//private static LinkedQueue<Node> queue = new LinkedQueue<>();
	//static LList<Node> nodes = new LList<Node>();

	static class Node {
		int data;
		boolean visited;
		LList<Node> neighbours;
		
		Node() {
			this.data = -1;
			this.neighbours = null;
		}

		Node(int data) {
			this.data = data;
			this.neighbours = new LList<>();
		}

		public int getData() {
			int toReturn = this.data;
			return toReturn;
		}

		public void addNeighbors(Node currentNode, Node neighbourNode) {
			if(!currentNode.getNeighbours().contains(neighbourNode)){
				currentNode.neighbours.add(neighbourNode);
				neighbourNode.neighbours.add(currentNode);
			}
			else {
				System.out.println("Neighbor already exists!");
			}
		}

		public LList<Node> getNeighbours() {
			return neighbours;
		}

		public void setNeighbours(LList<Node> neighbours) {
			this.neighbours = neighbours;
		}
	}

	// TESTING THE CODE ABOVE

	public static LList<String> bfs(Node node) {
		LinkedQueue<Node> queue = new LinkedQueue<Node>();
		queue.enqueue(node);
		node.visited = true;
		LList<Node> friendsToRecommend = new LList<Node>();

		//queue all friends friends
		
		Node currentUser = queue.dequeue();
		int currentID = currentUser.getData();
		LList<Node> neighbours = new LList<Node>();
		neighbours = currentUser.getNeighbours();

		for(int i = 1; i <= neighbours.getLength(); i++) {
			Node friend = neighbours.getEntry(i);
			
			if (friend != null && !friend.visited) {
				queue.enqueue(friend);
				friend.visited = true;
			}
					
		}
		
		
		//load all nodes into a list that do not already have edges with the current user
		while(!queue.isEmpty()) {
			Node testFriend = queue.dequeue();
			for(int p = 1; p <= testFriend.getNeighbours().getLength(); p++) {
				Node toTest = testFriend.getNeighbours().getEntry(p);
				if(!hasEdge(node, toTest) && node != toTest) {
					if (!friendsToRecommend.contains(toTest)) {
						friendsToRecommend.add(toTest);
					}
	
				}
				else {
					System.out.println("friend connection exists already");
				}
			}
		}

		//convert list of nodes to list of strings of usernames to recommend
		LList<String> friendStrings = new LList<String>();
		for(int k = 1; k <= friendsToRecommend.getLength(); k++) {
			Person<Object> temp = new Person<Object>();
			temp = HashClass.hashArray[friendsToRecommend.getEntry(k).getData()];
			String toAdd = temp.getUserName();
			friendStrings.add(toAdd);
		}

		return friendStrings;
	}

	
	private static HashClass<Integer, LList<Node>> vertices;
	private static int edgeCount;
	private static int verticeCount;

	public UndirectedGraph() {
		vertices = new HashClass<Integer, LList<Node>>();
		edgeCount = 0;
	} // end default constructor

	public static void addEdge(Node begin, Node end) {
		if (hasEdge(begin, end)) {
			if (!vertices.containsKey(begin.getData())) {
				addVertex(begin);
			}

			if (!vertices.containsKey(end.getData())) {
				addVertex(end);
			}

			vertices.get(begin.getData()).add(end);
			// vertices.get(end).add(begin);
			edgeCount++;
		} 
			else { System.out.println("edge already created"); }
			
	} // end addEdge
	
	
	
	/**
	 * removeEdge will remove the connection between two users 
	 * in the graph
	 * @param Node begin current user node
	 * @param Node end user of friend to remove
	 */
	public static void removeEdge(Node begin, Node end) {
		vertices.get(begin.getData()).removeEntry(end);
		edgeCount--;
	}

	
	

	
	public int getNumberOfEdges() {
		return edgeCount;

	} // end getNumberOfEdges


	public static boolean hasEdge(Node begin, Node end) {
		if (vertices.get(begin.getData()).contains(end)) {
			return true;
		} else
			return false;
	}

	public boolean isEmpty() {
		return getNumberOfVertices() != 0;
	}

	public static int getNumberOfVertices() {
		return verticeCount;
	}

	public void clear() {
		
	}

	
	/**
	 * addVertex adds new profile to the hashtable utilized by
	 * undirectedGraph.java
	 * 
	 * @param String   vertexLabel username of the profile
	 * @param LList<T> list list of profile's friends
	 * 
	 */
	public static <T> void addVertex(Node temp) {
		HashClass.hashList[temp.getData()] = temp;
		verticeCount++;
    }
    
    /**
     * removes profile from hash graph
     * @param String vertexLabel - username of the profile
     * @param LList<T> list - list of profile's friends
     *
     */
    public <T> void removeFromHashGraph(String vertexLabel, LList<T> list) {
        int ID = HashClass.validateKey(vertexLabel);
        HashClass.hashList[ID] = null;
    }

} // end UndirectedGraph
