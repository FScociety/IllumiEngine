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
	private Alignment alignment;
	private Vector2 alignmentOffsetP1 = new Vector2(0);
	private Vector2 alignmentOffsetP2 = new Vector2(0);

	public RectTransform(Vector2 size, Alignment align) {
		this.size = size;
		this.alignment = align;
	}
	
	public RectTransform(Vector2 p1, Vector2 p2, Alignment align) {
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
	
	public void setPoint1(Vector2 point) {
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
	
	public void setPoint2(Vector2 point) {
		
	}
	
	public void setSize(Vector2 size) {
		this.size = size;
	}

	public void calcOffset() {
		if (this.alignment.x == RectTransform.LEFT_int) {
			this.alignmentOffset.x = this.gameObject.getLocalTransform().position.x;
		} else if (this.alignment.x == RectTransform.RIGHT_int) {
			this.alignmentOffset.x = -this.parentBounds.size.x/2 + this.gameObject.getTransformWithCaution().position.x;
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
	
	/*private void uigbUpdate() {
		//Updates smaller Components of this GameObject like "Button", "Input Field"
		
		for (GameBehaviour gb : this.gameObject.getComponents()) {
			//Geht wahrscheinlich einfacher
			if (gb.getClass().getSuperclass().getSimpleName().equals(UIGameBehaviour.class.getSimpleName())) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
			}
		}
	}*/
	
	public void updateBounds(final RectTransform parent) {
		Vector2 pos = this.gameObject.getTransformWithCaution().position;
		
		/*if (this.alignment.x == RectTransform.LEFT_int) {
			this.gameObject.setPosition(new Vector2(-this.parentBounds.size.x/2 + this.alignmentOffset.x, 0));
		} else if (this.alignment.x == RectTransform.RIGHT_int) {
			this.gameObject.setPosition(new Vector2(this.parentBounds.size.x/2 + this.alignmentOffset.x, 0));

		}*/
		
		if (this.alignment.getAlignP1().x == Alignment.LEFT_int) {
			
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