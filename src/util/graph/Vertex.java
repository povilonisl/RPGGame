package util.graph;

import java.awt.Point;
import java.util.ArrayList;

public class Vertex {

	private ArrayList<Edge> neighbors;
	//private String label;
	private Point label;
	private int polygonID;
	
	public Vertex(Point label) {
		this.label = label;
		this.neighbors = new ArrayList<Edge>();
		this.polygonID = -1;
	}
	
	public int getPolygonID() {
		return polygonID;
	}

	public void setPolygonID(int polygonID) {
		this.polygonID = polygonID;
	}
	
	public void addNeighbor(Edge edge) {
		if(this.neighbors.contains(edge)) {
			return;
		}
		this.neighbors.add(edge);
	}
	
	public boolean hasNeighbor(Edge edge) {
		return this.neighbors.contains(edge);
	}
	
	public Edge getNeighbor(int i) {
		return this.neighbors.get(i);
	}
	
	public void deleteNeighbor(Edge edge) {
		this.neighbors.remove(edge);
	}
	
	public int getNeighborCount() {
		return this.neighbors.size();
	}
	
	public Point getLabel() {
		return this.label;
	}
	
	public ArrayList<Edge> getNeighbors(){
		return new ArrayList<Edge>(this.neighbors);
	}
	
	@Override
	public String toString() {
		return "(" + label.x + ", " + label.y + ")";
	}
	
	@Override
	public int hashCode() {
		return this.label.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Vertex)) {
			return false;
		}
		Vertex v = (Vertex) other;
		return this.label.equals(v.label);
	}
}
