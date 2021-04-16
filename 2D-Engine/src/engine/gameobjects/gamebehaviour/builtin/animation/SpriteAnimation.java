package engine.gameobjects.gamebehaviour.builtin.animation;

import java.awt.Color;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class SpriteAnimation extends GameBehaviour {
	
	SpriteSheet ss;
	
	int posX,  posY;
	
	float time = 0;
	
	public SpriteAnimation(SpriteSheet ss) {
		this.ss = ss;
	}
	
	public void update() {
		time += GameContainer.dt;
		
		if (time >= 0.15f) {
			time = 0;
			if (posX < ss.getLengthX()-1) {
				posX++;
			} else {
				posX = 0;
			}
		}
	}
	
	public void render() {
		this.d.drawImage(ss.getSprite(posX, posY));
		this.d.setColor(Color.WHITE);
		this.d.drawRect(new Vector2(10,10));
		
		System.out.println(posX);
	}

}