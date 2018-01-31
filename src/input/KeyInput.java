package input;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Handler;
import main.ID;
import objects.GameObject;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for(int i = 0; i < handler.sizeObjects(); i++) {
			GameObject tempObject = handler.getObject(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) tempObject.setValY(-3);
				if(key == KeyEvent.VK_S) tempObject.setValY(3);
				if(key == KeyEvent.VK_A) tempObject.setValX(-3);
				if(key == KeyEvent.VK_D) tempObject.setValX(3);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for(int i = 0; i < handler.sizeObjects(); i++) {
			GameObject tempObject = handler.getObject(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) tempObject.setValY(0);
				if(key == KeyEvent.VK_S) tempObject.setValY(0);
				if(key == KeyEvent.VK_A) tempObject.setValX(0);
				if(key == KeyEvent.VK_D) tempObject.setValX(0);
			}
		}
	}
}
