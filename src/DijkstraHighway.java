/*
 * Author: Obed Josiah Tandadjaja
 * COS230 - Assignment 6: Dijkstra Google Map
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DijkstraHighway {

	static AdjList list;
	static int vertices = 0;
	static int paths = 0;
	static Distance[] p;
	static boolean[] unvisited;
	static double distanceToCurrent = 0.0;
	static int currentIndex;
	static Path[] shortest_path;
	static boolean[] shortest_vertex;
	static int shortest_path_index = 0;
	static int nVertex = 0;
	static int nPath = 0;
	static PrintWriter writer;
	
	public static void main(String[] args)
	{
		if(args.length < 3)
		{
			System.out.println("Wrong Input!");
			System.out.println("Program Input Format: java DijkstraHighway <starting_city_index/name> <destination_city_indexes/names>");
		}
		else 
		{
			multipleDestinations(args);
		}
	}
	
	/**
	 * calculates the shortest distance for one route
	 * @param args - command line arguments
	 */
	public static void singleDestination(String[] args)
	{
		// get the arguments and put it into variables
		String filename = args[0];
		String start = args[1];	// starting cityName
		String end = args[2]; // ending cityName

		// get the starting and destination city's index
		int start_index;
		int end_index;
		if(Character.isDigit(start.charAt(0)))
			start_index = Integer.parseInt(start);
		else
			start_index = list.findVertex(start);
		if(Character.isDigit(end.charAt(0)))
			end_index = Integer.parseInt(end);
		else
			end_index = list.findVertex(end);

		// try to find the cities, if theyre not there then print an error
		if(start_index == -1)
		{
			System.out.println("There is no vertex with the name of "+start+". Quitting...");
			System.exit(0);
		}
		if(end_index == -1)
		{
			System.out.println("There is n`o vertex with the name of "+end+". Quitting...");
			System.exit(0);
		}

		// initialize variables
		unvisited = new boolean[vertices];
		unvisited[start_index] = true;

		// compute paths
		computePaths(start_index);

		// find the shortest path
		findShortestPath(start_index, end_index);
	}
	
	/**
	 * calculates the shortest distance for multiple routes
	 * @param args - command line arguments
	 */
	public static void multipleDestinations(String[] args)
	{
		try 
		{
			loadFile(args[0]);
			p = new Distance[vertices];
			shortest_path = new Path[paths];
			shortest_vertex = new boolean[vertices];
			writer = new PrintWriter("result.gra", "UTF-8");
			for(int i = 1; i < args.length-1; i++)
			{
				String[] temp = new String[3];
				temp[0] = args[0];
				temp[1] = args[i];
				temp[2] = args[i+1];
				singleDestination(temp);
			}
			generateFile();
			writer.close();
			System.out.println("Done! Please open result.gra on your directory");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * load in all the .gra file to be read
	 * @param filename
	 */
	public static void loadFile(String filename)
	{
		File f = new File(filename);
		try 
		{
			Scanner sc = new Scanner(f);
			String header = sc.nextLine();
			String[] header_split = header.split(" ");
			vertices = Integer.parseInt(header_split[0]);
			paths = Integer.parseInt(header_split[1]);
			
			list = new AdjList(vertices);
			
			while(sc.hasNext())
			{
				String s = sc.nextLine();
				if(Character.isDigit(s.charAt(0)))
				{
					String[] split = s.split(" ");
					int start = Integer.parseInt(split[0]);
					int end = Integer.parseInt(split[1]);
					String path_name = split[2];
					double distance = calculateDistance(list.graph[start].head.getLatitude(), list.graph[start].head.getLongitude(), 
														list.graph[end].head.getLatitude(), list.graph[end].head.getLongitude());
					list.addRelation(path_name, start, end, distance);
					list.addRelation(path_name, end, start, distance);
				}
				else
				{
					String[] split = s.split(" ");
					String name = split[0];
					double latitude = Double.parseDouble(split[1].replace(",", ""));
					double longitude = Double.parseDouble(split[2]);
					list.addVertex(name, latitude, longitude);
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Cannot find file! Quitting...");
			System.exit(0);
		}
	}
	
	/**
	 * finds all the shortest path to the starting city
	 * @param start - index of starting city
	 */
	public static void computePaths(int start)
	{
		unvisited[start] = true;
		int counter = 1;
		for(int i = 0; i < vertices; i++)
		{
			if(list.graph[start].contains(i))
			{
				p[i] = new Distance(start, list.graph[start].getDistanceByIndex(i));
			}
			else
			{
				p[i] = new Distance(start, Double.POSITIVE_INFINITY);
			}
		}
		
		while(counter < vertices)
		{
			int index = getMin();
			double d = p[index].distance;
			
			if(d == Double.POSITIVE_INFINITY)
			{
				System.out.println("There are unreachable vertices "+index);
				break;
			}
			else
			{
				currentIndex = index;
				distanceToCurrent = d;
			}
			
			unvisited[currentIndex] = true;
			counter++;
			adjustPaths();
		}
	}
	
	/**
	 * just code from the book, with some tweaks... Nothing special
	 */
	public static void adjustPaths()
	{
		int i = 0;
		while(i < vertices)
		{
			if(unvisited[i] && list.graph[currentIndex].contains(i))
			{
				i++;
				continue;
			}
			else
			{
				double currentToFringe = list.graph[currentIndex].getDistanceByIndex(i);
				double startToFringe = distanceToCurrent + currentToFringe;
				double distanceRightNow = p[i].distance;
				
				if(startToFringe < distanceRightNow)
				{
					p[i].parent = currentIndex;
					p[i].distance = startToFringe;
				}
				i++;
			}
		}
	}
	
	/**
	 * just code from the book to get the minimum neighbor, with some tweaks... Nothing special
	 * @return
	 */
	public static int getMin()
	{
		double min = Double.POSITIVE_INFINITY;
		int index = 0;
		for(int i = 0; i < vertices; i++)
		{
			if(!unvisited[i] && p[i].distance < min)
			{
				min = p[i].distance;
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * displays the path. Only for testing purposes.
	 */
	public static void displayPaths()
	{
		for(int i = 0; i < vertices; i++)
		{
			System.out.print(list.graph[i].peek().getName()+" = ");
			if(p[i].distance == Double.POSITIVE_INFINITY)
			{
				System.out.print("inf");
			}
			else
			{
				System.out.print(p[i].distance);
			}
			System.out.print(" ("+list.graph[p[i].parent].peek().getName()+")\n");
		}
	}
	
	/**
	 * Writes the .gra file based on the results
	 */
	public static void generateFile()
	{
		int[] temp_array = new int[vertices];
		int index = 0;
		try 
		{
			writer.printf("%d %d\n", nVertex, nPath);
			for(int i = 0; i < vertices; i++)
			{
				if(shortest_vertex[i])
				{
					writer.printf("%s %f, %f\n",list.graph[i].peek().getName(),
							list.graph[i].peek().getLatitude(), list.graph[i].peek().getLongitude());
					temp_array[index] = i;
					index++;
				}
			}
			for(int i = 0; i < shortest_path_index; i++)
			{
				writer.printf("%d %d %s\n", find(temp_array, shortest_path[i].start_point), 
						find(temp_array, shortest_path[i].end_point), shortest_path[i].path_name);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * finds the index of a particular thing in an int array
	 * @param array
	 * @param index
	 * @return
	 */
	public static int find(int[] array, int index)
	{
		for(int i = 0; i < vertices; i++)
		{
			if(array[i] == index)
				return i;
		}
		return -1;
	}
	
	/**
	 * checks if the Path array contains the particular path
	 * @param array
	 * @param data
	 * @return
	 */
	public static boolean contains(Path[] array, Path data)
	{
		for(int i = 0; i < paths; i++)
		{
			if(array[i].compareTo(data) == 0)
				return true;
		}
		return false;
	}
		
	/**
	 * finds the shortest path
	 * @param start
	 * @param end
	 */
	public static void findShortestPath(int start, int end)
	{
		int index = end;
		Path[] temp = new Path[paths];
		int i = 0;
		if(!shortest_vertex[end])
		{
			shortest_vertex[end] = true;
			nVertex++;
		}
		do
		{
			if(!shortest_vertex[p[index].parent])
			{
				shortest_vertex[p[index].parent] = true;
				nVertex++;
			}
			System.out.println(index+" "+p[index].parent);
			list.graph[index].print();
			String path_name = list.graph[index].find(p[index].parent).getName();
			Path path = new Path(null, path_name, p[index].parent, index, 0.0, null);
			temp[i] = path;
			if(contains(temp, path))
				nPath++;
			index = p[index].parent;
			i++;
		}
		while(index != start);
		
		for(int x = i-1; x >= 0; x--)
		{
			shortest_path[shortest_path_index] = temp[x];
			shortest_path_index++;
		}
	}
	
	/**
	 * calculates the distance between two coordinates. I did not write this method.
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) 
	{
	    double earthRadius = 6371000; 
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    // returns the distance in km
	    return dist/1000;
	}
}
