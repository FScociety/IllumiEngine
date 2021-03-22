package game;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class PlayerObject extends GameBehaviour {

	private int size;
	private PlayerObject leader;
	private ArrayList<PlayerObject> childs;
	
	private Vector2 velocity;
	
	public PlayerObject(int size, PlayerObject leader) {
		this.velocity = new Vector2(0);
		this.setSize(size);
		this.leader = leader;
		if (leader == null) { //No leader => Iam the leader 
			childs = new ArrayList<PlayerObject>();
			this.leader = this;
		}
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void addSize(int size) {
		this.size += size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void addForce(Vector2 force) {
		this.velocity.add(force);
	}
	
	public void update() {
		if (GameContainer.input.isButton(1)) {
			split();
		}
		
		Vector2 movingVec = new Vector2(GameContainer.input.getMousePos(true), this.gameObject.getTransformWithCaution().position);
		velocity.add(Vector2.multiply(movingVec, (float)GameContainer.dt / 10));
		
		for (PlayerObject po : this.leader.childs) {
			Vector2 distanceVec = new Vector2(this.gameObject.getTransformWithCaution().position, po.gameObject.getTransformWithCaution().position);
			float minDistance = (this.size + po.size) / 2;
			float distance = distanceVec.length();
			
			if (distance <= minDistance) {
				this.gameObject.addPosition(movingVec);
			}
		}
		
		velocity.normalize();
		this.gameObject.addPosition(velocity);
	}
	
	private void split() {
		GameObject newSplit = new GameObject(Vector2.add(this.gameObject.getTransformWithCaution().position, new Vector2(200)), true);
		newSplit.addComponent(new PlayerObject((int)(this.size), this.leader));
		this.setSize(this.size);
		SceneManager.activeScene.addGameObject(newSplit);
		this.leader.childs.add((PlayerObject) newSplit.getComponent(PlayerObject.class));
	}
	
	public void render() {
		this.d.setColor(this.leader == this ? Color.PINK : Color.WHITE);
		this.d.fillCircle(new Vector2(size));
	}
}