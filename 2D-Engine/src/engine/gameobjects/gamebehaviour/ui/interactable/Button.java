package engine.gameobjects.gamebehaviour.ui.interactable;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;

public class Button extends GameBehaviour {
	private Vector2[] sizes;
	private Color[] colors;
	private boolean wire;
	private int state = 0; //0 = normal; 1 = hover; 2 = clicked

	private ArrayList<ButtonListener> listener;

	public Button(Vector2 size) {
		this.sizes = new Vector2[3];
		this.colors = new Color[3];
		this.wire = false;
		this.listener = new ArrayList<ButtonListener>();
		this.setBaseColor(Color.WHITE);
		setSize(size);
		
	}

	public void addButtonListener(final ButtonListener bl) {
		this.listener.add(bl);
	}

	public Color getBaseColor() {
		return this.colors[0];
	}
	
	public void setSize(Vector2 size) {
		this.sizes[0] = size;
		this.sizes[1] = this.sizes[0].getCopy();
		this.sizes[2] = this.sizes[0].getCopy();
		
		this.sizes[1].substract(new Vector2(2));
		this.sizes[2].substract(new Vector2(5));
	}

	public Vector2 getSize() {
		return this.sizes[0];
	}

	public boolean isWire() {
		return this.wire;
	}

	@Override
	public void render() {
		d.setColor(this.colors[state]);
		
		if (this.wire) {
			d.drawRect(this.sizes[state]);	
		} else {
			d.fillRect(this.sizes[state]);	
		}
		
		d.setColor(Color.PINK);
		d.drawString(state+"");
	}

	public void setBaseColor(final Color c) {
		this.colors[0] = c;
		this.updateColors();
	}
	
	private void updateColors() {
		this.colors[1] = new Color((int)(this.colors[0].getRed() / 1.2f), (int)(this.colors[0].getGreen() / 1.2f),
				(int)(this.colors[0].getBlue() / 1.2f));
		this.colors[2] = new Color((int)(this.colors[0].getRed() / 1.5f), (int)(this.colors[0].getGreen() / 1.5f),
				(int)(this.colors[0].getBlue() / 1.5f));
	}

	public void setWire(final boolean wire) {
		this.wire = wire;
	}

	@Override
	public void start() {

	}

	@Override
	public void update() {
		if (Interactable.objectFocused == null || Interactable.objectFocused == this) {
			if (isColliding()) {
				if (state!=2) { //Just to catch the click/hover event
					//Hover
					state = 1;
					this.buttonHoverForListener();
					Interactable.objectFocused = this;
					
					if (GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
						//Press
						this.buttonPressForListener();
						state = 2;
					}
				}
			} else if (Interactable.objectFocused == this && !GameContainer.input.isButton(MouseEvent.BUTTON1)) { //Just after you focused, runs just one time
				Interactable.objectFocused = null;
				state = 0;
			}
			if (state == 2 && !GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
				state = 0;
				this.buttonClickForListener();
			}
		}
	}
	
	private void buttonClickForListener() {
		for (ButtonListener bl : listener) {
			bl.ButtonClicked();
		}
	}
	
	private void buttonHoverForListener() {
		for (ButtonListener bl : listener) {
			bl.ButtonHover();
		}
	}
	
	private void buttonPressForListener() {
		for (ButtonListener bl : listener) {
			bl.ButtonPress();
		}
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isColliding() {
		Vector2 mousePos = GameContainer.input.getMousePosToWorld();
		
		if (this.gameObject.getTransformWithCaution().rotation!=0) {
			mousePos.rotate(-this.gameObject.getTransformWithCaution().rotation, this.gameObject.getTransformWithCaution().position);
		} //Normalize the MousePos rotation relative to button
	
		return  this.gameObject.getTransformWithCaution().position.x - this.sizes[0].x/2 <= mousePos.x &&
				this.gameObject.getTransformWithCaution().position.x + this.sizes[0].x/2 >= mousePos.x &&
				this.gameObject.getTransformWithCaution().position.y - this.sizes[0].y/2 <= mousePos.y &&
				this.gameObject.getTransformWithCaution().position.y + this.sizes[0].y/2 >= mousePos.y;
	}
	
	public String toString() { 
		return "[State: " + this.state + 
				"; Colors: [" + this.colors[0] + 
				"; " + this.colors[1] + 
				"; " + this.colors[2] + 
				"]; Wire:" + this.wire;
	}
}