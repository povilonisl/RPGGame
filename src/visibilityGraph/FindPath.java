package visibilityGraph;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import util.graph.Edge;
import util.graph.Graph;
import util.graph.Vertex;

/**
 * Implementation of what it seems to be Lees Visibility Graph.
 * 
 * @author 0VOID0
 *
 */
public class FindPath {

	private final int INFINITY = 1000000;

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
				Vertex temp = new Vertex(new Point(tempPoly.xpoints[j], tempPoly.ypoints[j]));
				temp.setPolygonID(i);
				vertices.add(temp);
			}
		}
		Graph g = new Graph(vertices);

		Vertex v;
		Set<Vertex> visibleVertices;
		Vertex prev = null;
		Vertex prevVisible = null;

		for (Point label : g.getVertices()) {
			v = g.getVertex(label);

			visibleVertices = visibleVertices(v, polygons);
			for (Vertex w : visibleVertices) {
				g.addEdge(v, w, 1);
			}
		}

		return g;
	}

	public Set<Vertex> visibleVertices(Vertex point, ArrayList<Polygon> polygons) {
		LinkedList<VertexKey> sortedVertices = sortVertices(point, polygons);
		Vertex v = null;
		for (int i = 0; i < sortedVertices.size(); i++) {
			if (sortedVertices.get(i).getV().equals(point)) {
				v = sortedVertices.get(i).getV();
				sortedVertices.remove(i);
				break;
			}
		}

		LinkedList<EdgeKey> sortedEdgeKeys = sortEdges(v, sortedVertices);
		LinkedList<Edge> sortedEdges = new LinkedList<Edge>();
		for (EdgeKey ek : sortedEdgeKeys) {
			sortedEdges.add(ek.getEdge());
		}

		Set<Vertex> visibleVertices = new HashSet<Vertex>();

		for (int i = 0; i < sortedVertices.size(); i++) {
			Vertex w = sortedVertices.get(i).getV();

			if (v.getNeighborCount() > 0
					&& (v.getNeighbor(0).getNeighbor(v).equals(w) || v.getNeighbor(1).getNeighbor(v).equals(w))) {
				visibleVertices.add(w);

			} else if ((visible(v, sortedVertices.get(i), sortedEdgeKeys) && !crossingNumber(v, w, sortedEdges))) {
				visibleVertices.add(w);
			}

			for (Edge e : w.getNeighbors()) {

				if (ccw(v, w, e.getNeighbor(w)) >= 0) {
					removeEdge(e, sortedEdgeKeys);
				}

				if (ccw(v, w, e.getNeighbor(w)) == -1) {
					double angleBetweenEdges = calculateVectorAngle(v.getLabel().x, v.getLabel().y, w.getLabel().x,
							w.getLabel().y, e.getNeighbor(w).getLabel().x, e.getNeighbor(w).getLabel().y);
					EdgeKey ek = new EdgeKey(sortedVertices.get(i).getDis(), sortedVertices.get(i).getAng(),
							angleBetweenEdges, e);
					addEdge(ek, sortedEdgeKeys);
					/*
					 * for(int j = 0; j < sortedEdgeKeys.size(); j++) {
					 * if(sortedEdgeKeys.get(j).getEdge().equals(e)) { sortedEdgeKeys.remove(j);
					 * break; } }
					 * 
					 * if(sortedEdgeKeys.get(0).getAng() == ek.getAng() &&
					 * sortedEdgeKeys.get(0).getDis() == ek.getDis()) {
					 * if(sortedEdgeKeys.get(0).compareTo(ek) == 1) { sortedEdgeKeys.add(0, ek);
					 * }else { sortedEdgeKeys.add(1,ek); } }else { sortedEdgeKeys.add(0,ek); }
					 */
				}
			}

		}
		return visibleVertices;
	}

	private void addEdge(EdgeKey ek, LinkedList<EdgeKey> sortedEdgeKeys) {
		int i = 0;
		boolean toAdd = true;
		while (sortedEdgeKeys.size() > i) {
			int compare = sortedEdgeKeys.get(i).compareTo(ek);
			if (compare > 0) {
				break;
			} else if (compare == 0) {
				return;
			}
			i++;
		}

		if (toAdd)
			sortedEdgeKeys.add(i, ek);
	}

	private void removeEdge(Edge e, LinkedList<EdgeKey> sortedEdges) {
		for (int i = 0; i < sortedEdges.size(); i++) {
			if (sortedEdges.get(i).getEdge().equals(e)) {
				sortedEdges.remove(i);
				break;
			}
		}
	}

	public LinkedList<VertexKey> sortVertices(Vertex v, ArrayList<Polygon> polygons) {
		LinkedList<VertexKey> sortedVertices = new LinkedList<VertexKey>();

		int vertexX = v.getLabel().x;
		int vertexY = v.getLabel().y;

		int tempX;
		int tempY;

		double angle;
		double dis;

		for (int p = 0; p < polygons.size(); p++) {
			LinkedList<Vertex> polyVertices = new LinkedList<Vertex>();
			for (int i = 0; i < polygons.get(p).npoints; i++) {
				Vertex temp = new Vertex(new Point(polygons.get(p).xpoints[i], polygons.get(p).ypoints[i]));
				temp.setPolygonID(p);
				polyVertices.add(temp);
			}

			for (int i = 0; i < polygons.get(p).npoints; i++) {
				tempX = polygons.get(p).xpoints[i];
				tempY = polygons.get(p).ypoints[i];

				dis = calculateDistance(vertexX, vertexY, tempX, tempY);

				// calculate the angle between the Vertex found and the point v
				angle = calculateAngle(vertexX, vertexY, tempX, tempY);

				int j = 0;

				// find the correct place for the vertex
				while (sortedVertices.size() > j) {
					if (sortedVertices.get(j).getAng() > angle) {
						break;
					} else if (sortedVertices.get(j).getAng() == angle) {
						if (sortedVertices.get(j).getDis() > dis) {
							break;
						}
					}
					j++;
				}
				Vertex tempVertex = polyVertices.get(i);
				if (i + 1 < polyVertices.size()) {
					Edge tempEdge = new Edge(tempVertex, polyVertices.get(i + 1));
					tempVertex.addNeighbor(tempEdge);
					polyVertices.get(i + 1).addNeighbor(tempEdge);
				} else {
					if (!tempVertex.equals(polyVertices.get(0))) {
						Edge tempEdge = new Edge(tempVertex, polyVertices.get(0));
						tempVertex.addNeighbor(tempEdge);
						polyVertices.get(0).addNeighbor(tempEdge);
					}
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

					Point intersection = edge
							.intersectsAt(new Edge(centre, new Vertex(new Point(INFINITY, centre.getLabel().y))));

					/*
					 * if(intersection != null && (intersectsCorners(intersection.x, intersection.y,
					 * edge.getOne().getLabel().x, edge.getOne().getLabel().y) ||
					 * intersectsCorners(intersection.x, intersection.y, edge.getTwo().getLabel().x,
					 * edge.getTwo().getLabel().y))) { intersection = null; }
					 */

					// check if this edge is pierced by y=0 line. this is wrong. I need another way
					// to check
					if (intersection != null && ccw(centre, new Vertex(new Point(INFINITY, centre.getLabel().x)),
							edge.getNeighbor(tempV)) == 1) {
						// find the intersection point of the edge with y=0 line
						// Create a new edge that starts at centre and ends at farthest x of the edge
						// and y = 0;

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

	/*
	 * intersectsCorners(intersection.x, intersection.y, e.getOne().getLabel().x,
	 * e.getOne().getLabel().y)
	 * 
	 * intersection.x == e.getOne().getLabel().x && intersection.y ==
	 * e.getOne().getLabel().y || intersection.x == e.getTwo().getLabel().x &&
	 * intersection.y == e.getTwo().getLabel().y
	 */
	public boolean visible(Vertex v, VertexKey wk, LinkedList<EdgeKey> sortedEdgeKeys) {
		if (sortedEdgeKeys.isEmpty())
			return true;
		else {
			for (EdgeKey ek : sortedEdgeKeys) {
				Point intersection = ek.getEdge().intersectsAt(new Edge(v, wk.getV()));
				if (intersection != null) {
					if (intersectsCorners(intersection.x, intersection.y, ek.getEdge().getOne().getLabel().x,
							ek.getEdge().getOne().getLabel().y)
							|| intersectsCorners(intersection.x, intersection.y, ek.getEdge().getTwo().getLabel().x,
									ek.getEdge().getTwo().getLabel().y)) {
						continue;
					} else {
						return false;
					}
				}
			}
			return true;
		}
	}

	private boolean intersectsCorners(int x1, int y1, int x2, int y2) {
		return x1 == x2 && y1 == y2;
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

	private double calculateAngle(int x1, int y1, int x2, int y2) {
		double arctan = Math.atan(Math.abs(y2 - y1) / ((double) Math.abs(x2 - x1)));

		if (Math.signum(y2 - y1) == 1) {
			if (Math.signum(x2 - x1) == 1) {
				arctan = 2 * Math.PI - arctan;
			} else {
				arctan += Math.PI;
			}
		} else if (Math.signum(x2 - x1) == -1) {
			arctan = Math.PI - arctan;
		}

		return arctan;
	}

	/**
	 * 
	 * 
	 * @param v1
	 *            centre
	 * @param v2
	 *            focus vertex
	 * @param v3
	 *            vertex that is connected by the edge from focus
	 * @return 1 if counter clockwise, -1 if clock wise
	 */
	public int ccw(Vertex v1, Vertex v2, Vertex v3) {
		int area = 0;
		area = ((v2.getLabel().x - v1.getLabel().x) * (v3.getLabel().y - v1.getLabel().y)
				- (v2.getLabel().y - v1.getLabel().y) * (v3.getLabel().x - v1.getLabel().x));
		if (area > 0)
			return 1;
		if (area < 0)
			return -1;
		return 0;
	}

	public boolean crossingNumber(Vertex v1, Vertex v2, LinkedList<Edge> sortedEdges) {
		if (v1.getPolygonID() == -1 || v2.getPolygonID() == -1)
			return false;

		if (v1.getPolygonID() == v2.getPolygonID()) {
			LinkedList<Edge> polyEdges = new LinkedList<Edge>();
			for (Edge e : sortedEdges) {
				if (e.getOne().getPolygonID() == v1.getPolygonID())
					polyEdges.add(e);
			}

			Vertex midPoint = new Vertex(
					new Point((v1.getLabel().x + v2.getLabel().x) / 2, (v1.getLabel().y + v2.getLabel().y) / 2));
			Vertex midPoint2 = new Vertex(new Point(INFINITY, midPoint.getLabel().y));

			int intersectionCount = 0;
			boolean co_flag = false;
			int co_dir = 0;

			for (Edge edge : polyEdges) {
				if (midPoint.getLabel().y < edge.getOne().getLabel().y
						&& midPoint.getLabel().y < edge.getTwo().getLabel().y)
					continue;
				if (midPoint.getLabel().y > edge.getOne().getLabel().y
						&& midPoint.getLabel().y > edge.getTwo().getLabel().y)
					continue;

				boolean co0 = (ccw(midPoint, edge.getOne(), midPoint2) == 0)
						&& (edge.getOne().getLabel().x > midPoint.getLabel().x);
				boolean co1 = (ccw(midPoint, edge.getTwo(), midPoint2) == 0)
						&& (edge.getTwo().getLabel().x > midPoint.getLabel().x);
				Vertex co_point = null;

				if (co0)
					co_point = edge.getOne();
				else
					co_point = edge.getTwo();

				if (co0 || co1) {
					if (edge.getNeighbor(co_point).getLabel().y > midPoint.getLabel().y) {
						co_dir += 1;
					} else {
						co_dir -= 1;
					}

					if (co_flag) {
						if (co_dir == 0) {
							intersectionCount += 1;
						}
						co_flag = false;
						co_dir = 0;
					} else {
						co_flag = true;
					}

				} else {
					Point p = edge.intersectsAt(new Edge(midPoint, midPoint2));
					if (p != null) {
						intersectionCount += 1;
					}
				}
			}

			if (intersectionCount % 2 == 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean edge_intersect(Vertex v11, Vertex v12, Edge edge) {
		Vertex v21 = edge.getOne();
		Vertex v22 = edge.getTwo();
		int o1 = ccw(v11, v12, v21);
		int o2 = ccw(v11, v12, v22);
		int o3 = ccw(v21, v22, v11);
		int o4 = ccw(v21, v22, v12);

		if (o1 != o2 && o3 != o4) {
			return true;
		}
		if (o1 == 0) {

		}

		return false;
	}
}
