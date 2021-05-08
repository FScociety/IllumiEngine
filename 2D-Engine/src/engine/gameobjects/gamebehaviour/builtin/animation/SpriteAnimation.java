package engine.gameobjects.gamebehaviour.builtin.animation;

import java.awt.image.BufferedImage;

public class SpriteAnimation {
	
	private BufferedImage[] sprites;
	
	public SpriteAnimation(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	
	public BufferedImage getSprite(int pos) {
		return this.sprites[pos];
	}
	
	public int getLength() {
		return this.sprites.length;
	}
}