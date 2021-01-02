package engine.gameobjects.gamebehaviour.builtin.ui;

import java.awt.Color;

import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;

public class ColorLabel extends UIGameBehaviour {
	
	private Color c;

	public ColorLabel(final Color c, Bounds b) {
		this.c = c;
		this.bounds = b;
	}

	@Override
	public void render() {
		this.d.setColor(this.c);
		this.d.fillRect(this.bounds);
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
}