package physics.collision;

import java.util.ArrayList;

import engine.gameobjects.GameObject;
import engine.math.Vector2;
import physics.collision.collider.CircleCollider;

public abstract class Collider {
	
	public ArrayList<Collision> collisions = new ArrayList<>();
	public GameObject obj;
	
	public abstract Collision getColWithCircle(CircleCollider cc);
	
	public static Collision circleAndCirlce(CircleCollider cc1, CircleCollider cc2) {
		float minDistance = cc1.radius + cc2.radius;
		Vector2 diffVec = new Vector2(cc1.obj.getTransformWithCaution().position, cc2.obj.getTransformWithCaution().position);
		float distance = diffVec.length();
		
		return distance <= minDistance ? new Collision(Vector2.multiply(diffVec.normalized(), cc1.radius), diffVec) : null;
	}
}