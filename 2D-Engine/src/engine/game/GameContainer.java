package engine.game;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.input.Input;
import engine.io.Logger;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class GameContainer {
	
	//DEBUG MODE
	public static final boolean debug = true;
	private String suffix = "GameContainer";
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
	private boolean abstractGameStarted = false;
	private Thread updateThread, renderThread;

	public GameContainer() {
		Logger.startLogger();
		GameContainer.gc = this;
	}
	
	public void setGame(final AbstractGame game) {
		GameContainer.game = game;
	}

	public Vector2 getSize() {
		return GameContainer.windowSize;
	}

	public void setSize(Vector2 size) {
		GameContainer.windowSize = size;
	}

	public void start() {
		Logger.println(suffix, "Starting Engine", 0);
		GameContainer.running = true;
		
		Logger.println(suffix, "Creating Window", 0);
		GameContainer.window = new Window(gc);
		Logger.println(suffix, "Created", 0);
		
		Logger.println(suffix, "Start Listening Input", 0);
		GameContainer.input = new Input(gc);
		Logger.println(suffix, "Started", 0);
		Logger.println(suffix, "Creating Drawer", 0);
		GameContainer.d = new Drawing((Graphics2D) window.g);
		GameContainer.game.d = GameContainer.d;
		Logger.println(suffix, "Created", 0);

		startUpdateThread();
		
		//TODO Nur komische Lösung!
		Window.frame.setSize(Window.frame.getSize());
	}

	private void startRenderThread() {
		Logger.println(suffix, "Starting RenderThread", 0);
		
		renderThread = new Thread(new Runnable() {

			@Override
			public void run() {

				int frame = 0;
				double startTime = System.nanoTime() / nd;
				double lastTime = 0;
				double frameTime = 0;

				Logger.println(suffix, "Running", 0);

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
					
					 try { Thread.sleep((long) (1 / GameContainer.targetFPS * 1000)); //Sleep for
					 } catch (InterruptedException e) {
					 System.out.println("What the... Thread couldn't be set to sleep");
					 e.printStackTrace(); }
					 
				}
			}
		});
		renderThread.setName("Render");
		renderThread.start();
	}
	
	private void startUpdateThread() {
		Logger.println(suffix, "Starting UpdateThread", 0);
		
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {

				int cycl = 0;
				double startTime = System.nanoTime() / nd;
				double endTime = 0;
				double frameTime = 0;
				dt = 1; // So it is not Stuck in the Beginning

				Logger.println(suffix, "Running", 0);

				startRenderThread();

				while (running) {
					if (renderThread.isAlive() && !abstractGameStarted)  {
						abstractGameStarted = true;
						game.start();
					}
					
					// GameUpdate BEGINN
					
					if (SceneManager.activeScene != null) {
						SceneManager.activeScene.update();
						game.update();
						input.update();

						if (SceneManager.oldScene != null) { // For Unloading the oldScene
							SceneManager.oldScene.update();
						}
					}

					// GameUpdate END

					endTime = startTime;
					startTime = System.nanoTime() / nd;
					dt = (startTime - endTime);

					frameTime += startTime - endTime;
					
					if (frameTime >= 1) {
						ups = cycl;
						cycl = 0; frameTime = 0;
					} else {
						cycl++;
					}
					try {
						Thread.sleep((long) (10f));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateThread.setName("Update");
		updateThread.start();
	}
}