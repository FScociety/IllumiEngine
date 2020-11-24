package world;

import java.awt.Color;

import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;

public class HealthBar extends GameBehaviour {
	
	int size;
	int maxValue;
	int value;
	
	public HealthBar(int size, int maxValue) {
		this.size = size;
		this.maxValue = maxValue;
		this.value = maxValue;
	}
	
	public void setHealth(int value) {
		this.value = value;
	}
	
	public void render() {
		this.d.setColor(Color.RED);
		this.d.drawRect(new Vector2(size, 10));
		this.d.setColor(Color.GREEN);
		this.d.fillRect(new Vector2(this.value-this.maxValue, 0), new Vector2(value * (size / maxValue),10));
	}

}
