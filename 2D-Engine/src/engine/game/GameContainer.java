package engine.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import engine.input.Input;
import engine.io.Logger;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class GameContainer {
	
	//DEBUG MODE
	public static final boolean debug = true;
	private String suffix = "GameContainer";
	public static GameContainer gc;
	
	public static boolean running = false;
	private static double nd = 1.0E9;
	private static double targetFPS = 80;
	
	private int frames = 0;
	private double renderStartTime = System.nanoTime() / nd;
	private double renderEndTime = 0;
	private double renderTimeElapsed = 0;
	
	private int cycl = 0;
	private double updateStartTime = System.nanoTime() / nd;
	private double updateEndTime = 0;
	private double updateTimeElapsed = 0;

	public static AbstractGame game;
	public static Window window;

	public static Input input;
	public static Drawing d;
	public static Vector2 windowSize;
	public static float fps = 0;
	public static float ups = 0;

	public static double dt;
	private static boolean abstractGameStarted = false;
	public static boolean updateThreadRunning, renderThreadRunning;
	private ScheduledExecutorService updateExecutor, renderExecutor;
	private Runnable updateThread, renderThread;

	public GameContainer(Vector2 size) {
		Logger.startLogger();
		GameContainer.gc = this;
		
		GameContainer.windowSize = size;
		
		Logger.log("Creating Window");
		GameContainer.window = new Window(this);
		Logger.fine("Created");
	}
	
	public void setGame(final AbstractGame game) {
		GameContainer.game = game;
	}

	public void start() {
		Logger.log("Starting Engine");
		GameContainer.running = true;
		
		GameContainer.input = new Input(gc);
		Logger.log("Started Input Listener");
		GameContainer.d = new Drawing((Graphics2D) window.g);
		GameContainer.game.d = GameContainer.d;
		Logger.log("Created Drawing Stuff");

		startUpdateThread();
		
		startRenderThread();
		
		//TODO Nur komische Lösung!
		Window.frame.setSize(Window.frame.getSize());
	}

	private void startRenderThread() {
		//IDK was des macht
		//Toolkit.getDefaultToolkit().sync();

		Window.frame.setTitle("Illumi-Engine | " + Math.round((renderStartTime - renderEndTime) * 1000) + "ms | " + Math.round(dt*1000) +"ms");
		Logger.log("Starting RenderThread");
		
		//this.renderExecutor = Executors.newSingleThreadScheduledExecutor();
		
		GameContainer.renderThreadRunning = true;
		
		renderThread = new Runnable() {

			@Override
			public void run() {
					
					if (SceneManager.activeScene != null) {
						game.render();
						SceneManager.activeScene.render();
					}

					try {
						window.bs.show(); // Flipp the Buffer
					} catch (IllegalStateException e) {
					}

					renderEndTime = renderStartTime;
					renderStartTime = System.nanoTime() / nd;
					renderTimeElapsed += renderStartTime - renderEndTime;
					
					if (renderTimeElapsed >= 1) {
						fps = frames;
						frames = 0;
						renderTimeElapsed = 0;
					} else {
						//Add one fps
						frames++;
					}
					
					//IDK was des macht
					//Toolkit.getDefaultToolkit().sync();

					Window.frame.setTitle("Illumi-Engine | " + Math.round((renderStartTime - renderEndTime) * 1000) + "ms | " + Math.round(dt*1000) +"ms");
			}
		
		};
		
		//renderExecutor.scheduleAtFixedRate(renderThread, 0, (long) (1000000/GameContainer.targetFPS), TimeUnit.MICROSECONDS);
	}
	
	private void startUpdateThread() {
		Logger.log("Starting UpdateThread");
		
		updateExecutor = Executors.newSingleThreadScheduledExecutor();
		
		GameContainer.updateThreadRunning = true;
		dt = 1; // So it is not Stuck in the Beginning
	
		updateThread = new Runnable() {
			@Override
			public void run() {
				//Rezising
				window.update();
				
				if (GameContainer.renderThreadRunning && !abstractGameStarted)  {
					abstractGameStarted = true;
					game.start();
				}
				
				// GameUpdate BEGINN
				
				if (SceneManager.activeScene != null) {
					SceneManager.activeScene.update();
					game.update();
					input.update();

					// For Unloading the oldScene
					if (SceneManager.oldScene != null) { 
						SceneManager.oldScene.update();
					}
				}
				
				
				
				
			
				
				if (SceneManager.activeScene != null) {
					game.render();
					SceneManager.activeScene.render();
				}

				try {
					window.bs.show(); // Flipp the Buffer
				} catch (IllegalStateException e) {
				}

				renderEndTime = renderStartTime;
				renderStartTime = System.nanoTime() / nd;
				renderTimeElapsed += renderStartTime - renderEndTime;
				
				if (renderTimeElapsed >= 1) {
					fps = frames;
					frames = 0;
					renderTimeElapsed = 0;
				} else {
					//Add one fps
					frames++;
				}
				
				//IDK was des macht
				//Toolkit.getDefaultToolkit().sync();

				Window.frame.setTitle("Illumi-Engine | " + Math.round((renderStartTime - renderEndTime) * 1000) + "ms | " + Math.round(dt*1000) +"ms");
				
				
				
				
				
				
				
				
				

				// GameUpdate END

				updateEndTime = updateStartTime;
				updateStartTime = System.nanoTime() / nd;
				dt = (updateStartTime - updateEndTime);

				updateTimeElapsed += dt;
				
				if (updateTimeElapsed >= 1) {
					ups = cycl;
					cycl = 0; updateTimeElapsed = 0;
				} else {
					cycl++;
				}
			}
		};
		
		updateExecutor.scheduleAtFixedRate(updateThread, 0, (long) (1000000/GameContainer.targetFPS), TimeUnit.MICROSECONDS);
	}
	
	public double getStartTime() {
		return System.nanoTime() / nd;
	}
}