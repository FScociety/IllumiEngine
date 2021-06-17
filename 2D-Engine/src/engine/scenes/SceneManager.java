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
						Logger.log("Scene[" + newScene.getName() + "] Loading");
						Logger.log("Scene[" + newScene.getName() + "] Creating objects");
						bufferScene.instanceGameObjects();
						Logger.fine("Scene[" + newScene.getName() + "] Created");
						
						Logger.log("Scene[" + newScene.getName() + "] Starting objects");
						bufferScene.start();
						Logger.fine("Scene[" + newScene.getName() + "] Started objects");
						if (SceneManager.oldScene != null) {
							SceneManager.oldScene.unload();
						}
						Logger.fine("Scene[" + newScene.getName() + "] loaded");
					} else {
						Logger.warn("Why load Scene[" + newScene.getName() + "] when activeScene["
								+ SceneManager.activeScene.getName() + "]");
					}
				} else {
					Logger.error("Scene[" + bufferScene.getName() + "] doesn't exist");
				}
			}
		});
		sceneLoader.start();
	}
}