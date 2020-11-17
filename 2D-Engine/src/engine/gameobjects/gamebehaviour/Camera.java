package engine.gameobjects.gamebehaviour;

import java.util.ArrayList;

public class Camera extends GameBehaviour  {
	
	public static ArrayList<Camera> cameras = new ArrayList<Camera>();
	public static Camera activeCam = null;
	
	public float zoom = 1;
	
	public Camera() {
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
		this.gameObject.inWorld = true;
	}

	@Override
	public void update() {
	}

	public void view() {
		Camera.activeCam = this;
	}
	
	@Override
	public String toString() {
		for (int i = 0; i < cameras.size(); i++) {
			if (cameras.get(i) == this) {
				return "Your Camera is: " + i;
			}
		}
		
		return "Bischt du Dumm im Kopf oder was, by the way er konnte die camera nicht in der Camera liste finden was absolut kein Sinn macht also. Ja genau macht halt einfach kein Sinn";
	}
}