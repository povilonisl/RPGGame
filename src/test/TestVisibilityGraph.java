package test;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.LinkedList;

import util.bst.Bst;
import util.bst.Empty;
import util.graph.Edge;
import util.graph.Vertex;
import visibilityGraph.*;

public class TestVisibilityGraph {

	public static void main(String[] args) {

		FindPath fp = new FindPath();

		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		polygons.add(new Polygon(new int[] { 100, 150, 170 }, new int[] { 100, 200, 100 }, 3));

		// fp.shortestPath(polygons, new Point(50, 125), new Point(250, 125));

/*		LinkedList<Vertex> sortedVertices = fp.sortVertices(new Vertex(new Point(50, 125)), polygons);
		Bst<Integer, Edge> edgeTree = fp.findObstacleEdges(sortedVertices);
		System.out.println(sortedVertices.size());
		for(Vertex v : sortedVertices) {
			System.out.println(v);
		}
		edgeTree = edgeTree.balanced();
		System.out.println(edgeTree.fancyToString());
		edgeTree.printInOrder();

		
		Bst<Integer, String> testBST = new Empty<>();
		testBST = testBST.put(0, "Hi");
		testBST = testBST.put(1, "a");
		testBST = testBST.put(3, "asd");
		testBST = testBST.balanced();
		System.out.println(testBST.fancyToString());
		testBST.printInOrder();*/
		
		Edge e1 = new Edge(new Vertex(new Point(0, 3)), new Vertex(new Point(2, 4)));
		Edge e2 = new Edge(new Vertex(new Point(0, 4)), new Vertex(new Point(4, 2)));
		System.out.println(e1.intersectsAt(e2));
	}

}
