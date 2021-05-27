package engine.scenes;

import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.builtin.camera.Camera;
import engine.gameobjects.gamebehaviour.builtin.ui.Canvas;
import engine.io.Logger;
import engine.math.Vector2;

public abstract class Scene {
	private ArrayList<GameObject> gameObjectsInScene = new ArrayList<GameObject>();
	private ArrayList<GameObject> gameObjectsInUI = new ArrayList<GameObject>();
	
	private ArrayList<GameObject> gameObjectsToBeAdded = new ArrayList<GameObject>();
	private ArrayList<GameObject> gameObjectsToBeRemoved = new ArrayList<GameObject>();
	
	private int objectCount = 0;

	private String name;
	private boolean unload;
	private boolean started = false;
	
	public final Camera defaultCamera = new Camera();
	public final GameObject defaultCameraObject;
	
	public final Canvas canvas;
	public final GameObject canvasGameObject;
	
	private static final String prefix = "Scene";

	public Scene(String name) {
		this.unload = false;
		this.setName(name);
		SceneManager.scenes.add(this);
		
		//Create default Camera
		defaultCameraObject = new GameObject(new Vector2(0), true);
		defaultCameraObject.addComponent(defaultCamera);
		this.gameObjectsInScene.add(defaultCameraObject);
		
		//Create UICanvas
		canvasGameObject = new GameObject(new Vector2(0), false);
		canvas = new Canvas(GameContainer.gc.getSize());
		canvasGameObject.addComponent(canvas);
		this.gameObjectsInScene.add(canvasGameObject);
	}

	public void addGameObject(final GameObject gobj) {
		this.gameObjectsToBeAdded.add(gobj);
	}

	public void addObjectCount() {
		this.objectCount++;
	}

	public ArrayList<GameObject> getGameObjectsInScene() {
		return this.gameObjectsInScene;
	}
	
	public GameObject getGameObject(int arrayPosition) {
		return this.gameObjectsInScene.get(arrayPosition);
	}

	public String getName() {
		return this.name;
	}

	public int getObjectCount() {
		return this.objectCount;
	}

	public abstract void instanceGameObjects();

	public void removeGameObject(final GameObject gobj) {
		this.gameObjectsToBeRemoved.add(gobj);
	}

	public void render() {

		/*
		 * Mann kann betimmt noch was bei der Perfomance rausholen
		 */

		if (!started) {
			return;
		}

		Object[] objects = this.gameObjectsInScene.toArray();

		for (int i = 0; i < objects.length; i++) {
			GameObject obj = (GameObject) objects[i];
			if (inView(obj) && obj.started) {
				obj.render();
			}
		}
		
		objects = this.gameObjectsInUI.toArray();
		
		for (int i = 0; i < objects.length; i++) {
			GameObject obj = (GameObject) objects[i];
			if (obj.started) {
				obj.render();
			}
		}
	}

	public void sceneLoaded() {
	
	}

	public void sceneUnloaded() {
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void start() {
		//DANGER Sollte eig vor eins vor "started = true" stehen aber sonst kann ich des mit dem Window Bounds nicht machen
		SceneManager.flipp();

		insertGameObjects();
		for (int i = 0; i < this.gameObjectsInScene.size(); ++i) {
			this.gameObjectsInScene.get(i).start(this);
		}
		for (int i = 0; i < this.gameObjectsInUI.size(); ++i) {
			this.gameObjectsInUI.get(i).start(this);
		}
		
		started = true;
		
		//If user does not create a custom cam this will be loaded (:
		this.defaultCamera.view();
		
		this.sceneLoaded();
	}

	public void unload() {
		this.unload = true;
	}

	public void update() {
		if (this.unload) {
			this.gameObjectsInScene.clear();
			Logger.println(prefix, "Unloaded Scene [" + this.name + "]", 0);
			this.sceneUnloaded();
			this.unload = false;
			/*
			 * Scene wird nicht komplett aus dem ram gel�scht!!!!!!!!!!!!!!! Sollte noch
			 * besser werden
			 */
			return;
		}

		for (final GameObject obj : this.gameObjectsInScene) {
			if ((obj.updatesOutOfView || inView(obj)) && obj.started) {
				obj.update();
			}
		}
		
		for (final GameObject obj : this.gameObjectsInUI) {
			if (obj.started) {
				obj.update();
			}
		}

		insertGameObjects();
		outsertGameObjects();
	}

	private void insertGameObjects() {
		if (!this.gameObjectsToBeAdded.isEmpty()) {

			for (GameObject obj : this.gameObjectsToBeAdded) {
				if (obj.getInWorld()) {
					this.gameObjectsInScene.add(obj);
				} else {
					this.gameObjectsInUI.add(obj);
				}
				
				if (!obj.started && this.started) {
					obj.start(this);
				}
			}

			this.gameObjectsToBeAdded.clear();
		}
	}

	private boolean inView(GameObject obj) {
		if (obj == Camera.activeCam.gameObject) {
			return true;
		} else {
			int viewRangeX = (int) (obj.viewRange * obj.getTransformWithCaution().scale.x); // x + y weil das object
																							// scaliert werden kann
			int viewRangeY = (int) (obj.viewRange * obj.getTransformWithCaution().scale.y);
			Vector2 windowBounds = new Vector2(GameContainer.windowSize.x / 2, GameContainer.windowSize.y / 2);
			windowBounds.divide(new Vector2(Camera.activeCam.zoom));
			if (!(obj.getTransformWithCaution().position.x
					+ viewRangeX <= Camera.activeCam.gameObject.getTransformWithCaution().position.x - windowBounds.x
					|| obj.getTransformWithCaution().position.x
							- viewRangeX >= Camera.activeCam.gameObject.getTransformWithCaution().position.x
									+ windowBounds.x
					|| obj.getTransformWithCaution().position.y
							+ viewRangeY <= Camera.activeCam.gameObject.getTransformWithCaution().position.y
									- windowBounds.y
					|| obj.getTransformWithCaution().position.y
							- viewRangeY >= Camera.activeCam.gameObject.getTransformWithCaution().position.y
									+ windowBounds.y)) {
				return true;
			} else {
				return false;
			}
		}
	}

	private void outsertGameObjects() {
		if (!this.gameObjectsToBeRemoved.isEmpty()) {

			this.gameObjectsInScene.removeAll(this.gameObjectsToBeRemoved);
			this.gameObjectsInUI.removeAll(this.gameObjectsToBeRemoved);
			this.objectCount -= this.gameObjectsToBeRemoved.size();
			this.gameObjectsToBeRemoved.clear();

			this.gameObjectsToBeAdded.clear();
		}
	}
}