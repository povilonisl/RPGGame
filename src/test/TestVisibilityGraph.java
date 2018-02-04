package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.bst.Bst;
import util.bst.Empty;
import util.graph.Edge;
import util.graph.Graph;
import util.graph.Vertex;
import visibilityGraph.*;

public class TestVisibilityGraph extends JPanel{

	ArrayList<Polygon> polygons= null;
	Graph graph = null;
	
	public TestVisibilityGraph(){
		FindPath fp = new FindPath();
		polygons = new ArrayList<Polygon>();
		//polygons.add(new Polygon(new int[] { 100, 150, 170 }, new int[] { 100, 400, 100  }, 3));
		polygons.add(new Polygon(new int[] { 200, 600, 500, 300 }, new int[] { 300, 300, 400, 400 }, 4));
		//polygons.add(new Polygon(new int[] { 180, 240, 240 }, new int[] { 300, 300, 350  }, 3));
		Vertex start = new Vertex(new Point(400, 100));
		//Vertex goal = new Vertex(new Point(300, 125));
		polygons.add(new Polygon(new int[] { start.getLabel().x }, new int[] { start.getLabel().y }, 1));
		//polygons.add(new Polygon(new int[] { goal.getLabel().x }, new int[] { goal.getLabel().y }, 1));
		graph = fp.visbilityGraph(polygons);
		for(Edge e : graph.getEdges()) {
			System.out.println(e);
		}
		
		JFrame frame = new JFrame();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 600);
		frame.add(this);
		frame.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.BLACK);
		for(Polygon p : polygons) {
			for(int i = 0; i < p.npoints; i++) {
				if(i+1 >= p.npoints) {
					g2d.drawLine(p.xpoints[i], p.ypoints[i], p.xpoints[0], p.ypoints[0]);
				}else {
					g2d.drawLine(p.xpoints[i], p.ypoints[i], p.xpoints[i+1], p.ypoints[i+1]);
				}
			}
		}
		
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLUE);
		for(Point point : graph.getVertices()) {
			g2d.drawOval(point.x-5, point.y-5, 10, 10);
		}
		
		g2d.setColor(Color.GREEN);
		for(Edge edge : graph.getEdges()) {
			g2d.drawLine(edge.getOne().getLabel().x, edge.getOne().getLabel().y, edge.getTwo().getLabel().x, edge.getTwo().getLabel().y);
		}
		
		g2d.setColor(Color.BLUE);
		for(Point point : graph.getVertices()) {
			g2d.drawString("("+point.x + ", " + point.y+")", point.x-10, point.y -10);
		}
		
	}
	
	public static void main(String[] args) {
		
		TestVisibilityGraph testVG = new TestVisibilityGraph();

/*		FindPath fp = new FindPath();

		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		polygons.add(new Polygon(new int[] { 100, 150, 170 }, new int[] { 100, 200, 100 }, 3));
		
		Vertex start = new Vertex(new Point(50, 125));
		Vertex goal = new Vertex(new Point(300, 125));*/

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
		
		/*Edge e1 = new Edge(new Vertex(new Point(0, 3)), new Vertex(new Point(2, 4)));
		Edge e2 = new Edge(new Vertex(new Point(0, 4)), new Vertex(new Point(4, 2)));
		System.out.println(e1.intersectsAt(e2));*/
		
/*		LinkedList<VertexKey> sortedVertices = fp.sortVertices(start, polygons);
		LinkedList<EdgeKey> sortedEdges = fp.sortEdges(start, sortedVertices);
		System.out.println("number of vertices: " + sortedVertices.size());
		System.out.println("number of edges: " + sortedEdges.size());
		for(EdgeKey ek : sortedEdges) {
			System.out.println("e: "+ ek.getEdge() + ", ang: " + ek.getAng() + ", dis: " + ek.getDis());
		}
		
		System.out.println(fp.ccw(start, new Vertex(new Point(170, 100)), new Vertex(new Point(150, 200))));
		System.out.println("");
		
		System.out.println(fp.visibleVertices(start, polygons));
		
		System.out.println("Graph edges: ");
		polygons.add(new Polygon(new int[] { start.getLabel().x }, new int[] { start.getLabel().y }, 1));
		polygons.add(new Polygon(new int[] { goal.getLabel().x }, new int[] { goal.getLabel().y }, 1));
		Graph g = fp.visbilityGraph(polygons);
		for(Edge e : g.getEdges()) {
			System.out.println(e);
		}*/
	}

}
