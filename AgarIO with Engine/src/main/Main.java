package main;

import java.awt.event.KeyEvent;

import engine.game.AbstractGame;
import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.math.Vector2;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
import game.PlayerObject;

public class Main extends AbstractGame {
	
	Scene inGame;
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main());	
		gc.setSize(new Vector2(1000, 500));
		gc.start();
	}
	
	public Main() {
		inGame = new Scene("InGame") {
			
			Camera cam;

			@Override
			public void instanceGameObjects() {
				GameObject player = new GameObject(new Vector2(0));
				player.addComponent(new PlayerObject(100, null));
				inGame.addGameObject(player);
				
				GameObject camera = new GameObject(new Vector2(0));
				cam = new Camera();
				camera.addComponent(cam);	
				camera.addComponent(new GameBehaviour() {
					public void update() {
						if (GameContainer.input.isKey(KeyEvent.VK_LEFT)) {
							this.gameObject.addPosition(new Vector2(-1,0));
						} else if (GameContainer.input.isKey(KeyEvent.VK_RIGHT)) {
							this.gameObject.addPosition(new Vector2(1,0));
						} else if (GameContainer.input.isKey(KeyEvent.VK_UP)) {
							this.gameObject.addPosition(new Vector2(0,-1));
						} else if (GameContainer.input.isKey(KeyEvent.VK_DOWN)) {
							this.gameObject.addPosition(new Vector2(0,1));
						}
					}
				});
				inGame.addGameObject(camera);
			}
			
			public void sceneLoaded() {
				cam.view();
			}
		};
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		SceneManager.loadScene(inGame);
		
	}

	@Override
	public void update() {
		Camera.activeCam.zoom += GameContainer.input.getScroll() / 50f;
		
	}
}