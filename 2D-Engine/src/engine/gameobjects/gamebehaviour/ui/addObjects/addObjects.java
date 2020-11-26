package engine.gameobjects.gamebehaviour.ui.addObjects;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;

public class addObjects extends GameBehaviour {
	
	private boolean opened = false;
	
	private addObjectType[] objects;
	
	public addObjects(GameObject[] objectsToAdd) {
		objects = new addObjectType[objectsToAdd.length];
		for (int i = 0; i < objectsToAdd.length; i++) {
			objects[i] = new addObjectType(objectsToAdd[i], new Vector2(100, 10));
		}
	}
	
	public void start() {
		for (int i = 0; i < objects.length; i++) {
			this.gameObject.addComponent(objects[i]);
		}
	}
	
	public void close() {
		this.opened = false;
	}
	
	public void update() {
		if (GameContainer.input.isKeyDown(KeyEvent.VK_SHIFT)) {
			if (GameContainer.input.isKey(KeyEvent.VK_A)) {
				this.gameObject.setPosition(GameContainer.input.getMousePosToWorld());
				opened = true;
				for (int i = 0; i < objects.length; i++) {
					this.objects[i]. //Compoent deactivieren????ß
				}
			}
		} else if (opened == true && GameContainer.input.isButton(MouseEvent.BUTTON1)) {
			opened = false;
			for (int i = 0; i < objects.length; i++) {
				this.gameObject.addComponent(objects[i]);
			}
		}
	}
	
	public void render() {
		if (!opened) {
			return;
		}
		this.d.setColor(Color.GRAY);
		//this.d.fillRect(new Vector2(100));
	}
}