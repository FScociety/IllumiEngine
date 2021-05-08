package engine.gameobjects.gamebehaviour.builtin.animation;

import java.awt.Color;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;

public class SpriteAnimator extends GameBehaviour {
	
	SpriteAnimationSheet ss;
	
	int posX,  posY;
	int newPosY;
	float newTimePerFrame;
	boolean playing = false;
	boolean wannaStop = false;
	boolean wannaSwitch = false;
	
	float time = 0;
	public float timePerFrame = 0.1f;
	
	public SpriteAnimator(SpriteAnimationSheet ss) {
		this.ss = ss;
	}
	
	public void update() {
		if (playing == true) {
			time += GameContainer.dt;
			
			if (time >= this.timePerFrame) {
				time = 0;
				if (posX < ss.getLengthX(this.posY)-1) {
					posX++;
				} else {
					posX = 0;
					
					if (this.wannaSwitch) {
						if (this.wannaStop) {
							this.stopInstant();
							this.wannaStop = false;
						} else {
							this.posY = this.newPosY;
							this.timePerFrame = newTimePerFrame;
						}
						this.wannaSwitch = false;
					}
				}
			}
		}
	}
	
	public void render() {
		if (this.playing) {
		this.d.drawImage(ss.getSprite(posX, posY));
		this.d.setColor(Color.WHITE);
		this.d.drawRect(new Vector2(15,15));
		}
	}
	
	public void playInstant(int posY) {
		this.playing = true;
		this.posY = posY;
	}
	
	public void playInstant(int posY, float timePerFrame) {
		this.playing = true;
		this.posY = posY;
		this.newTimePerFrame = timePerFrame;
	}
	
	public void playAfterFinish(int posY) {
		if (this.playing == false) {
			this.playInstant(posY);
		} else {
			this.wannaSwitch = true;
			this.newPosY = posY;
		}
	}
	
	public void playAfterFinish(int posY, float timePerFrame) {
		if (this.playing == false) {
			this.playInstant(posY, timePerFrame);
		} else {
			this.wannaSwitch = true;
			this.newPosY = posY;
			this.newTimePerFrame = timePerFrame;
		}
	}
	
	public void stopInstant() {
		this.playing = false;
	}
	
	public void stopAfterFinish() {
		this.wannaSwitch = true;
		this.wannaStop = true;
	}

}