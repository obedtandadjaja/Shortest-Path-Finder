/*
 * Author: Obed Josiah Tandadjaja
 * COS 230 - Assignment 6: Dijkstra Google Map
 * Copied the whole thing from Lab 8...
 */

public class AdjList {
	
	LinkedList<String, Double>[] graph;
	
	// constructor
    public AdjList(int vertices) {
    	
    	// My graph is basically an array of linkedlists
    	// The first Node is always a Vector and the subsequent Nodes are Paths
    	graph = (LinkedList<String, Double>[]) new LinkedList[vertices];
    }

    // add relation
    void addRelation(String name, int from, int to, double distance) {
    	graph[from].add(name, from, to, distance);
    }
    
    // find the vertex, if not found then add vertex
    int findVertex(String vertex)
    {
    	for(int i = 0; i < graph.length; i++)
    	{
    		if(graph[i] == null)
    		{
    			break;
    		}
    		if(vertex.equals(graph[i].peek().getName()))
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    // add the vertex to the graph
    void addVertex(String vertex, double latitude, double longitude)
    {
    	for(int i = 0; i < graph.length; i++)
    	{
    		if(graph[i] == null)
    		{
    			graph[i] = new LinkedList<String, Double>();
    			graph[i].addFront(vertex, latitude, longitude);
    			return;
    		}
    	}
    }

    // prints the adjacency list
    void print()
    {
    	for(int i = 0; i < graph.length; i++)
    	{
    		if(graph[i] != null)
    		{
    			System.out.print(i);
    			graph[i].print();
    		}
    	}
    }
}

     