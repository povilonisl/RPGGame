package objects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import main.Handler;
import main.ID;

public class Player extends GameObject{

	private Handler handler;
	private Ellipse2D.Double shape;
	private Area a;
	public static final int pWIDTH = 35, pHEIGHT = 35;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		shape = new Ellipse2D.Double(x, y, pWIDTH, pHEIGHT);
		a = new Area();
	}
	
	@Override
	public void tick() {
		if(collide()) {
			
		}
		
		valX = (int) (Math.min(Math.abs(targetX-x), 3)*Math.signum(targetX-x));
		valY = (int) (Math.min(Math.abs(targetY-y), 3)*Math.signum(targetY-y));
		
		x += valX;
		y += valY;
		
	}
	
	private boolean collide() {
		boolean collide = false;
		for(int i = 0; i < handler.sizeObjects(); i++) {
			GameObject tempObject = handler.getObject(i);
			if(tempObject.getId() == ID.Tree) {
				a = new Area(shape);
				a.intersect(tempObject.bounds());
				collide = collide || !a.isEmpty();
				
				if (collide == true) return collide;
			}
		}
		
		return collide;
	}


	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		shape.x = x;
		shape.y = y;
		shape.width = pWIDTH;
		shape.height = pHEIGHT;
		g2d.fill(shape);
	}

	@Override
	public Area bounds() {
		return new Area(shape);
	}

}
