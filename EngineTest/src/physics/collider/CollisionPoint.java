package physics.collider;

import java.awt.Color;

import engine.game.Drawing;
import engine.math.Vector2;

public class CollisionPoint {

	
	public Vector2 position, velocity;
	
	public CollisionPoint(Vector2 pos, Vector2 vel) {
		this.position = pos;
		this.velocity = vel;
	}
	
	public void render(Drawing d) {
		d.setColor(Color.PINK);
		d.fillCircle(this.position, new Vector2(5));
		d.drawLine(this.position, Vector2.add(velocity, this.position));
	}
}