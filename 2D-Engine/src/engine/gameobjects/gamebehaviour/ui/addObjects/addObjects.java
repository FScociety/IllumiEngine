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
		this.gameObject.setInWorld(false);
		
		float distance = size.y / objectsToAdd.length;
		for (int i = 0; i < objectsToAdd.length; i++) {
			this.buttonsList[i] = new GameObject(new Transform(new Vector2(0, distance * i - size.y/2 + distance/2), 0, new Vector2(1), new Vector2(1)), this.gameObject);
			Button b = new Button(new Vector2(98,distance - 2));
			b.active = false;
			addObjectType aot = new addObjectType(this.objectsToAdd[i]);
			aot.active = false;
			b.addButtonListener(aot);
			this.buttonsList[i].addComponent(b);
			this.buttonsList[i].addComponent(aot);
		}
	}
	
	public void update() {
		if (GameContainer.input.isKeyDown(KeyEvent.VK_SHIFT)) {
			if (GameContainer.input.isKey(KeyEvent.VK_A)) {
				Vector2 newPos = Vector2.add(GameContainer.input.getMousePos(false), Vector2.divide(this.size, 2));
				this.gameObject.setPosition(Vector2.add(newPos, new Vector2(-size.x / 2, -5)));
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
			this.d.setColor(Color.DARK_GRAY);
			this.d.fillRect(new Vector2(0, -10), new Vector2(size.x, size.y+10));
			this.d.setColor(Color.WHITE);
			this.d.drawString("addObject", new Vector2(-this.size.x/2 +5, -this.size.y/2));
		}
	}
}