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
	private Vector2 alignment;
	private Vector2 alignmentOffset = new Vector2(0);

	public RectTransform(Vector2 size, Vector2 align) {
		this.size = size;
		this.alignment = align;
	}
	
	public RectTransform(Vector2 p1, Vector2 p2, Vector2 align) {
		this.setBounds(p1, p2);
		this.alignment = align;
	}
	
	public void setBounds(Vector2 p1, Vector2 p2) {
		Vector2 diffVec = new Vector2(p2, p1);
		this.size = diffVec.getCopy();
		diffVec.multiply(0.5f);
		
		this.newPos = Vector2.add(p1, diffVec);
		
		if (this.started) {
			this.gameObject.setPosition(newPos);
		} else {
			this.hasToApplyObjPos = true;
		}
	}
	
	public void setSize(Vector2 size) {
		this.size = size;
	}

	public void calcOffset() {
		//You could mix "LEFT" & "RIGHT", but the question is,
		//is a "if" faster or slowed than "multi"
		
		//X
		if (this.alignment.x == Alignment.LEFT_int) {
			this.alignmentOffset.x = this.parentBounds.size.x/2 + this.gameObject.getLocalTransform().position.x;
		} else if (this.alignment.x == Alignment.RIGHT_int) {
			this.alignmentOffset.x = -this.parentBounds.size.x/2 + this.gameObject.getLocalTransform().position.x;
		} else if (this.alignment.x == Alignment.SCALE_int) {
			//Not positon offset, this will be used for saving the scaling aspect with parent
			this.alignmentOffset.x = this.parentBounds.size.x / this.size.x;
		}
		
		//Y
		if (this.alignment.y == Alignment.LEFT_int) {
			this.alignmentOffset.y = this.parentBounds.size.y/2 + this.gameObject.getLocalTransform().position.y;
		} else if (this.alignment.y == Alignment.RIGHT_int) {
			this.alignmentOffset.y = -this.parentBounds.size.y/2 + this.gameObject.getLocalTransform().position.y;
		} else if (this.alignment.y == Alignment.SCALE_int) {
			//Not positon offset, this will be used for saving the scaling aspect with parent
			this.alignmentOffset.y = this.parentBounds.size.y / this.size.y;
		}
	}
	
	public void start() {
		if (this.hasToApplyObjPos) {
			this.gameObject.setPosition(newPos);
		}
		
		//Find Parent for Alignment (if there is one)
		GameObject parentObj = this.gameObject.getParent();
		if (parentObj != null) {
			this.parentBounds = (RectTransform) parentObj.getComponent(RectTransform.class);
			this.calcOffset();
		}
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
	
	public void updateBounds(final RectTransform parent) {
		Vector2 pos = this.gameObject.getLocalTransform().position;
		
		//X
		if (this.alignment.x == Alignment.LEFT_int) {
			this.gameObject.setPosition(new Vector2(-this.parentBounds.size.x/2 + this.alignmentOffset.x, pos.y));
		} else if (this.alignment.x == Alignment.RIGHT_int) {
			this.gameObject.setPosition(new Vector2(this.parentBounds.size.x/2 + this.alignmentOffset.x, pos.y));
		} else if (this.alignment.x == Alignment.SCALE_int) {
			this.size.x = this.parentBounds.size.x / this.alignmentOffset.x;
		}
		
		pos = this.gameObject.getLocalTransform().position;
		
		//Y
		if (this.alignment.y == Alignment.LEFT_int) {
			this.gameObject.setPosition(new Vector2(pos.x, -this.parentBounds.size.y/2 + this.alignmentOffset.y));
		} else if (this.alignment.y == Alignment.RIGHT_int) {
			this.gameObject.setPosition(new Vector2(pos.x, this.parentBounds.size.y/2 + this.alignmentOffset.y));
		} else if (this.alignment.y == Alignment.SCALE_int) {
			this.size.y = this.parentBounds.size.y / this.alignmentOffset.y;
		}
		
		this.updateComponents();
	}
	
	public void updateComponents() {
		for (GameBehaviour gb : this.gameObject.getComponents()) {
			if (gb.getType().equals("ui")) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
			}
		}
	}
	
	public Vector2 getSize() {
		return this.size;
	}
	
	public Vector2 getPoint1() {
		return new Vector2(-this.size.x/2, -this.size.y/2);
	}
	
	public Vector2 getPoint2() {
		return new Vector2(this.size.x/2, -this.size.y/2);
	}
	
	public Vector2 getPoint3() {
		return new Vector2(this.size.x/2, this.size.y/2);
	}
	
	public Vector2 getPoint4() {
		return new Vector2(-this.size.x/2, this.size.y/2);
	}
	
	public Vector2 getAlignment() {
		return this.alignment;
	}
	
	public String toString() {
		return this.size+"";
	}
}