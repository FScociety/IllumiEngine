package engine.gameobjects.gamebehaviour;

import java.awt.Color;

import engine.math.Vector2;

public class addObjects extends GameBehaviour {
	
	public void render() {
		this.d.setColor(Color.GRAY);
		this.d.fillRect(new Vector2(100));
	}
}