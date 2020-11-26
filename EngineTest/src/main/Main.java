package main;

import java.awt.Color;
import java.awt.event.KeyEvent;

import engine.game.AbstractGame;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.Camera;
import engine.gameobjects.gamebehaviour.ColorLabel;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
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
				GameObject profileObject = new GameObject(new Vector2(0));
				profileObject.inWorld = false;
				profileObject.addComponent(new Profile());
				betterScene.addGameObject(profileObject);
				
				GameObject test = new GameObject(new Vector2(0));
				test.addComponent(new addObjects());
				betterScene.addGameObject(test);
				
				camera = new GameObject(new Vector2(0));
				cam = new Camera();
				camera.addComponent(cam);	
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
		Camera.activeCam.zoom += GameContainer.input.getScroll() / 50f;
	}

	public void render() {	
		
	}
}