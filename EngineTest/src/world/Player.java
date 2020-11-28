package world;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.Drawing;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Player extends Organism {
	
	private Vector2 moving = new Vector2(0);

	public Player() {
		super(100, Color.GREEN);
	}
	
	public void update() {
		if (GameContainer.input.isKey(KeyEvent.VK_W)) {
			moving.y-=20f;
		} else if (GameContainer.input.isKey(KeyEvent.VK_A)) {
			moving.x-=20f;
		} else if (GameContainer.input.isKey(KeyEvent.VK_S)) {
			moving.y+=20f;
		} else if (GameContainer.input.isKey(KeyEvent.VK_D)) {
			moving.x+=20f;
		}
		
		this.gameObject.addPosition(Vector2.multiply(moving, (float)GameContainer.dt));
		moving.multiply(.97f);
		
		if (GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
			Vector2 subVec = Vector2.substract(this.gameObject.getTransformWithCaution().position, GameContainer.input.getMousePos(true));
			subVec.normalize();
			
			GameObject bObj = new GameObject(this.gameObject.getTransformWithCaution().position);
			bObj.addComponent(new Bullet(this.gameObject, subVec));
			SceneManager.activeScene.addGameObject(bObj);
		}
	}
	
	public void death() {
		
	}

}