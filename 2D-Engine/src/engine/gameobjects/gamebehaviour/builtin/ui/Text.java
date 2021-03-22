package engine.gameobjects.gamebehaviour.builtin.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import engine.game.GameContainer;
import engine.game.Window;
import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;

public class Text extends UIGameBehaviour {
	
	private String text;
	
	private Font font;
	private float prefferedSize;
	private float realSize;
	private Color color;
	
	private Vector2 alignment = Text.CENTER;
	private Vector2 offset = new Vector2(0);
	
	boolean autoFit = false;
	
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

	public Text(Bounds b) {
		this.text = "";
		this.prefferedSize = 20;
		this.color = Color.RED;
		this.font = Window.standartFont;
		this.bounds = b;
	}
	
	public Text(String text, int size, Color c, Bounds b) {
		this.text = text;
		this.prefferedSize = size;
		this.color = c;
		this.bounds = b;
		this.font = Window.standartFont;
	}
	
	@Override
	public void start() {
		this.realSize = this.prefferedSize;
		this.textUpdate();
	}

	@Override
	public void render() {
		d.setFont(this.font);
		d.setColor(this.color);
		d.drawString(this.text, this.realSize, this.offset);
	}
	
	private void textUpdate() {
		float width;
		
		if (this.autoFit) {
			//Fit the text into the Bounds
			//Get Text length
			width = this.d.getFontSize(this.font).stringWidth(text) * this.realSize;
			//Compare it to max Bounds
			float div = this.bounds.getSize().x / width;
			//Apply 'div' to font size
			this.realSize *= div;
		}
		
		//Y-Alignmnet
		if (this.alignment.y == Text.CENTER_int) { //CENTER
			this.offset.y = (-this.bounds.getSize().y + this.realSize) / 2;
		} else if (this.alignment.y == Text.LEFT_int) { //TOP
			this.offset.y = this.bounds.getPoint1().y + this.realSize;
		} else if (this.alignment.y == Text.RIGHT_int) { //BOTTON
			this.offset.y = this.bounds.getPoint2().y;
		}
		
		//X-Alignment
		width = this.d.getFontSize(this.font).stringWidth(text) * this.realSize;
		if (this.alignment.x == Text.CENTER_int) { //CENTER
			this.offset.x = -this.bounds.getSize().x/2 - width / 2;
		} else if (this.alignment.x == Text.LEFT_int) { //LEFT
			this.offset.x = -this.bounds.getSize().x;
		} else if (this.alignment.x == Text.RIGHT_int) { //Right
			this.offset.x = -width;
		}
		
		this.offset.add(this.bounds.getPoint2());
	}
	
	public void setAlignment(Vector2 alignment) {
		this.alignment = alignment;
		if (this.started) {
			this.textUpdate();
		}
	}
	
	public void setText(final String text) {
		this.text = text;
		this.textUpdate();
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public void setFont(final Font font) {
		this.font = font;
		this.textUpdate();
	}

	public void setSize(final int size) {
		this.prefferedSize = size;
		this.realSize = size;
		
		if (this.started) { //Error if not started
		this.textUpdate();
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public Color getColor() {
		return this.color;
	}

	public Font getFont() {
		return this.font;
	}

	public float getSize() {
		return this.realSize;
	}
	
	public Vector2 getOffset() {
		return this.offset;
	}
}