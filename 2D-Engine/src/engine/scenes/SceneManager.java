package engine.scenes;

import java.util.ArrayList;

import engine.game.GameContainer;
import engine.io.Logger;

public class SceneManager {
	
	private static String prefix = "SceneManager";
	
	public static ArrayList<Scene> scenes = new ArrayList<Scene>();
	public static Scene activeScene;
	public static Scene oldScene;
	private static Scene bufferScene;

	public synchronized static void flipp() {
		SceneManager.activeScene = bufferScene;

		if (SceneManager.activeScene == null) {
			System.err.println(
					"No Idea why, but when loading scenes with big objectCounts the SceneManager.activeScene is 'null'");
			System.err.println("BTW This was called from SceneManager.flipp()");
		}
	}

	public static Scene getActiveScene() {
		return SceneManager.activeScene;
	}

	public static void loadScene(final Scene newScene) {
		/*
		 * TODO
		 * Everytime a scene gets loaded, a new Thread is created,
		 * this maybe be very shitty for performance ):
		 * Please change me
		 */
		
		Thread sceneLoader = new Thread(new Runnable() {

			@Override
			public void run() {
				Thread.currentThread().setName("SceneLoader");
				
				if (SceneManager.scenes.contains(newScene)) {
					if (SceneManager.activeScene != newScene) {
						SceneManager.oldScene = SceneManager.activeScene;
						bufferScene = newScene;
						Logger.println(prefix, "Scene[" + newScene.getName() + "] Loading", 0);
						Logger.println(prefix, "Scene[" + newScene.getName() + "] Creating objects", 0);
						bufferScene.instanceGameObjects();
						Logger.println(prefix, "Scene[" + newScene.getName() + "] Created", 0);
						
						Logger.println(prefix, "Scene[" + newScene.getName() + "] Starting objects", 0);
						bufferScene.start();
						Logger.println(prefix, "Scene[" + newScene.getName() + "] Objects started objects", 0);
						if (SceneManager.oldScene != null) {
							SceneManager.oldScene.unload();
						}
						Logger.println(prefix, "Scene[" + newScene.getName() + "] loaded", 0);
					} else {
						Logger.println(prefix, "Why load Scene[" + newScene.getName() + "] when activeScene["
								+ SceneManager.activeScene.getName() + "]", 1);
					}
				} else {
					Logger.println(prefix, "Scene[" + bufferScene.getName() + "] doesn't exist", 2);
				}
			}
		});
		sceneLoader.start();
	}
}