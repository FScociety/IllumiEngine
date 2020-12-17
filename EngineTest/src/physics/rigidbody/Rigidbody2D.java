package physics.rigidbody;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;
import physics.collider.Collider;

public class Rigidbody2D extends GameBehaviour {
	
	public Vector2 velocity = new Vector2();
	public Collider c;
	
	public void start() {
		this.c = (Collider)this.gameObject.getComponent(Collider.class);
	}
	
	public void update() {
		if (c.cp != null) {
			this.velocity.substract(Vector2.multiply(c.cp.velocity, (float)GameContainer.dt));
		}
		this.gameObject.addPosition(Vector2.multiply(velocity, (float)GameContainer.dt * 100));
		this.velocity.substract(Vector2.multiply(Vector2.substract(this.velocity, 0.01f), (float)GameContainer.dt));
	}
}
