package objects;
import java.awt.Graphics;
import java.awt.geom.Area;

import main.ID;

public abstract class GameObject {

	// protected means that it can be only accessed by the object that inherits it
	protected int x, y; //location of the object
	protected ID id; //id of the object
	protected int valX =0, valY=0; //velocity of the object
	protected int targetX, targetY; //where the object should be
	
	/**
	 * Information about the objects in the game
	 * 
	 * @param x x-coordinate of the current location of the object
	 * @param y y-coordinate of the current location of the object
	 * @param width the width of the object
	 * @param height the height of the object
	 * @param id the ID of the object
	 */
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public abstract Area bounds();	


	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}


	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getValX() {
		return valX;
	}

	public void setValX(int valX) {
		this.valX = valX;
	}

	public int getValY() {
		return valY;
	}

	public void setValY(int valY) {
		this.valY = valY;
	}


}
