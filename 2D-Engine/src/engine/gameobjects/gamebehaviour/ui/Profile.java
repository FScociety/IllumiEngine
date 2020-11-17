// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui;

import java.awt.Color;

import engine.game.GameContainer;
import engine.game.Window;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Profile extends GameBehaviour {

	GameObject ois;

	@Override
	public void render() {
		d.setColor(Color.DARK_GRAY);
		d.fillRect(new Vector2(50), new Vector2(100));
		d.setColor(Color.WHITE);
		d.setFont(Window.standartFont);
		d.drawString("FramesPerSecond: " + GameContainer.fps, new Vector2(0, 10));
		d.drawString("UpdatesPerSecond: " + GameContainer.ups, new Vector2(0, 25));
		d.drawString("Objects: " + SceneManager.activeScene.getObjectCount(), new Vector2(0, 40));

	}

	@Override
	public void start() {

	}

	@Override
	public void update() {

	}
}
