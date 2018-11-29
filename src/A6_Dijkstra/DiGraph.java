package A6_Dijkstra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import A6_Dijkstra.DiGraph_Interface;
import A6_Dijkstra.Edge;
import A6_Dijkstra.Vertex;




public class DiGraph implements DiGraph_Interface {

	// in here go all your data and methods for the graph
	// and the topo sort operation

	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
	}

	long numNodes = 0;
	long numEdges = 0;
	//Should I change the maps for vertexes and edges to hashsets?


	Set<Long> identity = new HashSet<Long>(); 
	Set<Long> edges = new HashSet<Long>();
	Set<Edge> eg = new HashSet<Edge>();
	Map<String, Vertex> vert = new ConcurrentHashMap<String, Vertex>();
	List<Vertex> zeroEdge = new ArrayList<Vertex>();
	MinBinHeap queue = new MinBinHeap();
	Set<Vertex> included = new HashSet<Vertex>();
	ArrayList<ShortestPathInfo> temp = new ArrayList<ShortestPathInfo>();

	@Override
	public boolean addNode(long idNum, String label) {
		// TODO Auto-generated method stub
		if(idNum < 0 || vert.containsKey(label) || identity.contains(idNum) || label == null)
		{
			return false;
		}
		else 
		{
			identity.add(idNum);
			numNodes++;
			Vertex x = new Vertex(idNum, label);
			vert.put(label, x);
			if(x.getEdgeCount() == 0)
			{
				zeroEdge.add(x);
			}

			return true;
		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		// TODO Auto-generated method stub
		if(idNum < 0 || edges.contains(idNum) || !vert.containsKey(sLabel) || !vert.containsKey(dLabel) || weight < 0	)
		{
			return false;
		}
		if(vert.get(sLabel).connect.contains((vert.get(dLabel))))
		{
			return false;
		}
		else {
			edges.add(idNum);
			eg.add(new Edge(idNum, vert.get(sLabel), vert.get(dLabel), weight, eLabel));
			numEdges++;
			vert.get(sLabel).connect.add(vert.get(dLabel));
			vert.get(dLabel).addEdge();
			vert.get(dLabel).from.put(sLabel, weight);
			zeroEdge.remove(vert.get(dLabel));

			return true;
		}

	}

	@Override
	public boolean delNode(String label) {
		// TODO Auto-generated method stub
		if(!vert.containsKey(label))
		{
			return false;
		}
		else
		{
			for(Vertex v: vert.get(label).connect)
			{
				v.removeEdge();
				numEdges--;
				v.from.remove(label);
				if(v.getEdgeCount() == 0)
				{
					zeroEdge.add(v);
				}
			}
			for(Vertex z: vert.values())
			{
				if(z.connect.contains(vert.get(label)))
				{
					z.connect.remove(vert.get(label));
					numEdges--;
				}
			}
			identity.remove(vert.get(label).getID());
			vert.remove(label);
			numNodes--;
			return true;
		}
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		// TODO Auto-generated method stub

		if(!vert.containsKey(sLabel) || !vert.containsKey(dLabel) || !(vert.get(sLabel).connect.contains(vert.get(dLabel))))
		{
			return false;
		}
		else
		{

			vert.get(sLabel).connect.remove(vert.get(dLabel));
			vert.get(dLabel).removeEdge();
			vert.get(dLabel).from.remove(sLabel);
			numEdges -= 1;	
			for(Edge e: eg)
			{
				if(e.getIn().getName().equals(sLabel) && e.getTo().getName().equals(dLabel))
				{
					long idNum = e.getID();
					String name = e.getName();
					edges.remove(idNum);
					break;
				}
			}

			if(vert.get(dLabel).getEdgeCount() == 0)
			{
				zeroEdge.add(vert.get(dLabel));
			}

			return true;
		}
	}

	@Override
	public long numNodes() {
		// TODO Auto-generated method stub
		return numNodes;
	}

	@Override
	public long numEdges() {
		// TODO Auto-generated method stub
		return numEdges;
	}

	@Override
	public String[] topoSort() {
		// TODO Auto-generated method stub

		int size = vert.size();
		String[] temp = new String[size];
		int index = 0;

		while(!zeroEdge.isEmpty())
		{
			Vertex u = zeroEdge.remove(0);
			temp[index] = u.getName();
			this.delNode(u.getName());
			index++;


		}
		if(!vert.isEmpty())
		{
			return null;
		}
		else
		{
			return temp;
		}
	}

	// rest of your code to implement the various operations


	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		// TODO Auto-generated method stub
		//ShortestPathInfo[] paths = new ShortestPathInfo[vert.size()];
		//int index= 0;
		Vertex start = vert.get(label);
		start.setDistance(0);
		queue.insert(new EntryPair(label, 0));
		
		while(queue.size() > 0)
		{
			String name = queue.getMin().getValue();
			long dist = queue.getMin().getPriority();
			Vertex v = vert.get(name);
			//System.out.println(name + " " + dist);
			//paths[index] = new ShortestPathInfo(name, dist);
			//temp.add(new ShortestPathInfo(name, dist));
			queue.delMin();
			if(v.getProcess())
			{
				continue;
			}
				v.trueProcess();
				temp.add(new ShortestPathInfo(name, dist));
				for(Vertex adj: v.connect)
				{
					long newDistance = 0;
					for(Edge e: eg)
					{
						if(e.getIn().getName().equals(name) && e.getTo().getName().equals(adj.getName()))
						{
							newDistance = dist + e.getWeight();
						}	
					}
					if(!adj.getProcess())
					{
						if(newDistance < adj.getDistance())
						{
							adj.setDistance(newDistance);
							adj.prevNode = v;
						}
						queue.insert(new EntryPair(adj.getName(), adj.getDistance()));
					/*if(adj.getDistance() > dist + adj.from.get(name))
					{
						adj.setDistance(dist + adj.from.get(name));
					}
					queue.insert(new EntryPair(adj.getName(), adj.getDistance())); */
				}
			}
		}
		for(Vertex v: vert.values())
		{
			if(!v.getProcess())
			{
				temp.add(new ShortestPathInfo(v.getName(), -1));
			}
		}
		
		ShortestPathInfo[] paths = new ShortestPathInfo[temp.size()];
		for(int i = 0; i < temp.size(); i++)
		{
			paths[i] = temp.get(i);
			//System.out.println(paths[i].getDest() + " " + paths[i].getTotalWeight());
		}
		return paths;
		
		
	}

	// rest of your code to implement the various operations
}