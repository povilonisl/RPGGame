package path;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Cell {

	public double f = -1;
	public int g = 0;
	public double h = 0;
	public int x = 0;
	public int y = 0;
	public Cell previous = null;
	public boolean wall = false;
	public LinkedList<Cell> neighbors = new LinkedList<Cell>();
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		
		Random a; 
		a = new Random();
		if(a.nextDouble() < 0.40) {
			wall = true;
		}
	}
	
	public void render(Graphics g, Color color) {
		if (wall == true) {
			g.setColor(Color.BLACK);
		}else {
			g.setColor(color);
		}
		
		g.fillRect(x*Class1.w, y*Class1.h, Class1.w, Class1.h);

		g.setColor(Color.BLACK);
		g.drawRect(x*Class1.w, y*Class1.h, Class1.w, Class1.h);
		
	}
	
	public void addNeighbors(Cell[][] grid) {
		if(y > 0) neighbors.add(grid[x][y - 1]);
		if(x < Class1.rows - 1) neighbors.add(grid[x + 1][y]);
		if(y < Class1.cols - 1) neighbors.add(grid[x][y + 1]);
		if(x > 0) neighbors.add(grid[x - 1][y]);
		if(x>0 && y >0) neighbors.add(grid[x-1][y-1]);
		if(x<Class1.rows-1 && y>0) neighbors.add(grid[x+1][y-1]);
		if(x < Class1.rows-1 && y < Class1.cols -1) neighbors.add(grid[x+1][y+1]);
		if(x > 0 && y < Class1.cols - 1) neighbors.add(grid[x-1][y+1]);

	}

	@Override
	public boolean equals(Object c) {
		return (x== ((Cell) c).x && y ==((Cell) c).y);
	}
	
	
}
