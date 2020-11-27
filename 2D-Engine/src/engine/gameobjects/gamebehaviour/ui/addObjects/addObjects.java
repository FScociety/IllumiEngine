package engine.gameobjects.gamebehaviour.ui.addObjects;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.Transform;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.ui.interactable.Button;
import engine.math.Vector2;

public class addObjects extends GameBehaviour {
	
	private GameObject[] objectsToAdd;
	private boolean active;
	
	private GameObject[] buttonsList;
	private Vector2 size;
	
	public  addObjects(GameObject[] objectsToAdd, Vector2 size) {
		this.size = size;
		this.objectsToAdd = objectsToAdd;
		this.buttonsList = new GameObject[this.objectsToAdd.length];
	}
	
	public void start() {
		float distance = size.y / objectsToAdd.length;
		for (int i = 0; i < objectsToAdd.length; i++) {
			this.buttonsList[i] = new GameObject(new Transform(new Vector2(0, distance * i + 5 - size.y/2), 0, new Vector2(1), new Vector2(1)), this.gameObject);
			Button b = new Button(new Vector2(100,10));
			addObjectType aot = new addObjectType(this.objectsToAdd[i]);
			b.addButtonListener(aot);
			this.buttonsList[i].addComponent(b);
		}
	}
	
	public void update() {
		if (GameContainer.input.isKeyDown(KeyEvent.VK_SHIFT)) {
			if (GameContainer.input.isKey(KeyEvent.VK_A)) {
				this.gameObject.setPosition(Vector2.add(GameContainer.input.getMousePosToWorld(), Vector2.multiply(this.size, 0.5f)));
				this.active = true;
				updateChildren(true);
			}
		}
	}
	
	private void updateChildren(boolean state) {
		for (int i = 0; i < objectsToAdd.length; i++) {
			this.buttonsList[i].getComponent(Button.class).active = state;
			this.buttonsList[i].getComponent(addObjectType.class).active = state;
		}
	}
	
	public void close() {
		this.active = false;
		updateChildren(false);
	}
	
	public void render() {
		if (this.active) {
			this.d.setColor(Color.GRAY);
			this.d.fillRect(size);
		}
	}
}