package util.graph;

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
		return edge.equals(this.one) && edge.two.equals(this.two);
	}
}
