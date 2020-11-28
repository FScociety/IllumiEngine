package engine.gameobjects.gamebehaviour;

import java.io.Serializable;

import engine.game.Drawing;
import engine.gameobjects.GameObject;

public abstract class GameBehaviour implements Serializable {
	public boolean active = true;
	public GameObject gameObject;
	public Drawing d;
	public boolean started;
	protected int prefferedInWorldState = 2; // 2=doesntMatter; 0=false; 1=true

	public void delete() {
	}

	public void render() {
	}

	public void start() {
	}

	public boolean testInWorldStateSame() { // Test if the state of the Behaviour is compatible with the from gameObject
		if (prefferedInWorldState == 2) {
			return true;
		} else if ((this.gameObject.getInWorld() ? 1 : 0) != prefferedInWorldState) { // Behaviour not compatible !
																						// Sorry
			this.gameObject.removeComponent(this);
			System.err.println(
					this.getClass() + " no compatible with GameObjects inWorld State:" + this.gameObject.getInWorld());
			return false;
		}
		return true;
	}

	public void update() {
	}
}