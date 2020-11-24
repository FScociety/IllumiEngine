package world;

import java.awt.Color;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Enemy extends Organism {

	Player p;
	
	float time;
	float reloadingTime = 0.1f;
	int sichtweite = 750;
	int fov = 90;
	
	float targetAngle;
	float lastTargetAngle;
	float toDoAngle;
	
	public boolean inView;
	
	public Enemy(Player p) {
		super(10, Color.RED);
		this.p = p;
	}
	
	public void start() {
		this.gameObject.viewRange = 1000000000;
	}
	
	private boolean inView(Organism org) {
		Vector2 objectPos = org.gameObject.getTransformWithCaution().position;
		float angle = this.gameObject.getTransformWithCaution().rotation;
		Vector2 distance = Vector2.substract(org.gameObject.getTransformWithCaution().position, org.gameObject.getTransformWithCaution().position);
		
		return Vector2.abs(distance).length() <= this.sichtweite && collideWithLine(org, angle, fov/2) && collideWithLine(org, angle, -fov/2);
	}
	
	private boolean collideWithLine(Organism org, float angle, float angleOffset) {
		Vector2 point = org.gameObject.getTransformWithCaution().position.getCopy();
		point.rotate(-angle + angleOffset, this.gameObject.getTransformWithCaution().position);
		Vector2 distanceToLine = Vector2.substract(point, this.gameObject.getTransformWithCaution().position);
		System.out.println(distanceToLine);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (angleOffset > 0) {
			return (distanceToLine.y <= org.size/2 && distanceToLine.x > 0);	
		} else if (angleOffset < 0) {
			return (distanceToLine.y >= -org.size/2 && distanceToLine.x > 0);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		}
		return false;
	}
	
	public void update() {
		time+=GameContainer.dt;
		
		if (inView(p)) {
			Vector2 targetVec = new Vector2(this.gameObject.getTransformWithCaution().position, p.gameObject.getTransformWithCaution().position);
			this.lastTargetAngle = this.targetAngle;
			this.targetAngle = targetVec.toAngle();
			if (this.lastTargetAngle - this.targetAngle > 50) {
				this.gameObject.addRotation(-360);
			} else if (this.lastTargetAngle - this.targetAngle < -50) {
				this.gameObject.addRotation(360);
			}
		} else {
			//toDoAngle -= toDoAngle/2 * GameContainer.dt;
		}
		
		toDoAngle = this.targetAngle - this.gameObject.getTransformWithCaution().rotation;
		this.gameObject.addRotation((float) (toDoAngle * GameContainer.dt * 3));	
		
		if (time >= reloadingTime) {
			time = 0;
			int viewing = 0;
			for (Organism org : Bullet.organisms) {
				if (org != this && org != p) {
					viewing = inView(org) ? 1 : 0;
				}
			}
			if (viewing <= 0) {
				/*GameObject bObj = new GameObject(this.gameObject.getTransformWithCaution().position);
				bObj.addComponent(new Bullet(this.gameObject, subVec.getCopy().inverted().normalized()));
				SceneManager.activeScene.addGameObject(bObj);*/
				inView = false;
			} else {
				inView = true;
			}
		}
	}
	
	public void render() {
		super.render();
		this.d.setColor(this.inView ? Color.GREEN : Color.RED);
		Vector2 vec = new Vector2(this.sichtweite, 0);
		Vector2 vec2 = vec.getCopy();
		vec2.rotate(fov/2);
		Vector2 vec3 = vec.getCopy();
		vec3.rotate(-fov/2);
		
		this.d.drawLine(vec);
		this.d.drawLine(vec2);
		this.d.drawLine(vec3);
	}

	public void death() {
		SceneManager.activeScene.removeGameObject(this.gameObject);
	}

}
