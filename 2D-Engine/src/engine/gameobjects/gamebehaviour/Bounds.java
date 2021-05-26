package engine.gameobjects.gamebehaviour;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;

public class Bounds extends GameBehaviour {
	
	private Vector2 size;
	
	private boolean hasToApplyObjPos = false;
	private Vector2 newPos;
	
	private Bounds parentBounds;
	private Vector2 alignment = Bounds.RIGHT_CENTER; //WURDE Umgestellt
	private Vector2 alignmentOffset = new Vector2(0);
	
	private static final int
		LEFT_int = -1, //Y: TOP
		CENTER_int = 0, //Y: CENTER
		RIGHT_int = 1; //Y: BOTTOM

	public static final Vector2 
		CENTER = new Vector2(CENTER_int, CENTER_int), 
		LEFT_CENTER = new Vector2(LEFT_int, CENTER_int), 
		RIGHT_CENTER = new Vector2(RIGHT_int, CENTER_int), 
		CENTER_TOP = new Vector2(CENTER_int, LEFT_int),
		CENTER_BOTTOM = new Vector2(CENTER_int, RIGHT_int),
		LEFT_TOP = new Vector2(LEFT_int, LEFT_int),
		LEFT_BOTTOM = new Vector2(LEFT_int, RIGHT_int),
		RIGHT_TOP = new Vector2(RIGHT_int, LEFT_int),
		RIGHT_BOTTOM = new Vector2(RIGHT_int, RIGHT_int);

	public Bounds(Vector2 size) {
		this.size = size;
	}
	
	public Bounds(Vector2 p1, Vector2 p2) {
		this.setBounds(p1, p2);
		
		//calcOffset wird schon mit "this.setBounds();" ausgeführt
	}

	public void calcOffset() {
		//NUR TEST
		this.alignmentOffset = this.gameObject.getLocalTransform().position;
		
		/*
		 * 
		 * 
		 * 
		 * TODO 
		 * Calc Offset wird 24/7 ausgeführt was glaub ich auch anders geht
		 * Ich glaub es würde sogar auch gehen, wenn man sihc einfach die localPos vom GameObject holt
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
	}
	
	public void start() {
		if (this.hasToApplyObjPos) {
			this.gameObject.setPosition(newPos);
			this.uigbUpdate();
		}
		
		this.calcOffset();
		
		//Find Parent for Alignment (if there is one)
		GameObject parentObj = this.gameObject.getParent();
		if (parentObj != null) {
			this.parentBounds = (Bounds) parentObj.getComponent(Bounds.class);
		}
	}
	
	public void setBounds(Vector2 p1, Vector2 p2) {
		Vector2 diffVec = new Vector2(p2, p1);
		this.size = diffVec.getCopy();
		diffVec.multiply(0.5f);
		
		this.newPos = Vector2.add(p1, diffVec);
		if (this.started) {
			this.gameObject.setPosition(newPos);
			this.uigbUpdate();
		} else {
			this.hasToApplyObjPos = true;
		}
	}
	
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public void setAlignment(Vector2 alignment) {
		this.alignment = alignment;
		this.uigbUpdate();
	}
	
	public void update() {
	}
	
	public void render() {
		if (GameContainer.debug) {
			this.d.setColor(Color.GREEN);
			this.d.drawCircle(new Vector2(10));
			this.d.setColor(Color.CYAN);
			this.d.drawRect(this);
		}
	}
	
	private void uigbUpdate() {
		//Updates smaller Components of this GameObject like "Button", "Input Field"
		
		for (GameBehaviour gb : this.gameObject.getComponents()) {
			if (gb.getClass().getSuperclass().getSimpleName().equals(UIGameBehaviour.class.getSimpleName())) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
			}
		}
	}
	
	public void updateBounds(final Bounds parent, final Vector2 change) {
		if (this.alignment.x == Bounds.LEFT_int) {
			this.gameObject.setPosition(new Vector2(this.alignmentOffset.x + -this.size.x, 0));
		} else if (this.alignment.x == Bounds.RIGHT_int) {
			this.gameObject.setPosition(new Vector2(this.parentBounds.size.x/2 - this.alignmentOffset.x, 0));	
		}
	}
	
	public Vector2 getSize() {
		return this.size;
	}
	
	public Vector2 getAlignment() {
		return this.alignment;
	}
	
	public String toString() {
		return this.size+"";
	}
}