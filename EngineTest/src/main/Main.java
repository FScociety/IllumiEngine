package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import engine.game.AbstractGame;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.builtin.debug.Profile;
import engine.gameobjects.gamebehaviour.builtin.debug.TransformController;
import engine.gameobjects.gamebehaviour.builtin.debug.addObjects.addObjects;
import engine.gameobjects.gamebehaviour.builtin.ui.ColorLabel;
import engine.gameobjects.gamebehaviour.builtin.ui.Text2;
import engine.gameobjects.gamebehaviour.builtin.ui.interactable.Button;
import engine.gameobjects.gamebehaviour.builtin.ui.interactable.CheckBox;
import engine.gameobjects.gamebehaviour.builtin.ui.interactable.InputField;
import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.builtin.camera.Camera;
import engine.gameobjects.gamebehaviour.builtin.camera.CameraController;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
import physics.PhysicsObject;
import physics.PhysicsWorld;
import physics.collision.collider.CircleCollider;

public class Main extends AbstractGame {
	
	Scene scene1, scene2;
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main());	
		gc.setSize(new Vector2(1000, 500));
		gc.start();
	}
	
	public Main() {
		scene1 = new Scene("1") {
			Camera cam;
			GameObject camera;

			public void instanceGameObjects() {
				GameObject profileObject = new GameObject(new Vector2(0), false);
				profileObject.addComponent(new Profile());
				scene1.addGameObject(profileObject);
				
				GameObject buttonObj = new GameObject(new Vector2(300, 100), false);
				Bounds b = new Bounds(new Vector2(-200, -50), new Vector2(100, 0)); //TODO POinter nicht an der richtigen Stelle und Bounds affecten Button nicht so richtig
				buttonObj.addComponent(b);
				//buttonObj.addComponent(new InputField(b));
				buttonObj.addComponent(new TransformController());
				scene1.addGameObject(buttonObj);
				
				camera = scene1.getGameObject(0);
				cam = (Camera) camera.getComponent(Camera.class);
			}

			public void sceneLoaded() {
				cam.view();
			}
			
		};
		
	}
	
	public void start() {
		SceneManager.loadScene(scene1);
	}
	
	public void update() {

	}

	public void render() {	

	}
}