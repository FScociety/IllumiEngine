package engine.gameobjects.gamebehaviour;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import engine.game.GameContainer;
import engine.math.Vector2;

public class addObjects extends GameBehaviour {
	
	private boolean opened = false;
	
	public void update() {
		if (GameContainer.input.isKeyDown(KeyEvent.VK_SHIFT)) {
			if (GameContainer.input.isKey(KeyEvent.VK_A)) {
				this.gameObject.setPosition(GameContainer.input.getMousePosToWorld());
				opened = true;
			}
		} else if (opened == true && GameContainer.input.isButton(MouseEvent.BUTTON1)) {
			opened = false;
		}
	}
	
	public void render() {
		if (!opened) {
			return;
		}
		this.d.setColor(Color.GRAY);
		this.d.fillRect(new Vector2(100));
	}
}