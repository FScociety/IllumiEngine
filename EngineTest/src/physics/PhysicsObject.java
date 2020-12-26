package physics;

import engine.gameobjects.gamebehaviour.GameBehaviour;
import physics.collision.Collider;
import physics.effector.Effector;

public class PhysicsObject extends GameBehaviour {
	
	public Collider collid;
	public Effector effect;
	
	public PhysicsObject(Collider collid, Effector effect) {
		this.collid = collid;
		this.effect = effect;
		PhysicsWorld.phObjects.add(this);
	}
	
	public PhysicsObject(Collider collid) {
		this.collid = collid;
		PhysicsWorld.phObjects.add(this);
	}
	
	public void start() {
		this.collid.obj = this.gameObject;
		//TODO effect.obj = this.gameObject;
	}

}