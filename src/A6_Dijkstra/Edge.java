package A6_Dijkstra;


import A6_Dijkstra.Vertex;

public class Edge {
	
	private long id;
	private long weight = 1;
	private String name;
	private Vertex in, to;
	
	public Edge(long id, Vertex in, Vertex to, long weight, String name)
	{
		this.id = id;
		this.in = in;
		this.to = to;
		this.weight = weight;
		this.name = name;
	}
	
	public long getID()
	{
		return id;
	}
	public long getWeight(){
		return weight;
	}
	public String getName()
	{
		return name;
	}
	public Vertex getIn()
	{
		return in;
	}
	public Vertex getTo()
	{
		return to;
	}
}