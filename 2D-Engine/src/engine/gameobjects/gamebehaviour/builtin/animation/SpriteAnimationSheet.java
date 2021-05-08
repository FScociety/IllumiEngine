package engine.gameobjects.gamebehaviour.builtin.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.math.Vector2;

public class SpriteAnimationSheet {
	
	private BufferedImage base;
	private SpriteAnimation[] sprites;
	private int spritesY;
	private int spriteSizeX, spriteSizeY;
	
	public SpriteAnimationSheet(BufferedImage base, int[] sizesX, Vector2 size) {
		this.base = base;
		this.generateInfos(sizesX, size);
	}
	
	public SpriteAnimationSheet(String path, int[] sizesX, Vector2 size) {
		try {
			 this.base = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			System.err.println("Could not load the sprite with Path: " + path);
		}
		
		this.generateInfos(sizesX, size);
	}
	
	private void generateInfos(int[] sizesX, Vector2 size) {

		this.spritesY = sizesX.length;
		this.spriteSizeX = (int)size.x;
		this.spriteSizeY = (int)size.y;
	
		sprites = new SpriteAnimation[this.spritesY];
		
		pullSmallerSprites(sizesX);		
	}
	
	private void pullSmallerSprites(int[] sizesX) {
		
		for (int y = 0; y < sprites.length; y++) {
			BufferedImage buffSprites[] = new BufferedImage[sizesX[y]];
			for (int x = 0; x < sizesX[y]; x++) {
		 		buffSprites[x] = base.getSubimage(x * this.spriteSizeX, y * this.spriteSizeY, this.spriteSizeX, this.spriteSizeY);
				
			}
			this.sprites[y] = new SpriteAnimation(buffSprites);
		}
	}
	
	public BufferedImage getSprite(int x, int y) {
		return this.sprites[y].getSprite(x);
	}
	
	public int getLengthX(int y) {
		return this.sprites[y].getLength();
	}
	
	public int getLengthY() {
		return this.spritesY;
	}
}