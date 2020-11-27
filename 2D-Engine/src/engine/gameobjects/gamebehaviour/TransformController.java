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
	
	private float state = 0; //1:grabed 2:clicked 3:rotate 4:scale
	
	Vector2 oldMousePos = new Vector2(0);
	Vector2 oldObjectPos;
	
	float oldRotation;
	
	Vector2 oldScale;
	float oldMouseObjectDistance;
	Vector2 scaleMultiplier;
	
	boolean features[] = new boolean[3];
	
	
	public TransformController() {
		listenButton = new Button(new Vector2(100));
		this.features[0] = true;
		this.features[1] = true;
		this.features[2] = true;
	}
	
	public TransformController(boolean position, boolean rotation, boolean scaling) {
		listenButton = new Button(new Vector2(100));
		this.features[0] = position;
		this.features[1] = rotation;
		this.features[2] = scaling;
	}
	
	public TransformController(Button b, boolean position, boolean rotation, boolean scaling) {
		listenButton = b;
		this.features[0] = position;
		this.features[1] = rotation;
		this.features[2] = scaling;
	}
	
	public TransformController(Button b) {
		listenButton = b;
		this.features[0] = true;
		this.features[1] = true;
		this.features[2] = true;
	}
	
	@Override
	public void ButtonClicked() {
		if (this.state == 2 || this.state == 3 || this.state == 4) { //no "if" if its acitivaded cause this would ask ethen more
			this.state = 0;
			this.listenButton.setWire(false);
			this.listenButton.setBaseColor(this.oldButtonC);
		} else {
			Vector2 newMousePos = GameContainer.input.getMousePosToWorld();
			state = 0;
			if (!this.features[0] || ((this.features[1] || this.features[2]) && Math.abs(Vector2.substract(newMousePos, this.oldMousePos).length()) <= 2)) { //Not moving mouseMuch
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
		if (this.state == 2 || !this.features[0]) { //If the button is clicked you dont want to move it || position feature is deactivaded
			return;
		}
		this.oldMousePos = GameContainer.input.getMousePosToWorld();
		this.state = 1;
		this.oldObjectPos = this.gameObject.getTransformWithCaution().position;
	}
	
	@Override
	public void start() {
		listenButton.addButtonListener(this);
		this.gameObject.addComponent(listenButton);
		this.oldButtonC = this.listenButton.getBaseColor();
		this.gameObject.viewRange = 100;
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
			if (this.features[1] && GameContainer.input.isKeyDown(KeyEvent.VK_R)) {
				this.state = 3;
				this.oldRotation = objectTrans.rotation - Vector2.toAngle(objectTrans.position, mousePos);
			} else if (this.features[2] && GameContainer.input.isKeyDown(KeyEvent.VK_S)) {
				this.state = 4;
				this.oldScale = this.gameObject.getTransformWithCaution().scale;
				this.scaleMultiplier = new Vector2(1,1);
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
			
			if (GameContainer.input.isKey(KeyEvent.VK_X)) { //Just want to adjust the x-Axis
				this.scaleMultiplier = new Vector2(1,0);
			} else if (GameContainer.input.isKey(KeyEvent.VK_Y)) { //Just want to adjust the y-Axis
				this.scaleMultiplier = new Vector2(0,1);
			} else {
				Vector2 vec = this.oldScale.getCopy();
				vec.multiply(mouseObjectDistance / oldMouseObjectDistance);
				vec.multiply(this.scaleMultiplier);
				if (this.scaleMultiplier.length() == this.scaleMultiplier.x + this.scaleMultiplier.y) {
					vec.add(Vector2.multiply(Vector2.flipp(this.scaleMultiplier), this.oldScale)); 
				} //If you just zoom at one axis
				
				this.gameObject.setScale(vec);
			}
			
			tryExit();
		}
	}
	
	public Button getButton() {
		return this.listenButton;
	}

}
