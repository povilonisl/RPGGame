package util.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {

	private HashMap<Point, Vertex> vertices;
	private HashMap<Integer, Edge> edges;
	
	public Graph() {
		this.vertices = new HashMap<Point, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
	}
	
	public Graph(ArrayList<Vertex> vertices) {
		this.vertices = new HashMap<Point, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
	
		for(Vertex v : vertices) {
			this.vertices.put(v.getLabel(), v);
		}
	}
	
	public boolean addEdge(Vertex one, Vertex two) {
		return addEdge(one, two, 1);
	}
	
	public boolean addEdge(Vertex one, Vertex two, int weight) {
		if(one.equals(two)) {
			return false;
		}
		
		Edge edge = new Edge(one, two, weight);
		if(edges.containsKey(edge.hashCode())) {
			return false;
		}
		edges.put(edge.hashCode(), edge);
		one.addNeighbor(edge);
		two.addNeighbor(edge);
		return true;
	}
	
	public boolean containsEdge(Edge edge) {
		if(edge.getOne() == null || edge.getTwo() == null) {
			return false;
		}
		
		return this.edges.containsKey(edge.hashCode());
	}
	
	public Edge removeEdge(Edge edge) {
		edge.getOne().deleteNeighbor(edge);
		edge.getTwo().deleteNeighbor(edge);
		return this.edges.remove(edge.hashCode());
	}
	
	public boolean containsVertex(Vertex vertex) {
		return this.vertices.get(vertex.getLabel()) != null;
	}
	
	public Vertex getVertex(Point label) {
		return vertices.get(label);
	}
	
	public boolean addVertex(Vertex vertex) {
		Vertex v = this.vertices.get(vertex.getLabel());
		if(v != null) {
			return false;
		}
		vertices.put(vertex.getLabel(), vertex);
		return true;
	}
	
	public Vertex removeVertex(String label) {
		Vertex v = vertices.remove(label);
		while(v.getNeighborCount() > 0) {
			this.removeEdge(v.getNeighbor(0));
		}
		return v;
	}
	
	public Set<Point> getVertices(){
		return this.vertices.keySet();
	}
	
	public Set<Edge> getEdges(){
		return new HashSet<Edge>(this.edges.values());
	}
	
	public int getVerticesCount() {
		return vertices.size();
	}
	
	public int getEdgeCount() {
		return edges.size();
	}
	
	
}
