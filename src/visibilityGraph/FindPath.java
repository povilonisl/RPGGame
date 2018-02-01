package visibilityGraph;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import objects.PolygonObject;
import util.bst.Bst;
import util.graph.*;

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
		Bst obstacleEdges = findObstacleEdges(v, polygons);
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
				sortedVertices.add(j, new Vertex(new Point(tempX, tempY)));

			}
		}

		return sortedVertices;
	}
	
	public Bst findObstacleEdges(Vertex v, ArrayList<Polygon> polygons) {
		for(Polygon p : polygons) {
			for(int i = 0; i < p.npoints; i++) {
				int i = p.xpoints[i];
			}
		}
		return null;
	}
	
	public boolean visible(Vertex w) {
		return false;
	}
}
