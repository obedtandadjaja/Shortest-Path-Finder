/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 6: Dijkstra Google Map
 */

public class Node {

	Node next;
	Node prev;
	
	String getName()
	{
		return "";
	}
	double getLatitude()
	{
		return 0.0;
	}
	double getLongitude()
	{
		return 0.0;
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
	double getDistance()
	{
		return 0;
	}
	int getIndex()
	{
		return -1;
	}
}
