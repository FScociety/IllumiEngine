package engine.gameobjects.gamebehaviour.builtin.ui.interactable;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;

public class Button extends UIGameBehaviour {

	private Color[] colors = new Color[3]; //Colors for different "state"
	private boolean wire;
	protected int state = 0; // 0 = normal; 1 = hover; 2 = press
	
	private String text; //TODO To be replaced with Text Behaviour and Bounds Object

	private ArrayList<ButtonListener> listener = new ArrayList<ButtonListener>();

	public Button(Color c) {
		this.setBaseColor(c);
	}
	
	public Button(String t) {
		this.setBaseColor(Color.WHITE);
		this.text = t;
	}
	
	public Button(String t, Color c) {
		this.text = t;
		this.setBaseColor(c);
	}
	
	public Button(String t, Color c, boolean wire) {
		this.text = t;
		this.setBaseColor(c);
		this.wire = wire;
	}

	public void addButtonListener(final ButtonListener bl) {
		this.listener.add(bl);
	}

	public Color getBaseColor() {
		return this.colors[0];
	}

	public boolean isColliding() {
		Vector2 mousePos = GameContainer.input.getMousePos(this.gameObject.getInWorld());

		if (this.gameObject.getTransformWithCaution().rotation != 0) {
			mousePos.rotate(-this.gameObject.getTransformWithCaution().rotation,
					this.gameObject.getTransformWithCaution().position);
		} // Normalize the MousePos rotation relative to button

		Vector2 boundsP1 = Vector2.add(this.gameObject.getTransformWithCaution().position, this.bounds.getPoint1());
		Vector2 boundsP2 = Vector2.add(this.gameObject.getTransformWithCaution().position, this.bounds.getPoint2());
		
		return boundsP1.x <= mousePos.x && boundsP2.x >= mousePos.x && boundsP1.y <= mousePos.y && boundsP2.y >= mousePos.y;
	}

	public boolean isWire() {
		return this.wire;
	}

	@Override
	public void render() {
		d.setColor(this.colors[state]);

		if (this.wire) {
			d.drawRect(this.bounds);
		} else {
			d.fillRect(this.bounds);
		}

		d.setColor(this.colors[2]);
	}
	
	public void setBaseColor(final Color c) {
		this.colors[0] = c;
		this.updateColors();
	}

	public void setWire(final boolean wire) {
		this.wire = wire;
	}

	@Override
	public String toString() {
		return "[State: " + this.state + "; Colors: [" + this.colors[0] + "; " + this.colors[1] + "; " + this.colors[2]
				+ "]; Wire:" + this.wire;
	}
	
	@Override
	public void update() {
		boolean collision = isColliding();
		
		if (Interactable.objectFocused == null || Interactable.objectFocused == this.gameObject) {
			if (collision) {
				if (state != 2) { //Colliding and Not Pressing => Hovering
					// Hover
					state = 1;
					this.buttonHoverForListener();
					Interactable.objectFocused = this.gameObject;

					if (GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
						// Press
						state = 2;
						this.buttonPressForListener();
					}
				}
			} else if ((Interactable.objectFocused == this.gameObject && !GameContainer.input.isButton(MouseEvent.BUTTON1))) {
				//Mouse Leaving the Button => Defocus
				Interactable.objectFocused = null;
				state = 0;
			}
			if (state == 2 && !GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
				//Click
				state = 0;
				Interactable.objectFocused = null;
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

	private void updateColors() {
		this.colors[1] = new Color((int) (this.colors[0].getRed() / 1.2f), (int) (this.colors[0].getGreen() / 1.2f),
				(int) (this.colors[0].getBlue() / 1.2f));
		this.colors[2] = new Color((int) (this.colors[0].getRed() / 1.5f), (int) (this.colors[0].getGreen() / 1.5f),
				(int) (this.colors[0].getBlue() / 1.5f));
	}
}