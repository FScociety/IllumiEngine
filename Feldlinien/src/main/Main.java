package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Physics.Feldlinie;
import Physics.Influencer;
import engine.game.AbstractGame;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.TransformController;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.gameobjects.gamebehaviour.ui.addObjects.addObjects;
import engine.math.Vector2;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class Main extends AbstractGame {
	
	Scene testScene;

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main());	
		gc.setSize(new Vector2(1000, 500));
		gc.start();
	}
	
	public Main() {
		testScene = new Scene("testScene") {
			
			Camera cam;
			
			public void instanceGameObjects() {
				
				int size = 30;
				int distance = 30;
				
				for (int i = 0; i < size; i++) {
					for (int i2 = 0; i2 < size; i2++) {
						GameObject feldObject = new GameObject(new Vector2(i*distance, i2*distance), true);
						feldObject.addComponent(new Feldlinie());
						testScene.addGameObject(feldObject);
					}
				}
				
				GameObject[] objectsToAdd = new GameObject[2];
				objectsToAdd[0] = new GameObject(new Vector2(0), true);
				objectsToAdd[0].addComponent(new Influencer(1));
				objectsToAdd[1] = new GameObject(new Vector2(0), true);
				objectsToAdd[1].addComponent(new Influencer(-1));
				
				GameObject objectAdder = new GameObject(new Vector2(0), false);
				objectAdder.addComponent(new addObjects(objectsToAdd, new Vector2(100)));
				testScene.addGameObject(objectAdder);
				
				
				GameObject camera = new GameObject(new Vector2(100), true);
				cam =  new Camera();
				camera.addComponent(cam);
				camera.addComponent(new GameBehaviour() {
					
					Vector2 oldMouse = new Vector2(0);
					Vector2 oldObject = new Vector2(0);
					boolean moving;
					
					public void update() { //Moving Camera with Mouse
						if (GameContainer.input.isButton(MouseEvent.BUTTON2)) {
							oldMouse = GameContainer.input.getMousePos(false).getCopy();
							oldObject = this.gameObject.getTransformWithCaution().position;
							moving = true;
						} else if (GameContainer.input.isButtonUp(MouseEvent.BUTTON2)) {
							moving = false;
						}
						
						if (moving) {
							this.gameObject.setPosition(Vector2.add(oldObject, Vector2.divide(Vector2.substract(oldMouse, GameContainer.input.getMousePos(false)), Camera.activeCam.zoom)));
						}
					}
				});
				testScene.addGameObject(camera);
			}
			
			public void sceneLoaded() {
				cam.view();
				cam.zoom=2;
			}
		};
	}
	
	public void start() {
		SceneManager.loadScene(testScene);
	}
	
	public void update() {
		Camera.activeCam.zoom += (float)GameContainer.input.getScroll() / 20;
	}
}