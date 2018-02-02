package visibilityGraph;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import util.bst.*;
import util.graph.*;

/** 
 * Implementation of what it seems to be Lees Visibility Graph.
 * 
 * @author 0VOID0
 *
 */
public class FindPath {
	public FindPath() {

	}

	public void shortestPath(ArrayList<Polygon> polygons, Point start, Point goal) {
		polygons.add(new Polygon(new int[] { start.x }, new int[] { start.y }, 1));
		polygons.add(new Polygon(new int[] { goal.x }, new int[] { goal.y }, 1));
		Graph gVis = visbilityGraph(polygons);

	}

	public Graph visbilityGraph(ArrayList<Polygon> polygons) {
		Polygon tempPoly;
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for (int i = 0; i < polygons.size(); i++) {
			tempPoly = polygons.get(i);
			for (int j = 0; j < tempPoly.npoints; j++) {
				// vertices.add(new Vertex("(" + tempPoly.xpoints[j] + ", " +
				// tempPoly.ypoints[j] +")"));
				vertices.add(new Vertex(new Point(tempPoly.xpoints[j], tempPoly.ypoints[j])));
			}
		}
		Graph g = new Graph(vertices);

		Vertex v;
		Set<Vertex> visibleVertices;
		for (Point s : g.getVertices()) {
			// System.out.println(s);
			v = g.getVertex(s);
			visibleVertices = visibleVertices(v, polygons);
			for (Vertex w : visibleVertices) {
				g.addEdge(v, w, 1);
			}
		}

		return null;
	}

	public Set<Vertex> visibleVertices(Vertex v, ArrayList<Polygon> polygons) {
		LinkedList<Vertex> sortedVertices = sortVertices(v, polygons);
		Bst obstacleEdges = findObstacleEdges(sortedVertices);
		Set<Vertex> visibleEdges = new HashSet<Vertex>();

		for (int i = 0; i < sortedVertices.size(); i++) {
			Vertex w = sortedVertices.get(i);
			if (visible(w)) {
				visibleEdges.add(w);
			}

		}

		return null;
	}

	public LinkedList<Vertex> sortVertices(Vertex v, ArrayList<Polygon> polygons) {
		LinkedList<Vertex> sortedVertices = new LinkedList<Vertex>();
		LinkedList<Double> sortedAngles = new LinkedList<Double>();
		
		int vertexX = v.getLabel().x;
		int vertexY = v.getLabel().y;

		int tempX;
		int tempY;

		double angle;

		for (Polygon p : polygons) {
			LinkedList<Vertex> polyVertices = new LinkedList<Vertex>();
			for(int i = 0; i < p.npoints; i++) {
				polyVertices.add(new Vertex(new Point(p.xpoints[i],  p.ypoints[i])));
			}
			
			for (int i = 0; i < p.npoints; i++) {
				tempX = p.xpoints[i];
				tempY = p.ypoints[i];

				angle = Math.atan(Math.abs(tempY - vertexY) / ((double) Math.abs(tempX - vertexX)));

				if (Math.signum(-(tempY - vertexY)) == -1) {
					angle += Math.PI;
					if (Math.signum(tempX - vertexX) == 1) {
						angle += Math.PI / 2;
					}
				} else if (Math.signum(tempX - vertexX) == -1) {
					angle += Math.PI / 2;
				}

				int j = 0;

				while (sortedAngles.size() > j) {
					if (sortedAngles.get(j) > angle)
						break;
					j++;
				}
				sortedAngles.add(j, angle);
				Vertex tempVertex = polyVertices.get(i);
				if(i+1 < polyVertices.size()) {
					Edge tempEdge = new Edge(tempVertex, polyVertices.get(i+1));
					tempVertex.addNeighbor(tempEdge);
					polyVertices.get(i+1).addNeighbor(tempEdge);
				}else {
					Edge tempEdge = new Edge(tempVertex, polyVertices.get(0));
					tempVertex.addNeighbor(tempEdge);
					polyVertices.get(0).addNeighbor(tempEdge);
				}
				sortedVertices.add(j, tempVertex);

			}
		}
		
		return sortedVertices;
	}
	
	public Bst<Integer, Edge> findObstacleEdges(LinkedList<Vertex> sortedVertices) {
		Set<Edge> lookOut = new HashSet<Edge>();
		Bst<Integer, Edge> bst = new Empty<Integer, Edge>();
		
		for(int i = 0; i < sortedVertices.size(); i++) {
			Set<Edge> toRemove = new HashSet<Edge>();
			//Check if the current vertex belongs to any edge in the lookOut, add them to a list of edges to remove
			//Edges have to be removed after new edges are added, otherwise deleted edges will be just added again.
			//We want to remove edges from lookOut so that it decreases the number of edges we have to search each time.
			//We cannot search for which edges should remove after we add new edges because then we will delete newly added edges.
			for(Edge e : lookOut) {
				if(e.getOne().equals(sortedVertices.get(i)) || e.getTwo().equals(sortedVertices.get(i))) {
					toRemove.add(e);
					bst = bst.put(e.hashCode(), e);
				}
			}
			
			//add all edges that are connected to current vertex
			for(Edge e : sortedVertices.get(i).getNeighbors()) {
				lookOut.add(e);
			}
			
			//Remove the edges from Lookout
			for(Edge e : toRemove) {
				lookOut.remove(e);
			}
			
		}
		return bst;
	}
	
	public boolean visible(Vertex w) {
		return false;
	}
	
	public Point calculateIntersection(Edge e1, Edge e2) {
		
		
		
		return null;
		
	}
}
