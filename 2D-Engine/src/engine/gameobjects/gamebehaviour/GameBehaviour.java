package engine.gameobjects.gamebehaviour;

import java.io.Serializable;

import engine.game.Drawing;
import engine.gameobjects.GameObject;

public abstract class GameBehaviour implements Serializable {
	public boolean active = true;
	public GameObject gameObject;
	public Drawing d;
	public boolean started;
	
	public void delete() {}
	
	public void render() {}

	public void start() {}
	
	public void update() {}
}