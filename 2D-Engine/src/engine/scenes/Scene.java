package engine.scenes;

import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.math.Vector2;

public abstract class Scene {
	private ArrayList<GameObject> gameObjectsInScene = new ArrayList<GameObject>();
	private int objectCount = 0;

	private ArrayList<GameObject> gameObjectsToBeAdded = new ArrayList<GameObject>();
	private ArrayList<GameObject> gameObjectsToBeRemoved = new ArrayList<GameObject>();

	private String name;
	private boolean unload;
	private boolean started = false;

	public Scene(final String name) {
		this.unload = false;
		this.setName(name);
		SceneManager.scenes.add(this);
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

	public String getName() {
		return this.name;
	}

	public int getObjectCount() {
		return this.objectCount;
	}

	private void insertGameObjects() {
		if (!this.gameObjectsToBeAdded.isEmpty()) {

			for (GameObject obj : this.gameObjectsToBeAdded) {
				this.gameObjectsInScene.add(obj);
				if (!obj.started && this.started) {
					obj.start(this);
				}
			}

			this.gameObjectsToBeAdded.clear();
		}
	}

	public void instanceGameObjects() {
	}

	private boolean inView(GameObject obj) {
		if (obj == Camera.activeCam.gameObject || !obj.getInWorld()) {
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
			this.objectCount -= this.gameObjectsToBeRemoved.size();
			this.gameObjectsToBeRemoved.clear();

			this.gameObjectsToBeAdded.clear();
		}
	}

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
	}

	public void sceneLoaded() {
	}

	public void sceneUnloaded() {
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void start() {
		this.sceneLoaded();

		insertGameObjects();
		System.out.println();
		System.out.println("Scene[" + this.name + "] with sizeOf[" + this.gameObjectsInScene.size() + "] loading");
		for (int i = 0; i < this.gameObjectsInScene.size(); ++i) {
			this.gameObjectsInScene.get(i).start(this);
		}

		SceneManager.flipp();
		started = true;

	}

	public void unload() {
		this.unload = true;
	}

	public void update() {
		if (this.unload) {
			this.gameObjectsInScene.clear();
			System.out.println("Unloaded Scene [" + this.name + "]");
			this.sceneUnloaded();
			this.unload = false;
			/*
			 * Scene wird nicht komplett aus dem ram gelöscht!!!!!!!!!!!!!!! Sollte noch
			 * besser werden
			 */
			return;
		}

		for (final GameObject obj : this.gameObjectsInScene) {
			if ((obj.updatesOutOfView || inView(obj)) && obj.started) {
				obj.update();
			}
		}

		insertGameObjects();
		outsertGameObjects();
	}
}