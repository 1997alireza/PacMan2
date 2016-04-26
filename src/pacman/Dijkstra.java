package pacman;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }

}


class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Dijkstra
{
	boolean [][] map;
	public Dijkstra(boolean map [][])
	{
		this.map = map;
	}
    private void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

	    while (!vertexQueue.isEmpty())
	    {
	        Vertex u = vertexQueue.poll();
	
	        for (Edge e : u.adjacencies)
	        {
	        	Vertex v = e.target;
	        	double weight = e.weight;
	        	double distanceThroughU = u.minDistance + weight;
	        	if (distanceThroughU < v.minDistance) 
	        	{
	        		vertexQueue.remove(v);
	        		
	        		v.minDistance = distanceThroughU ;
	        		v.previous = u;
	        		vertexQueue.add(v);
	        	}
	        }
	    }
    }

    public List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    public int getDistance(int y1,int x1,int y2,int x2)
    {
    	if(map[x1][y1] || map[x2][y2])
    		return (int)Double.POSITIVE_INFINITY;
    	Vertex [][] vertexes = new Vertex[map.length][map[0].length];
    	for(int i=0;i<map.length;i++)
    		for(int j=0;j<map[i].length;j++)
    		{
    			if(!map[i][j])
    				vertexes[i][j] = new Vertex(i+" "+j);
        		else
        			vertexes[i][j] = null;
        	}
        		
    	for(int i=0;i<map.length;i++)
    		for(int j=0;j<map[i].length;j++)
    		{
    			if(!map[i][j])
    			{
    				ArrayList <Edge> edgesForThis = new ArrayList<Edge>();
    				if(i>=1 && !map[i-1][j])
    					edgesForThis.add(new Edge(vertexes[i-1][j],1));
    	      		if(j>=1 && !map[i][j-1])
    	      			edgesForThis.add(new Edge(vertexes[i][j-1],1));
    	    		if(i<=map.length-2 && !map[i+1][j])
    	   				edgesForThis.add(new Edge(vertexes[i+1][j],1));
    	   			if(j<=map[0].length-2 && !map[i][j+1])
    	   				edgesForThis.add(new Edge(vertexes[i][j+1],1));
    	    			
    	   			Edge[] arrayOfEdge = new Edge[edgesForThis.size()];
        			for(int k=0;k<edgesForThis.size();k++)
   	    				arrayOfEdge[k] = edgesForThis.get(k);
   	    			vertexes[i][j].adjacencies =arrayOfEdge;
       			}
       		}
       			computePaths(vertexes[x1][y1]);
       			return (int)vertexes[x2][y2].minDistance;
//            List<Vertex> path = getShortestPathTo(Z);
//            System.out.println("Path: " + path);
    }
}