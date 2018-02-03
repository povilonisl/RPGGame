package visibilityGraph;

import util.graph.Edge;
import util.graph.Vertex;

public class EdgeKey implements Comparable<EdgeKey> {

	private double dis;
	private double ang;
	private double ang2;
	private Edge edge;

	public EdgeKey(double dis, double ang, double ang2, Edge edge) {
		this.dis = dis;
		this.ang = ang;
		this.ang2 = ang2;
		this.edge = edge;
	}

	/**
	 * This compared to another ek if this has higher priority then return value
	 * will be -1, meaning ek has lower priority if this has lower priority then
	 * return value will be 1, meaning ek has higher priority, otherwise 0
	 */
	@Override
	public int compareTo(EdgeKey ek) {
		if (ek.getEdge().equals(this.edge)) {
			return 0;
		}

		if (compareVar(ek.ang, this.ang) == 0) {
			if (compareVar(ek.dis, this.dis) == 0) {
				return compareVar(ek.ang2, this.ang2);
			} else {
				return compareVar(ek.dis, this.dis);
			}
		} else {
			return compareVar(ek.ang, this.ang);
		}
	}

	private int compareVar(double n1, double n2) {
		if (n1 > n2) {
			return -1;
		} else if (n1 < n2) {
			return 1;
		} else {
			return 0;
		}
	}

	public double getDis() {
		return dis;
	}

	public double getAng() {
		return ang;
	}

	public double getAng2() {
		return ang2;
	}

	public Edge getEdge() {
		return edge;
	}
}
