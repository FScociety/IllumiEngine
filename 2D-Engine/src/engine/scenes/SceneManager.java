package engine.scenes;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.Window;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.ColorLabel;
import engine.gameobjects.gamebehaviour.Text;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.gameobjects.gamebehaviour.camera.CameraController;
import engine.gameobjects.gamebehaviour.ui.Profile;
import engine.math.Vector2;

public class SceneManager {
	public static ArrayList<Scene> scenes = new ArrayList<Scene>();
	public static Scene activeScene;
	public static Scene oldScene;
	private static Scene bufferScene;
	
	public static Scene noScene = new Scene("start") {
		
		Camera cam;
		
		public void instanceGameObjects() {
			GameObject test = new GameObject(new Vector2(0,0), true);
			test.addComponent(new Text("No Scene loaded", 10, Window.standartFont, Color.WHITE));
			noScene.addGameObject(test);
			
			GameObject camera = new GameObject(new Vector2(0), true);
			cam = new Camera();
			camera.addComponent(cam);	
			camera.addComponent(new CameraController(true, true, true));
			noScene.addGameObject(camera);
		}
		
		public void sceneLoaded() {
			cam.view();
		}
		
	};

	public synchronized static void flipp() {
		SceneManager.activeScene = bufferScene;

		if (SceneManager.activeScene == null) {
			System.err.println(
					"No Idea why, but when loading scenes with big objectCounts the SceneManager.activeScene is 'null'");
			System.err.println("This was called from SceneManager.flipp()");
		}
	}

	public static Scene getActiveScene() {
		return SceneManager.activeScene;
	}

	public static void loadScene(final Scene newScene) {
		Thread sceneLoader = new Thread(new Runnable() {

			@Override
			public void run() {
				if (SceneManager.scenes.contains(newScene)) {
					if (SceneManager.activeScene != newScene) {
						SceneManager.oldScene = SceneManager.activeScene;
						System.out.println(SceneManager.activeScene);
						bufferScene = newScene;
						bufferScene.instanceGameObjects();
						bufferScene.start();
						if (SceneManager.oldScene != null) {
							SceneManager.oldScene.unload();
						}
						System.out.println("Scene[" + newScene.getName() + "] loaded!");
						System.out.println();
					} else {
						System.out.println("Why load Scene[" + newScene.getName() + "] when activeScene["
								+ SceneManager.activeScene.getName() + "]");
					}
				} else {
					System.out.println("Scene[" + bufferScene.getName() + "] doesn't exist");
				}
			}
		});
		sceneLoader.start();
	}
}