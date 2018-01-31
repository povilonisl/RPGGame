package path;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JPanel;

import main.Window;

public class Class1 extends Canvas implements Runnable{

	public static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
	
	

	private Thread thread;
	private boolean running = false;
	
	
	public static final int cols = 25;
	public static final int rows = 25;
	private Cell[][] grid = new Cell[cols][rows];
	
	public static final int w = (WIDTH - 16)/cols;
	public static final int h = (HEIGHT - 40)/rows; 
	
	private LinkedList<Cell> openSet = new LinkedList<Cell>();
	private LinkedList<Cell> closedSet = new LinkedList<Cell>();
	private Cell start;
	private Cell goal;
	private boolean finished = false;
	private boolean noSolution = false;
	
	private LinkedList<Cell> path = new LinkedList<Cell>();
	
	Cell current = null;
	
	public Class1() {
		
		new Window(WIDTH, HEIGHT, "Test A*", this);

		 for(int i = 0; i < cols; i++) {
			 for(int j = 0; j < rows; j++) {
				 grid[i][j] = new Cell(i, j);
			 }
		 }
		 
		 for(int i = 0; i < cols; i++) {
			 for(int j = 0; j < rows; j++) {
				 grid[i][j].addNeighbors(grid);
			 }
		 }
		 goal = grid[cols-1][rows - 1];
		 goal.wall = false;
		 
		 start = grid[0][0];
		 start.wall = false;
		 start.g = 0;
		 start.f = heuristic(start, goal);
		 
		 
		 openSet.add(start);
		 
	}
	
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
	this.requestFocus();
		
		long lastTime = System.nanoTime(); //start time to measure the amount of time passed
		double amountOfTicks = 60.0; //number of ticks per second
		double ns = 1000000000/amountOfTicks; //number of nanoseconds allowed between ticks
		double delta = 0; //the amount of time difference between ticks
		long timer = System.currentTimeMillis();
		int frames = 0; //number of frames per second
		int count = 0;
		
		while(running) {
			
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//redraw the screen	
			if(running){
				render();
				if(finished) stop();

			}
			
		}
		stop();
	}
	
	public void tick() {

	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
	if(bs == null) {
		this.createBufferStrategy(2);
		return;
	}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		AStar(g);
		
		g.dispose();
		bs.show();
	}
	
	public void AStar(Graphics g) {
		
		//if openSet is not empty
		if(openSet.size() > 0) {
			//get the best node and let it be the current node
			Cell bestCell = openSet.get(0);
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).f < bestCell.f) {
					bestCell = openSet.get(i);
				}
			}
			
			
			 current = bestCell;
			 System.out.println("current: (" + current.x + ", " + current.y + ")");

			//if the current node is the goal, then done.
				if (current == goal) {
				
				
				System.out.println("DONE!");
				finished = true;
			}else {
				// otherwise
				openSet.remove(current);
				closedSet.add(current);

				LinkedList<Cell> neighbors = current.neighbors;
				for (int i = 0; i < neighbors.size(); i++) {
					Cell neighbor = neighbors.get(i);

					if (!closedSet.contains(neighbor) && !neighbor.wall) {
						int tempG = current.g + 1;

						boolean newPath = false;
						if (openSet.contains(neighbor)) {
							if (tempG < neighbor.g) {
								neighbor.g = tempG;
								newPath = true;
							}
						} else {
							neighbor.g = tempG;
							openSet.add(neighbor);
							newPath = true;
						}

						if(newPath) {
							neighbor.h = heuristic(neighbor, goal);
							neighbor.f = neighbor.g + neighbor.h;
							neighbor.previous = current;
						}
						

					}

				}
			}
			
		}else {
			System.out.println("no solution!");
			noSolution = true;	
		}

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j].render(g, Color.WHITE);
			}
		}

		for (int i = 0; i < closedSet.size(); i++) {
			closedSet.get(i).render(g, Color.RED);
		}

		for (int i = 0; i < openSet.size(); i++) {
			openSet.get(i).render(g, Color.GREEN);
		}
		
		if(!noSolution) {
			Cell temp = current;
			path = new LinkedList<Cell>();
			path.add(temp);
			while(temp.previous != null) {
				path.add(temp.previous);
				temp = temp.previous;
			}
		}
		for(int i = 0; i < path.size(); i++) {
			System.out.println("Path: (" + path.get(i).x + ", " + path.get(i).y + ")");
			//path.get(i).render(g, Color.BLUE);
			
			if(path.size() > i+1) {
				g.setColor(Color.PINK);
				g.drawLine(path.get(i).x*w + w/2, path.get(i).y*h + h/2, path.get(i+1).x*w + w/2, path.get(i+1).y*h + h/2);
			}
		}
		
		if(finished || noSolution) stop();

	}
	
	public double heuristic(Cell s, Cell g) {
		double d = Math.sqrt(Math.pow((s.x - g.x),2) + Math.pow((s.y - g.y), 2));
		//double d = Math.abs(s.x - g.x) + Math.abs(s.y - g.y);
		return d;
	}
	
	
	
	
	public static void main(String args[]) {
		new Class1();
	}
	
}
