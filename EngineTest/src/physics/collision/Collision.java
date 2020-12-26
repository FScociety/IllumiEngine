package physics.collision;

import engine.math.Vector2;

public class Collision {
	
	private Vector2 localPosition; //Position of 2Object-Collision
	private Vector2 normal;		  //Direction and Force transmitted
	
	public Collision(Vector2 locPos, Vector2 norm) {
		this.localPosition = locPos;
		this.normal = norm;
	}
	
	public Vector2 getPos() {
		return this.localPosition;
	}
	
	public Vector2 getNorm() {
		return this.normal;
	}
}