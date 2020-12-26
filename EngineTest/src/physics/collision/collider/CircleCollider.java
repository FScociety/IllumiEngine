package physics.collision.collider;

import physics.collision.Collider;
import physics.collision.Collision;

public class CircleCollider extends Collider {
	
	public float radius;
	
	public CircleCollider(float r) {
		this.radius = r;
	}

	@Override
	public Collision getColWithCircle(CircleCollider cc) {
		return Collider.circleAndCirlce(this, cc);
	}

}