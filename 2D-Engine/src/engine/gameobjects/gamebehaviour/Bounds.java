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
	
	private Vector2 point1;
	private Vector2 point2;
	
	private Vector2 bufferPoint1, bufferPoint2;
	
	private Vector2 alignment = Bounds.CENTER;
	
	private boolean scaling[] = new boolean[4];
	
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
	
	public Bounds(Vector2 p1, Vector2 p2) {
		this.bufferPoint1 = p1;
		this.bufferPoint2 = p2;
	}
	
	public Bounds(Vector2 size) {
		this.bufferPoint1 = Vector2.multiply(size, -0.5f);
		this.bufferPoint2 = Vector2.multiply(size, 0.5f);
	}
	
	public void start() {
		//Generate Real Points, cause now the GameObject != null
		this.setLocalBounds(this.bufferPoint1, this.bufferPoint2);
		
		//Chooses point with the max Distance
		int p1Distance = (int) this.point1.length();
		int p2Distance = (int) this.point2.length();
		this.gameObject.viewRange = p1Distance >= p2Distance ? p1Distance : p2Distance;
		
		//Find Parent for Alignment (if there is one)
		/*GameObject parentObj = this.gameObject.getParent();
		if (parentObj != null) {
			this.parent = (Bounds) parentObj.getComponent(Bounds.class);
		}*/
		
		System.out.println(this.point1 + " " + this.point2);
	}
	
	public void setBounds(Vector2 p1, Vector2 p2) {
		this.point1 = p1;
		this.point2 = p2;
		this.uigbUpdate();
	}
	
	public void setLocalBounds(Vector2 p1, Vector2 p2) {
		this.point1 = Vector2.add(this.gameObject.getTransformWithCaution().position, p1);
		this.point2 = Vector2.add(this.gameObject.getTransformWithCaution().position, p2);
		this.uigbUpdate();
	}
	
	public void setSize(Vector2 size) {
		this.point1 = Vector2.multiply(size, -0.5f);
		this.point1.add(this.gameObject.getTransformWithCaution().position);
		this.point2 = Vector2.multiply(size, 0.5f);
		this.point2.add(this.gameObject.getTransformWithCaution().position);
		this.uigbUpdate();
	}
	
	public void multiply(Vector2 mul) {
		this.point1.multiply(mul);
		this.point2.multiply(mul);
	}
	
	public void setPoint1(Vector2 p1) {
		this.point1 = p1;
		this.uigbUpdate();
	}
	
	public void setPoint2(Vector2 p2) {
		this.point2 = p2;
		this.uigbUpdate();
	}
	
	public void setAlignment(Vector2 alignment) {
		this.alignment = alignment;
		this.uigbUpdate();
	}
	
	public void update() {
		/*if (this.parent != null) {
			//TODO Aligment System!!!
		}*/
		
		if (GameContainer.debug) {
			if (GameContainer.input.isButtonDown(MouseEvent.BUTTON1)) {
				Vector2 mousePos = GameContainer.input.getMousePos(false).getCopy();
				Vector2 objectPos = this.gameObject.getTransformWithCaution().position;

				
			} else {
				this.scaling[0] = false;
			}
		}
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
		for (GameBehaviour gb : this.gameObject.getComponents()) {
			if (gb.getClass().getSuperclass().getSimpleName().equals(UIGameBehaviour.class.getSimpleName())) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
			}
		}
	}
	
	public Vector2 getPoint1() {
		return this.point1;
	}
	
	public Vector2 getGlobalPoint1() {
		Vector2 globalPoint = Vector2.add(this.gameObject.getTransformWithCaution().position, this.point1);
		return globalPoint;
	}
	
	public Vector2 getPoint2() {
		return this.point2;
	}
	
	public Vector2 getGlobalPoint2() {
		Vector2 globalPoint = Vector2.add(this.gameObject.getTransformWithCaution().position, this.point2);
		return globalPoint;
	}
	
	public Vector2 getSize() {
		Vector2 size = Vector2.substract(this.point1, this.point2);
		size.abs();
		return size;
	}
	
	public Vector2 getAlignment() {
		return this.alignment;
	}
	
	/*public Bounds getCopy(GameObject newObj) {
		return new Bounds(this.point1.getCopy(), this.scaling);
	}*/
	
	public String toString() {
		return this.point1 + " " + this.point2;
	}
}