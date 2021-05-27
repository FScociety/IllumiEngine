package engine.gameobjects.gamebehaviour.builtin.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.io.Logger;
import engine.math.Vector2;

public class RectTransform extends GameBehaviour {
	
	private Vector2 size;
	
	private boolean hasToApplyObjPos = false;
	private Vector2 newPos;
	
	private RectTransform parentBounds;
	private Vector2 alignment = RectTransform.RIGHT_CENTER; //WURDE Umgestellt
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

	public RectTransform(Vector2 size) {
		this.size = size;
	}
	
	public RectTransform(Vector2 p1, Vector2 p2) {
		this.setBounds(p1, p2);
	}

	public void calcOffset() {
		if (this.alignment.x == RectTransform.LEFT_int) {
			this.alignmentOffset.x = this.gameObject.getLocalTransform().position.x;
		} else if (this.alignment.x == RectTransform.RIGHT_int) {
			//this.alignmentOffset.x = -this.getSize().x/2 + this.gameObject.getLocalTransform().position.x;
			this.alignmentOffset.x = 250;
		}
		
		Logger.println(this.parentBounds+"",this.gameObject.getLocalTransform().position.x+"", 1);
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
			this.parentBounds = (RectTransform) parentObj.getComponent(RectTransform.class);
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
			//Geht wahrscheinlich einfacher
			if (gb.getClass().getSuperclass().getSimpleName().equals(UIGameBehaviour.class.getSimpleName())) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
			}
		}
	}
	
	public void updateBounds(final RectTransform parent, final Vector2 change) {
		if (this.alignment.x == RectTransform.LEFT_int) {
			this.gameObject.setPosition(new Vector2(-this.parentBounds.size.x/2 + this.alignmentOffset.x, 0));
		} else if (this.alignment.x == RectTransform.RIGHT_int) {
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