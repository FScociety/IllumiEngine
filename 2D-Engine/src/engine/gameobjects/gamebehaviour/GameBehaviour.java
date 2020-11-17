package engine.gameobjects.gamebehaviour;

import engine.game.Drawing;
import engine.gameobjects.GameObject;
import engine.math.Vector2;

public abstract class GameBehaviour {
	protected boolean active;
	public GameObject gameObject;
	public Drawing d;
	public boolean started;
	
	public void start() {}
	
	public void update() {}

	public void render() {}
	
	public void delete() {}
}