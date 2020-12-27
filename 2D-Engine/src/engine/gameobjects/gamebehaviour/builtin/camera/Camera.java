package engine.gameobjects.gamebehaviour.builtin.camera;

import java.util.ArrayList;

import engine.gameobjects.gamebehaviour.type.GameBehaviour;

public class Camera extends GameBehaviour {

	public static ArrayList<Camera> cameras = new ArrayList<Camera>();
	public static Camera activeCam = null;

	public float zoom = 1;

	public Camera() {
		this.prefferedInWorldState = 1;
		cameras.add(this);
	}

	@Override
	public void delete() {
		cameras.remove(this);
	}

	@Override
	public void render() {

	}

	@Override
	public void start() {
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

	@Override
	public void update() {
	}

	public void view() {
		Camera.activeCam = this;
	}
}