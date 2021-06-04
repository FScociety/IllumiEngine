package engine.gameobjects.gamebehaviour.builtin.ui;

import engine.math.Vector2;

public class Alignment {
	
	public static final int
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
	
	private final Vector2 alignP1, alignP2;
	
	public Alignment(Vector2 alignP1, Vector2 alignP2) {
		this.alignP1 = alignP1;
		this.alignP2 = alignP2;
	}
	
	public Vector2 getAlignP1() {
		return this.alignP1;
	}
	
	public Vector2 getAlignP2() {
		return this.alignP2;
	}
}