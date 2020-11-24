package world;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Bullet extends GameBehaviour {
	
	public static ArrayList<Organism> organisms = new ArrayList<Organism>();
	private Vector2 moving;
	private GameObject shooter;
	
	private float time;
	
	public Bullet(GameObject shooter, Vector2 force) {
		this.shooter = shooter;
		moving = force;
	}
	
	public void update() {
		time += GameContainer.dt;
		if (time >= 5) {
			SceneManager.activeScene.removeGameObject(this.gameObject);
		}
		
		Object[] organismss = this.organisms.toArray();
		for (Object obj : organismss) {
			Organism org = (Organism)obj;
			if (org.gameObject != shooter) {
				Vector2 subVec = Vector2.substract(org.gameObject.getTransformWithCaution().position, this.gameObject.getTransformWithCaution().position);
				subVec.abs();
				
				int minSize = 5 + 50;
				
				if (subVec.length() <= minSize) {
					org.damage(5);
					SceneManager.activeScene.removeGameObject(this.gameObject);
				}
			}
		}
		
		this.gameObject.addPosition(Vector2.multiply(moving, (float)-GameContainer.dt * 500));
	}
	
	public void render() {
		this.d.setColor(Color.PINK);
		this.d.fillCircle(new Vector2(10));
	}
	
}
