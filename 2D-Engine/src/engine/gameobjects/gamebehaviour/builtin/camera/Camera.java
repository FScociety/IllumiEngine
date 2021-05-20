package engine.gameobjects.gamebehaviour.builtin.camera;

import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Camera extends GameBehaviour {

	public static ArrayList<Camera> cameras = new ArrayList<Camera>();
	public static Camera activeCam = null;

	public float zoom = 1;
	public GameObject windowBoundsObj;
	public Bounds windowBounds;

	public Camera() {
		this.prefferedInWorldState = 1;
		cameras.add(this);
	}

	@Override
	public void delete() {
		cameras.remove(this);
	}
	
	public void update() {
		windowBounds.setBounds(new Vector2(0), GameContainer.windowSize);
	}

	@Override
	public void start() {
		windowBoundsObj = new GameObject(new Vector2(0), false);
		windowBounds = new Bounds(new Vector2(0), GameContainer.windowSize);
		windowBoundsObj.addComponent(windowBounds);
		SceneManager.activeScene.addGameObject(windowBoundsObj);
	}

	@Override
	public String toString() {
		for (int i = 0; i < cameras.size(); i++) {
			if (cameras.get(i) == this) {
				return i + "";
			}
		}

		//Truely notwendig!!!!
		return "Bischt du Dumm im Kopf oder was, "
				+ "by the way er konnte die camera nicht in der Camera liste finden "
				+ "was absolut kein Sinn macht also. "
				+ "Ja genau macht halt einfach kein Sinn";
	}

	public void view() {
		Camera.activeCam = this;
	}
}