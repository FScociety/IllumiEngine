package engine.game;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.input.Input;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class GameContainer {

	public static GameContainer gc;

	private static boolean running = false;
	private static double nd = 1.0E9;

	private static double targetFPS = 80;
	public static AbstractGame game;

	public static Window window;
	public static Input input;
	public static Drawing d;
	public static Vector2 windowSize;
	public static float fps = 0;

	public static float ups = 0;
	public static double dt;
	private Thread updateThread, renderThread;

	public GameContainer(final AbstractGame game) {
		GameContainer.game = game;
		GameContainer.gc = this;
	}

	public Vector2 getSize() {
		return GameContainer.windowSize;
	}

	public void setSize(Vector2 size) {
		GameContainer.windowSize = size;
	}

	public void start() {
		System.out.println("Starting...");
		GameContainer.running = true;
		System.out.println("Creating Window...");
		GameContainer.window = new Window(gc);
		System.out.println("Start Listening Input...");
		GameContainer.input = new Input(gc);
		System.out.println("LoadingGame...");
		GameContainer.d = new Drawing((Graphics2D) window.g);

		startUpdateThread();

	}

	private void startRenderThread() {
		System.out.println("RenderThread...");
		renderThread = new Thread(new Runnable() {

			@Override
			public void run() {

				int frame = 0;
				double startTime = System.nanoTime() / nd;
				double lastTime = 0;
				double frameTime = 0;

				System.out.println("RenderThread is running!");

				while (running) {

					window.g.setColor(Color.BLACK);
					window.g.fillRect(0, 0, (int) GameContainer.windowSize.x, (int) GameContainer.windowSize.y);

					if (SceneManager.activeScene != null) {
						game.render();
						SceneManager.activeScene.render();
					}

					try {
						window.bs.show(); // Flipp the Buffer
					} catch (IllegalStateException e) {
					}

					lastTime = startTime;
					startTime = System.nanoTime() / nd;
					frameTime += startTime - lastTime;
					if (frameTime >= 1) {
						fps = frame;
						frame = 0;
						frameTime = 0;
					} else {
						frame++;
					}

					Window.frame.setTitle("Illumi-Engine | " + fps + " | " + ups);
					/*
					 * try { Thread.sleep((long) (1 / GameContainer.targetFPS * 1000)); //Sleep for
					 * } catch (InterruptedException e) {
					 * System.out.println("What the... Thread couldn't be set to sleep");
					 * e.printStackTrace(); }
					 */
				}
			}
		});
		renderThread.setName("Render-Thread");
		renderThread.start();
	}

	private void startUpdateThread() {
		System.out.println("UpdateThread...");
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				game.start();

				int cycl = 0;
				double startTime = System.nanoTime() / nd;
				double endTime = 0;
				double frameTime = 0;
				dt = 1; // So it isnt Stuck in the Beginning

				System.out.println("UpdateThread is running!");

				startRenderThread();

				while (running) {
					// Update

					if (SceneManager.activeScene != null) {
						SceneManager.activeScene.update();
						game.update();
						input.update();

						if (SceneManager.oldScene != null) { // For Unloading the oldScene
							SceneManager.oldScene.update();
						}
					}

					// Update

					endTime = startTime;
					startTime = System.nanoTime() / nd;
					dt = (startTime - endTime);

					frameTime += startTime - endTime;
					if (frameTime >= 1) {
						ups = cycl;
						cycl = 0;
						frameTime = 0;
					} else {
						cycl++;
					}
					try {
						Thread.sleep((long) (1f));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateThread.setName("Update-Thread");
		updateThread.start();
	}
}