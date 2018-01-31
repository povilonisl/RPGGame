package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import main.Game;
import main.ID;

public class Tree extends GameObject {

	private int maxWidth = 45, maxHeight = 45;
	private double rate = 0.0;
	private double gWidth, gHeight; //growth width and height, since gameObject width and height are int
	private Ellipse2D.Double shape;
	
	public Tree(int x, int y, int width, int height, ID id) {
		super(x, y, width, height, id);
		gWidth = width;
		gHeight = height;
		shape = new Ellipse2D.Double(x, y, width, height);
	}

	@Override
	public void tick() {
		rate = (maxWidth - gWidth)/(maxWidth*60);
		
		gWidth += rate;
		width = (int)gWidth;
		gHeight += rate;
		height = (int)gHeight;
		
		
		height = Game.clamp(height, 3, maxHeight);
		width = Game.clamp(width, 3, maxWidth);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		shape.x = x;
		shape.y = y;
		shape.width = width;
		shape.height = height;
		g2d.fill(shape);
	}
	
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public Area bounds() {
		return new Area(shape);
	}
	
}
