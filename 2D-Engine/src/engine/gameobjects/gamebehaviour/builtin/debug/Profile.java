// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.builtin.debug;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.game.Window;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.builtin.ui.interactable.Interactable;
import engine.gameobjects.gamebehaviour.builtin.camera.Camera;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Profile extends GameBehaviour implements KeyListener {

	GameObject ois;

	ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

	public Profile() {
		GameContainer.window.addKeyListener(this);
		this.prefferedInWorldState = 0;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (pressedKeys.contains(arg0.getKeyCode())) {

		} else {
			pressedKeys.add(arg0.getKeyCode());

			if (pressedKeys.size() >= 5) {
				pressedKeys.remove(0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void render() {
		d.setColor(Color.DARK_GRAY);
		d.setColor(Color.WHITE);
		d.setFont(Window.standartFont);
		d.drawString("FramesPerSecond: " + GameContainer.fps, 10, new Vector2(0, 10));
		d.drawString("UpdatesPerSecond: " + GameContainer.ups, 10, new Vector2(0, 25));
		d.drawString("Objects: " + SceneManager.activeScene.getObjectCount(), 10, new Vector2(0, 40));
		if (Interactable.objectFocused != null) {
			d.drawString("Selected:" + Interactable.objectFocused.toStringShort(), 10, new Vector2(0, 55));
		} else {
			d.drawString("Selected:" + Interactable.objectFocused, 10, new Vector2(0, 55));
		}

		d.drawString("Zoom:" + Camera.activeCam.zoom, 10, new Vector2(0, 70));
		
		d.drawString("Camera: " + Camera.activeCam, 10, new Vector2(0,85));

		d.drawString("Keys:", 10, new Vector2(0, 100));

		for (int i = 0; i < pressedKeys.size(); i++) {
			d.drawString(KeyEvent.getKeyText(pressedKeys.get(i)) + " (" + pressedKeys.get(i) + ")", 10,
					new Vector2(0, i * 15 + 115));
		}

	}

	@Override
	public void start() {

	}

	@Override
	public void update() {

	}
}