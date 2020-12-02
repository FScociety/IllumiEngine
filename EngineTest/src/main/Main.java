package main;

import java.awt.Color;
import java.awt.event.KeyEvent;

import engine.game.AbstractGame;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.ColorLabel;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.gameobjects.gamebehaviour.TransformController;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.gameobjects.gamebehaviour.camera.CameraController;
import engine.gameobjects.gamebehaviour.ui.Profile;
import engine.gameobjects.gamebehaviour.ui.addObjects.addObjects;
import engine.gameobjects.gamebehaviour.ui.interactable.Button;
import engine.math.Vector2;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class Main extends AbstractGame {
	
	Scene betterScene;
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main());	
		gc.setSize(new Vector2(1000, 500));
		gc.start();
	}
	
	public Main() {
		betterScene = new Scene("betterScene") {
			Camera cam;
			GameObject camera;

			public void instanceGameObjects() {
				GameObject profileObject = new GameObject(new Vector2(0), false);
				profileObject.addComponent(new Profile());
				betterScene.addGameObject(profileObject);
				
				for (int i = 0; i < 1; i++) {
					GameObject test = new GameObject(new Vector2(0), true);
					test.addComponent(new Button("Hi", new Vector2(100)));
					betterScene.addGameObject(test);
				}
				
				camera = new GameObject(new Vector2(0), true);
				cam = new Camera();
				camera.addComponent(cam);	
				camera.addComponent(new CameraController(true, true, true));
				betterScene.addGameObject(camera);
			}

			public void sceneLoaded() {
				cam.view();
			}
			
		};
	}
	
	public void start() {
		SceneManager.loadScene(betterScene);
		
	}
	
	public void update() {

	}

	public void render() {	
		
	}
}
	
	/*
	 * Wo zum fick wird nach "RenderThread.. => ", "null" ausgegen.
	 * Wer macht das?!
	 * Warum?!
	 * Es wird sehr sicher in UpdateThread ausgeführt
	 * Vllt in der Szene oder so
	 * Aber das weiß man halt
	 * Was schreib ich schon wieder für einen scheiß 
	 */