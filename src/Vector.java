/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 6: Dijkstra Google Map
 */

public class Vector extends Node {

	String name;
	Node next;
	Node prev;
	double longitude;
	double latitude;
	int from;
	
	public Vector(Node prev, String name, double latitude, double longitude, Node next)
	{
		this.name = name;
		this.next = next;
		this.prev = prev;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	String getName()
	{
		return name;
	}
	
	double getLatitude()
	{
		return latitude;
	}
	
	double getLongitude()
	{
		return longitude;
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
		return -1;
	}
}
