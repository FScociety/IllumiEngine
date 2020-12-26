package physics;

import java.util.ArrayList;

import physics.collision.Collider;
import physics.collision.collider.CircleCollider;
import physics.effector.Effector;

public class PhysicsWorld {
	
	public static ArrayList<PhysicsObject> phObjects = new ArrayList<>();
	
	public static void step() {
		for (PhysicsObject phObj1 : phObjects) {
			for (PhysicsObject phObj2 : phObjects) {
				if (phObj1 != phObj2 && phObj1.effect != null) {
					if (phObj2.collid.getClass().getName() == CircleCollider.class.getName()) {
						phObj1.collid.collisions.add(phObj1.collid.getColWithCircle((CircleCollider) phObj2.collid));
					}
				}
			}
		}
	}
}