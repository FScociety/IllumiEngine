package Physics;

import java.awt.Color;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;

public class Feldlinie extends GameBehaviour {
	
	private Vector2 force = new Vector2(0);

	public void start() {
		this.gameObject.setRotation((float) (Math.random()*360));
	}
	
	public void update() {
		float angle = 0;
		force = new Vector2(1,0);
		for (Influencer inf : Influencer.influencers) {
			Vector2 oldForce = force.getCopy();
			Vector2 diffVec = new Vector2(inf.gameObject.getTransformWithCaution().position, this.gameObject.getTransformWithCaution().position);
			diffVec.divide(Vector2.multiply(inf.gameObject.getTransformWithCaution().scale, inf.gameObject.getTransformWithCaution().scale));
			float length = 1 / (diffVec.length()/300);
			diffVec.normalize();
			diffVec.multiply(length);
			if (inf.charge == -1) {
				diffVec.rotate(180);
			}
			this.force.add(diffVec);
			
		}
		this.gameObject.setRotation(force.toAngle() + 180);
	}
	
	public void render() {
		this.d.setColor(Color.WHITE);
		this.d.drawLine(new Vector2(10, 0));
		this.d.fillCircle(new Vector2(20, 0), new Vector2(5));
	}

}
