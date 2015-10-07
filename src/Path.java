/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 6: Dijkstra Google Map
 */

public class Path extends Node implements Comparable<Path>{

	Node prev;
	Node next;
	String path_name;
	int start_point;
	int end_point;
	double distance;
	
	Path(Node prev, String path_name, int start_point, int end_point, double distance, Node next)
	{
		this.path_name = path_name;
		this.start_point = start_point;
		this.end_point = end_point;
		this.distance = distance;
		this.prev = prev;
		this.next = next;
	}
	
	String getName()
	{
		return path_name;
	}
	double getDistance()
	{
		return distance;
	}
	Node getNext()
	{
		return next;
	}
	Node getPrev()
	{
		return prev;
	}
	void setNext(Node n)
	{
		next = n;
	}
	void setPrev(Node n)
	{
		prev = n;
	}
	int getIndex()
	{
		return end_point;
	}

	// I need this method to check if there exists a path
	// identical to this, in the array already
	@Override
	public int compareTo(Path arg0) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(arg0.start_point == this.start_point)
			flag = true;
		if(arg0.end_point == this.end_point)
			flag = true;
		if(arg0.path_name == this.path_name)
			flag = true;
		if(flag)
			return 0;
		else
			return -1;
	}
	
}
