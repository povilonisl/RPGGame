package test;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.LinkedList;

import util.graph.Vertex;
import visibilityGraph.*;

public class TestVisibilityGraph {

	public static void main(String[] args) {

		FindPath fp = new FindPath();

		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		polygons.add(new Polygon(new int[] { 100, 150, 170 }, new int[] { 100, 200, 100 }, 3));

		// fp.shortestPath(polygons, new Point(50, 125), new Point(250, 125));

		LinkedList<Vertex> sortedVertices = fp.sortVertices(new Vertex(new Point(50, 125)), polygons);
		System.out.println(sortedVertices.size());
		for(Vertex v : sortedVertices) {
			System.out.println(v);
		}
	}

}
