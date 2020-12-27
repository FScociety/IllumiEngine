package engine.scenes;

import java.util.ArrayList;

public class SceneManager {
	public static ArrayList<Scene> scenes = new ArrayList<Scene>();
	public static Scene activeScene;
	public static Scene oldScene;
	private static Scene bufferScene;

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
						bufferScene = newScene;
						System.out.println();
						System.out.println("Scene[" + newScene.getName() + "] loading...");
						System.out.print("Creating Objects... ");
						bufferScene.instanceGameObjects();
						System.out.println("Created!");
						System.out.println("Starting Objects... ");
						bufferScene.start();
						System.out.println("Objects Started!");
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