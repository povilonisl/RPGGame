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
		LinkedList<VertexKey> sortedVertices = sortVertices(v, polygons);
		LinkedList<EdgeKey> sortedEdges = sortEdges(v, sortedVertices);

		Set<Vertex> visibleVertices = new HashSet<Vertex>();

		for (int i = 0; i < sortedVertices.size(); i++) {
			Vertex w = sortedVertices.get(i).getV();
			if (visible(w)) {
				visibleVertices.add(w);
			}
			
			for(Edge e : sortedVertices.get(i).getV().getNeighbors()) {
				if(e.getNeighbor(sortedVertices.get(i).getV())) {
					
				}
				
				for(EdgeKey ek : sortedEdges) {
					
				}
			}
			
		}
		return null;
	}

	/**
	 * Return 1 if counter clockwise, -1 if clock wise
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 */
	public int ccw(Vertex v1, Vertex v2, Vertex v3) {
		int area = 0;
		    area = ((v2.getLabel().x - v1.getLabel().x) * (v3.getLabel().y - v1.getLabel().y) - (v2.getLabel().y - v1.getLabel().y) * (v3.getLabel().x - v1.getLabel().x));
		    if (area > 0) return 1;
		    if (area < 0) return -1;
		return 0;
	}


	public LinkedList<VertexKey> sortVertices(Vertex v, ArrayList<Polygon> polygons) {
		LinkedList<VertexKey> sortedVertices = new LinkedList<VertexKey>();

		int vertexX = v.getLabel().x;
		int vertexY = v.getLabel().y;

		int tempX;
		int tempY;

		double angle;
		double dis;

		for (Polygon p : polygons) {
			LinkedList<Vertex> polyVertices = new LinkedList<Vertex>();
			for (int i = 0; i < p.npoints; i++) {
				polyVertices.add(new Vertex(new Point(p.xpoints[i], p.ypoints[i])));
			}

			for (int i = 0; i < p.npoints; i++) {
				tempX = p.xpoints[i];
				tempY = p.ypoints[i];

				dis = calculateDistance(vertexX, tempX, vertexY, tempY);

				// calculate the angle between the Vertex found and the point v
				angle = calculateAngle(vertexX, tempX, vertexY, tempY);

				int j = 0;

				// find the correct place for the vertex
				while (sortedVertices.size() > j) {
					if (sortedVertices.get(j).getAng() > angle)
						break;
					j++;
				}
				Vertex tempVertex = polyVertices.get(i);
				if (i + 1 < polyVertices.size()) {
					Edge tempEdge = new Edge(tempVertex, polyVertices.get(i + 1));
					tempVertex.addNeighbor(tempEdge);
					polyVertices.get(i + 1).addNeighbor(tempEdge);
				} else {
					Edge tempEdge = new Edge(tempVertex, polyVertices.get(0));
					tempVertex.addNeighbor(tempEdge);
					polyVertices.get(0).addNeighbor(tempEdge);
				}
				sortedVertices.add(j, new VertexKey(angle, dis, tempVertex));
			}
		}

		return sortedVertices;
	}


	public LinkedList<EdgeKey> sortEdges(Vertex centre, LinkedList<VertexKey> sortedVertices) {
		LinkedList<EdgeKey> sortedEdgeKeys = new LinkedList<EdgeKey>();

		for (VertexKey vk : sortedVertices) {
			Vertex tempV = vk.getV();
			for (Edge edge : vk.getV().getNeighbors()) {

				// to check if this edge has already been added to the sortedEdgeKeys.
				Vertex temp = edge.getNeighbor(vk.getV());
				boolean nextEdge = false;

				for (int i = 0; i < sortedEdgeKeys.size(); i++) {
					if (sortedEdgeKeys.get(i).getEdge().equals(edge)) {
						nextEdge = true;
						break;
					}
				}
				if (!nextEdge) {

					EdgeKey ek;

					// check if this edge is pierced by y=0 line.
					if (edge.getNeighbor(tempV).getLabel().y > centre.getLabel().y) {
						// find the intersection point of the edge with y=0 line
						// Create a new edge that starts at centre and ends at farthest x of the edge
						// and y = 0;
						Point intersection = edge.intersectsAt(new Edge(centre, new Vertex(
								new Point(Math.max(edge.getNeighbor(tempV).getLabel().x, tempV.getLabel().x), centre.getLabel().y))));
						// find the distance from centre to intersection point.
						double dis = calculateDistance(centre.getLabel().x, centre.getLabel().y, intersection.x,
								intersection.y);
						ek = new EdgeKey(dis, 0, 0, edge);
					} else {
						// create ek and insert it into sortedEdgeKeys
						double angleBetweenEdges = calculateVectorAngle(centre.getLabel().x, centre.getLabel().y,
								tempV.getLabel().x, tempV.getLabel().y, edge.getNeighbor(tempV).getLabel().x,
								edge.getNeighbor(tempV).getLabel().y);
						ek = new EdgeKey(vk.getDis(), vk.getAng(), angleBetweenEdges, edge);
					}

					int i = 0;
					boolean toAdd = true;
					while (sortedEdgeKeys.size() > i) {
						int compare = sortedEdgeKeys.get(i).compareTo(ek);
						if (compare > 0) {
							break;
						}
						i++;
					}

					if (toAdd)
						sortedEdgeKeys.add(i, ek);
				}
			}
		}

		return sortedEdgeKeys;
	}

	public boolean visible(Vertex v, Vertex w, LinkedList<EdgeKey> sortedEdgeKeys) {
		return false;
	}


	private double calculateVectorAngle(int x1, int y1, int x2, int y2, int x3, int y3) {
		double[] v1 = new double[] { x2 - x1, y2 - y1 };
		double[] v2 = new double[] { x3 - x2, y3 - y2 };
		double angle = Math.acos((v1[0] * v2[0] + v1[1] * v2[1]) / (Math.sqrt(Math.pow(v1[0], 2) + Math.pow(v1[1], 2))
				* Math.sqrt(Math.pow(v2[0], 2) + Math.pow(v2[1], 2))));

		return angle;
	}
	
	private double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	private double calculateAngle(int x1, int x2, int y1, int y2) {
		double angle = Math.atan(Math.abs(y2 - y1) / ((double) Math.abs(x2 - x1)));

		if (Math.signum(-(y2 - y1)) == -1) {
			angle += Math.PI;
			if (Math.signum(x2 - x1) == 1) {
				angle += Math.PI / 2;
			}
		} else if (Math.signum(x2 - x1) == -1) {
			angle += Math.PI / 2;
		}

		return angle;
	}
}
