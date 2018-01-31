package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.Handler;
import main.ID;
import objects.GameObject;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	

	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseDragged(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseEntered(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseExited(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseMoved(arg0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//location of where mouse was clicked
		int targetX = e.getX();
		int targetY = e.getY();
		GameObject player = null; //store player in case later its found that the coordinates are within another object and we should cancel the movement.
		
		//Go through all of the objects in the game
		for(int i = 0; i < handler.sizeObjects(); i++) {
			GameObject tempObject = handler.getObject(i);
			
			//if the object is player
			if(tempObject.getId() == ID.Player) {
				player = tempObject;
				//Set the coordinates of where the player should move. 
				//(- player.getWidth()/2) - this is due to player expecting the sprite to be in the middle of where mouse was clicked.
				//If it is not changed then top corner of sprite will be where the user clicked, instead of the centre.
				player.setTargetX(targetX - player.getWidth()/2);
				player.setTargetY(targetY - player.getHeight()/2);
				
				//if the object is tree, coordinates are within the object.
			}else if(tempObject.getId() == ID.Tree) {
				if(tempObject.bounds().contains(targetX, targetY)){
					//if player object was found then stop the player from moving
					if(player != null) {
						player.setTargetX(player.getX());
						player.setTargetY(player.getY());
					}
					//and stop cycling through objects.
					break;	
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseReleased(arg0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(arg0);
	}
	
}
