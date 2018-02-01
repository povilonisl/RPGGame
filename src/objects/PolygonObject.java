package objects;

import java.util.ArrayList;
import java.util.List;

import main.ID;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;

public class PolygonObject extends GameObject {
	private int[] xCord;
	private int[] yCord;
	private Polygon polygon;
	private int noOfSides;

	public PolygonObject(List<Integer> xCord, List<Integer> yCord, int n, ID id) {
		super(0, 0, id);
		noOfSides = n;
		this.xCord = new int[n];
		this.yCord = new int[n];
		for(int i = 0; i<n; i++) {
			this.xCord[i] = xCord.get(i);
			this.yCord[i] = yCord.get(i);
		}
		
		polygon = new Polygon(this.xCord, this.yCord, noOfSides);
	}
	
	public PolygonObject(int[] xCord, int[] yCord, int n, ID id) {
		super(0, 0, id);
		noOfSides = n;
		this.xCord = xCord;
		this.yCord = yCord;
		
		polygon = new Polygon(this.xCord, this.yCord, noOfSides);
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.draw(polygon);
	}

	@Override
	public Area bounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
