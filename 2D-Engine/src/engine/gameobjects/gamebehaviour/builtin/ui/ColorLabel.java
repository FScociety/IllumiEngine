package engine.gameobjects.gamebehaviour.builtin.ui;

import java.awt.Color;

import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class ColorLabel extends GameBehaviour {
	private Vector2 size;
	private Color c;

	public ColorLabel(final Color c, Vector2 size) {
		this.c = c;
		this.size = size;
	}

	@Override
	public void render() {
		d.setColor(this.c);
		d.fillRect(size);
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
}