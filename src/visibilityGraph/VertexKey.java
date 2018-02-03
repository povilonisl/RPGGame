package visibilityGraph;

import util.graph.Vertex;

public class VertexKey implements Comparable<VertexKey> {
	
	private double ang;
	private Vertex v;
	private double dis;
	
	public VertexKey(Double ang, Double dis, Vertex v1) {
		this.ang = ang;
		this.v = v1;
		this.dis = dis;
	}
	
	public double getAng() {
		return ang;
	}

	public Vertex getV() {
		return v;
	}

	public double getDis() {
		return dis;
	}
	
	@Override
	public int compareTo(VertexKey vk) {
		if(vk.getAng() > this.ang) {
			return -1;
		}else if (vk.getAng() < this.ang) {
			return 1;
		}else {
			if(vk.getDis() > this.dis) {
				return -1;
			}else if(vk.getDis() < this.dis) {
				return 1;
			}else {
				return 0;
			}
		}
	}


}
