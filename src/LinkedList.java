/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 6: Dijkstra Google Map
 * Copied this class from Lab 8 with some tweaks
 */

public class LinkedList<V, E> {

	Node head;
	Node foot;
	
	//constructor
	LinkedList()
	{
		this.head = null;
		this.foot = null;
	}
	
	//adding single input
	public void add(String data, int start_point, int end_point, double distance)
	{
		if(head == foot)
		{
			foot = new Path(head, data, start_point, end_point, distance, null);
			head.next = foot;
		}
		else
		{
			foot = new Path(foot, data, start_point, end_point, distance, null);
			foot.getPrev().next = foot;
		}
	}
	
	public void addFront(String data, double latitude, double longitude)
	{
		head = foot = new Vector(null, data, latitude, longitude, null);
	}
	
	//prints the linked list [for testing purposes]
	public void print()
	{
		Node current = head;
		while(current != null)
		{
			if(current == head)
			{
				System.out.println("\nRelations from "+current.getName());
				current = current.next;
			}
			else
			{
				System.out.println(current.getName()+" "+current.getDistance());
				current = current.next;
			}
		}
	}
	
	// finds the Node based on the index and returns the Node
	public Node find(int index)
	{
		Node current = head;
		while(current != null)
		{
			if(current.getIndex() == index)
			{
				return current;
			}
			current = current.next;
		}
		return null;
	}
	
	// check if a node with this particular index exists in the LL
	public boolean contains(int index)
	{
		Node current = head;
		while(current != null)
		{
			if(current.getIndex() == index)
			{
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	// gets the distance of the path by its index
	public double getDistanceByIndex(int index)
	{
		Node current = head;
		while(current != null)
		{
			if(current.getIndex() == index)
			{
				return current.getDistance();
			}
			current = current.next;
		}
		return Double.POSITIVE_INFINITY;
	}
	
	// peeks the first node in the LL
	public Vector peek()
	{
		if(head != null)
		{
			return (Vector) head;
		}
		else
		{
			return null;
		}
	}
}
