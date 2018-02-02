package util.graph;

import java.awt.Point;

public class Edge implements Comparable<Edge> {
	private Vertex one, two;
	private int weight;

	public Edge(Vertex one, Vertex two) {
		this.one = one;
		this.two = two;
		this.weight = 1;
	}

	public Edge(Vertex one, Vertex two, int weight) {
		this.one = one;
		this.two = two;
		this.weight = weight;
	}

	public Vertex getNeighbor(Vertex current) {
		if (!(current.equals(one) || current.equals(two))) {
			return null;
		}
		return (current.equals(one)) ? two : one;
	}

	public Vertex getOne() {
		return this.one;
	}

	public Vertex getTwo() {
		return this.two;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Point intersectsAt(Edge e) {
		double m = (this.two.getLabel().y - this.one.getLabel().y)/((double) this.two.getLabel().x - this.one.getLabel().x); //this gradient
		double r = (e.two.getLabel().y - e.one.getLabel().y)/((double) e.two.getLabel().x - e.one.getLabel().x); //e gradient
		double c = this.one.getLabel().y - m*this.one.getLabel().x; //this y-intersection
		double b = e.one.getLabel().y - r*e.one.getLabel().x;//e y-intersection
		
		int intersectionX = (int) Math.round((c-b)/(r-m));
		int intersectionY = (int) Math.round(r*(intersectionX) + b);
		
		boolean valid = between(this.one.getLabel().x, intersectionX, this.two.getLabel().x) && between(this.one.getLabel().y, intersectionY, this.two.getLabel().y)
				&& between(e.one.getLabel().x, intersectionX, this.two.getLabel().x) && between(e.one.getLabel().x, intersectionX, this.two.getLabel().x);
		
		if(valid) return new Point(intersectionX, intersectionY);
		return null;
	}

	private boolean between(double bound1, double point, double bound2) {
		if ((bound1 <= point && point <= bound2) || (bound1 <= point && point <= bound2)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Edge edge) {
		return this.weight - edge.weight;
	}

	@Override
	public String toString() {
		return "({" + one + ", " + two + "}, " + weight + ")";
	}

	@Override
	public int hashCode() {
		return (one.getLabel().toString() + two.getLabel().toString()).hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Edge)) {
			return false;
		}
		Edge edge = (Edge) other;
		return edge.one.equals(this.one) && edge.two.equals(this.two);
	}
}
