package world;

import java.awt.Color;

import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;

public abstract class Organism extends GameBehaviour {
	
	int maxHealth = 100;
	int health;
	Color c;
	int size = 100;
	
	public Organism(int maxHealth, Color c) {
		this.maxHealth = maxHealth;
		this.health = this.maxHealth;
		this.c = c;
		Bullet.organisms.add(this);
	}
	
	public void damage(int damage) {
		/*this.health-=damage;
		if (this.health <= 0) {
			Bullet.organisms.remove(this);
			death();
		}
		HealthBar hb = this.gameObject.getChild().getComponent(HealthBar.class);
		hb.setHealth(this.health);*/
	}
	
	public abstract void death();
	
	public void heal(int healing) {
		this.health+=healing;
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void render() {
		this.d.setColor(c);
		this.d.fillCircle(new Vector2(this.size));
	}

}
