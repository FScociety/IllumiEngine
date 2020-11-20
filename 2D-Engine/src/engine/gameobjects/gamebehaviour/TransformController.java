package engine.gameobjects.gamebehaviour;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.GameContainer;
import engine.gameobjects.Transform;
import engine.gameobjects.gamebehaviour.ui.interactable.Button;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;

public class TransformController extends GameBehaviour implements ButtonListener {
	
	Button listenButton;
	Color oldButtonC;
	
	private int state = 0; //1:grabed 2:clicked 3:rotate 4:scale
	
	Vector2 oldMousePos;
	Vector2 oldObjectPos;
	
	float oldRotation;
	
	Vector2 oldScale;
	float oldMouseObjectDistance;
	
	
	public TransformController() {
		listenButton = new Button(new Vector2(100));
	}
	
	public TransformController(Button b) {
		listenButton = b;
	}
	
	@Override
	public void ButtonClicked() {
		if (this.state == 2 || this.state == 3 || this.state == 4) {
			this.state = 0;
			this.listenButton.setWire(false);
			this.listenButton.setBaseColor(this.oldButtonC);
		} else {
			Vector2 newMousePos = GameContainer.input.getMousePosToWorld();
			state = 0;
			if (Math.abs(Vector2.substract(newMousePos, this.oldMousePos).length()) <= 2) { //Not moving mouseMuch
				this.listenButton.setBaseColor(new Color(255, 100, 100));
				this.state = 2;
				this.listenButton.setWire(true);
			}
		}
	}
	
	@Override
	public void ButtonHover() {
		
	}
	
	@Override
	public void ButtonPress() {
		if (this.state == 2) { //If the button is clicked you dont want to move it
			return;
		}
		this.state = 1;
		this.oldMousePos = GameContainer.input.getMousePosToWorld();
		this.oldObjectPos = this.gameObject.getTransformWithCaution().position;
	}
	
	@Override
	public void start() {
		listenButton.addButtonListener(this);
		this.gameObject.addComponent(listenButton);
		this.oldButtonC = this.listenButton.getBaseColor();
	}

	private void tryExit() {
		if (GameContainer.input.isKey(KeyEvent.VK_ENTER) | GameContainer.input.isButton(MouseEvent.BUTTON1)) {
			state = 0;
			this.listenButton.setWire(false);
			this.listenButton.setBaseColor(this.oldButtonC);
		}
 	}

	@Override
	public void update() {
		Vector2 mousePos = GameContainer.input.getMousePosToWorld();
		Transform objectTrans = this.gameObject.getTransformWithCaution();
		
		if (this.state == 1) {
			this.gameObject.setPosition(Vector2.substract(mousePos, Vector2.substract(this.oldMousePos, this.oldObjectPos)));
		} else if (this.state == 2) { //if the Controller is selected it can be rotated / scaled
			if (GameContainer.input.isKeyDown(KeyEvent.VK_R)) {
				this.state = 3;
				this.oldRotation = objectTrans.rotation - Vector2.toAngle(objectTrans.position, mousePos);
			} else if (GameContainer.input.isKeyDown(KeyEvent.VK_S)) {
				this.state = 4;
				this.oldScale = this.gameObject.getTransformWithCaution().scale;
				this.oldMouseObjectDistance = Vector2.substract(this.gameObject.getTransformWithCaution().position, GameContainer.input.getMousePosToWorld()).length();
			}
			
			tryExit();
		} else if (this.state == 3) { //Rotating
			float newRotation;
			Vector2 ObjectMouseDif = Vector2.substract(objectTrans.position, mousePos);
			newRotation = -ObjectMouseDif.toAngle();
			this.gameObject.setRotation(this.oldRotation - newRotation);
			
			tryExit();
		} else if (this.state == 4) { //Scaling
			float mouseObjectDistance = Vector2.substract(this.gameObject.getTransformWithCaution().position, GameContainer.input.getMousePosToWorld()).length();
			this.gameObject.setScale(Vector2.multiply(this.oldScale, mouseObjectDistance / oldMouseObjectDistance));
			tryExit();
		}
	}

}
