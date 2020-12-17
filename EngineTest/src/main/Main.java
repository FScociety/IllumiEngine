package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
import physics.PhysicsWorld;
import physics.collider.CircleCollider;
import physics.rigidbody.Rigidbody2D;

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
				
				for (int i = 1; i < 5; i++) {
					GameObject circleObj = new GameObject(new Vector2(0, i * 110), true);
					circleObj.addComponent(new CircleCollider(100));
					circleObj.addComponent(new Rigidbody2D());
					scene1.addGameObject(circleObj);
				}
				
				GameObject circleMouse = new GameObject(new Vector2(0), true);
				circleMouse.addComponent(new CircleCollider(100));
				Rigidbody2D rgb = new Rigidbody2D();
				rgb.velocity.add(new Vector2(0, 3));
				circleMouse.addComponent(rgb);
				circleMouse.addComponent(new GameBehaviour() {
					public void update() {
						if (GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
							Vector2 subVec = new Vector2(GameContainer.input.getMousePos(true), this.gameObject.getTransformWithCaution().position);
							subVec.normalize();
							subVec.multiply((float)GameContainer.dt * 10);
							rgb.velocity.add(subVec);
						}
					}
				});
				scene1.addGameObject(circleMouse);

				camera = new GameObject(new Vector2(0), true);
				cam = new Camera();
				camera.addComponent(cam);	
				camera.addComponent(new CameraController(true, true, true));
				scene1.addGameObject(camera);
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
		PhysicsWorld.update();
	}

	public void render() {	
		
	}
}