package engine.gameobjects.gamebehaviour;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class Canvas extends Bounds {

	public Canvas(Vector2 size) {
		super(size);
	}
	
	public void update() {
		this.setBounds(new Vector2(0), GameContainer.gc.getSize());
	}
}