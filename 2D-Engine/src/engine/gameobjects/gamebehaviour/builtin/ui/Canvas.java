package engine.gameobjects.gamebehaviour.builtin.ui;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class Canvas extends RectTransform {

	public Canvas(Vector2 size) {
		super(size, Alignment.CENTER);
		this.setBounds(new Vector2(0), GameContainer.windowSize);
	}
	
	public void ScreenSizeChange() {
		this.setBounds(new Vector2(0), GameContainer.windowSize);
	}
}