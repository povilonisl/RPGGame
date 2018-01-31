package main;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import path.Class1;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255319694373975038L;

	/**
	 * Creates the windows of the game
	 * 
	 * @param width width of the windows
	 * @param height height of the window
	 * @param title title of the window
	 * @param class1 The content of the window
	 */
	public Window(int width, int height, String title, Class1 class1) {
		JFrame frame = new JFrame(title);
		
		//Size of the window
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(class1);
		frame.setVisible(true);
		class1.start();
	}
}
