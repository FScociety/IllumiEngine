package physics.collider;

import java.awt.Color;

import engine.gameobjects.GameObject;
import engine.math.Vector2;
import physics.PhysicsWorld;

public class CircleCollider extends Collider {
	
	public int r;
	
	public CircleCollider(int r) {
		this.r = r;
	}
	
	public void start() {
		PhysicsWorld.physicsObjects.add(this.gameObject);		
	}
	
	public void update() {
		if (PhysicsWorld.physicsObjects.size() > 1) { //If just '1' => Iam the only one
			for (GameObject obj : PhysicsWorld.physicsObjects) {
				if (obj != null && obj != this.gameObject) {
					this.cp = isCollideWithCircle(obj);
				}
			}
		}
	}
	
	private CollisionPoint isCollideWithCircle(GameObject obj) {
		float minDistance = (this.r/2 + ((CircleCollider)obj.getComponent(CircleCollider.class)).r/2) / 2;
		Vector2 distanceVec = Vector2.substract(obj.getTransformWithCaution().position, this.gameObject.getTransformWithCaution().position); 
		float distance = distanceVec.length();
		
		if (distance <= minDistance) {
			Vector2 direction = distanceVec.getCopy();
			distanceVec.normalize();
			distanceVec.multiply(this.r/2);
			return new CollisionPoint(distanceVec, direction);
		} else {
			return null;
		}
	}
	
	public void render() {
		this.d.setColor(Color.GREEN);
		this.d.drawCircle(new Vector2(r/2));
		
		if (cp != null) {
			cp.render(this.d);
		}
	}
	
	
}