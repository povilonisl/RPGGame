package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import input.KeyInput;
import input.MouseInput;
import objects.Player;
import objects.PolygonObject;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -8921419424614180143L;
	
	public static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	private Handler handler;
	
	private Random r;
	
	public Game() {
		handler = new Handler();
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler));
		
		new Window(WIDTH, HEIGHT, "Let's Build a game!", this);
		
		handler.addObject(new Player(100, 100, ID.Player, handler));
		
		r = new Random();
		
		handler.addObject(new PolygonObject(new int[] {r.nextInt(500), r.nextInt(500), r.nextInt(500)}, new int[] {r.nextInt(500),r.nextInt(500), r.nextInt(500)}, 3, ID.Polygon));

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
	
	/**
	 * 
	 */
	@Override
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
			//update delta with the amount of time passed since the last iteration
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			
			//redraw the screen	
			if(running){
				render();
			}
			
			frames++; //increase the FPS by 1
			
			//after 1 second has passed, display the FPS
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			count = 0;
		}
		stop();
	}
	
	/**
	 * Update to all valid objects in the game
	 */
	private  void tick() {
		handler.tick();
	}
	
	/**
	 * Redrawing of the screen
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else 
			return var;
		
	}

	public static void main(String args[]) {
		new Game();
	}
}
