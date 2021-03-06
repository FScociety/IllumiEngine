package engine.gameobjects.gamebehaviour.builtin.camera;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.builtin.ui.interactable.Interactable;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class CameraController extends GameBehaviour {

	boolean posChange, zoomChange, zoomToObject;
	Camera cam;
	Vector2 oldMouse, oldObject;

	public CameraController(boolean posChange, boolean zoomChange, boolean zoomToObject) {
		this.posChange = posChange;
		this.zoomChange = zoomChange;
		this.zoomToObject = zoomToObject;
		this.prefferedInWorldState = 1;
	}

	@Override
	public void start() {
		cam = (Camera) this.gameObject.getComponent(Camera.class);
	}

	@Override
	public void update() {
		if (this.posChange) {
			if (GameContainer.input.isButton(MouseEvent.BUTTON2)) {
				oldMouse = GameContainer.input.getMousePos(false).getCopy();
				oldObject = this.gameObject.getTransformWithCaution().position;
			} else if (GameContainer.input.isButtonDown(MouseEvent.BUTTON2)) {
				
				this.gameObject.setPosition(Vector2.add(oldObject, Vector2.divide(
						Vector2.substract(oldMouse, 
								GameContainer.input.getMousePos(false)), 
								Camera.activeCam.zoom)));
			}
		}

		if (this.zoomChange) {
			cam.zoom += GameContainer.input.getScroll() / 50f;
		}

		if (zoomToObject) {
			if (GameContainer.input.isKey(KeyEvent.VK_COMMA)) {
				if (Interactable.objectFocused != null) {
					cam.gameObject.setPosition(Interactable.objectFocused.getTransformWithCaution().position);
					float zoom = GameContainer.windowSize.y / this.gameObject.viewRange / 3;
					cam.zoom = zoom;
				}
			}
		}
	}

}
