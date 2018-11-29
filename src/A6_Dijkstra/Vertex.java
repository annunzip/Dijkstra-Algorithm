package A6_Dijkstra;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Vertex {
	
	private int inEdge;
	private long id;
	private String name;
	Set<Vertex> connect;
	Map<String, Long> from;
	private boolean processed;
	private long currentCheapest;
	public Vertex prevNode;
	
	public Vertex(long id, String name)
	{
		inEdge = 0;
		this.id = id;
		this.name = name;
		connect = new HashSet<Vertex>();
		processed = false;
		this.prevNode = null;
		this.currentCheapest = Integer.MAX_VALUE;
		from = new HashMap<String, Long>();
	}
	
	public int getEdgeCount()
	{
		return inEdge;
	}
	public String getName()
	{
		return name;
	}
	public long getID()
	{
		return id;
	}
	public void addEdge()
	{
		inEdge++;
	}
	public void removeEdge()
	{
		if(inEdge >= 1)
		{
		inEdge--;
		}
	}
	public boolean getProcess()
	{
		return processed;
	}
	public void trueProcess()
	{
		processed = true;
	}
	
	public Vertex getPrev()
	{
		return prevNode;
	}
	public long getDistance()
	{
		return currentCheapest;
	}
	public void setDistance(long x)
	{
		currentCheapest = x;
	}
}
