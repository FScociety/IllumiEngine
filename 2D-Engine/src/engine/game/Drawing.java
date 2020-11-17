package engine.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.gameobjects.GameObject;
import engine.gameobjects.Transform;
import engine.gameobjects.gamebehaviour.Camera;
import engine.math.Vector2;

public class Drawing {

	public Graphics2D g;
	private AffineTransform af;
	private Color c;
	
	public static float lineMultiplier = 3520f;
	
	public Vector2 cameraOffset = new Vector2();
	
	private float zoom;

	public Drawing(Graphics2D g) {
		this.g = g;
		af = g.getTransform();
	}

	public void applyTransforms(GameObject obj) {
		Vector2 diffPos;
		
		if (obj.inWorld) { 
			zoom = Camera.activeCam.zoom;
			this.cameraOffset = Camera.activeCam.gameObject.getTransformWithCaution().position;
			diffPos = Vector2.substract(obj.getTransformWithCaution().position, this.cameraOffset);
			diffPos.multiply(new Vector2(Camera.activeCam.zoom));
			diffPos.add(Vector2.divide(GameContainer.windowSize, new Vector2(2)));
		} else {
			zoom = 1;
			this.cameraOffset = new Vector2(0);
			diffPos = obj.getTransform().position;
		}
		
		Transform transform = new Transform(diffPos, obj.getTransformWithCaution().rotation, obj.getTransformWithCaution().scale, obj.getTransformWithCaution().defaultScale);
		
		this.g.rotate(Math.toRadians(transform.rotation));
		transform.position.rotate(-transform.rotation);
		this.g.scale(transform.scale.x, transform.scale.y);
		this.g.translate(transform.position.x / transform.scale.x, transform.position.y / transform.scale.y); //Apply Object Position
	}
	
	public void drawString(String text) {
		Font f = this.g.getFont();
		this.g.setFont(this.g.getFont().deriveFont(zoom * 10));
		this.g.drawString(text, -this.g.getFontMetrics().stringWidth(text) / 2, zoom * 5);
		this.g.setFont(f);
	}
	
	public void drawString(String text, Vector2 vec2) {
		Font f = this.g.getFont();
		this.g.setFont(this.g.getFont().deriveFont(zoom * 10));
		this.g.drawString(text, vec2.x, vec2.y);
		this.g.setFont(f);
	}
	
	public void drawLine(Vector2 vec1, Vector2 vec2) {
		vec2.scale(vec1, zoom);
		this.g.drawLine((int)vec1.x, (int)vec1.y, (int)vec2.x, (int)vec2.y);
	}
	
	public void drawLine(Vector2 vec) {
		this.g.drawLine(0, 0, (int)(vec.x * zoom), (int)(vec.y * zoom));
	}
	
	public void drawImage(BufferedImage img) {
		this.g.drawImage(img, (int) -((img.getWidth() * zoom) / 2), (int) -((img.getHeight() * zoom) / 2), (int) (img.getWidth() * zoom), (int) (img.getHeight() * zoom), null);
	}
	
	public void drawCircle(Vector2 scale) {
		this.g.drawOval((int) - (scale.x * zoom) / 2, (int) - (scale.y * zoom) / 2, (int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void drawCircle(Vector2 pos, Vector2 scale) {
		this.g.drawOval((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2), (int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void fillCircle(Vector2 scale) {
		this.g.fillOval((int) - (scale.x * zoom) / 2, (int) - (scale.y * zoom) / 2, (int) (scale.x * zoom), (int) (scale.y * zoom));
	}
	
	public void fillCircle(Vector2 pos, Vector2 scale) {
		this.g.fillOval((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2), (int) (scale.x * zoom), (int) (scale.y * zoom));
	}
	
	public void drawRect(Vector2 scale) {
		this.g.drawRect((int) - (scale.x * zoom) / 2, (int) - (scale.y * zoom) / 2, (int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void drawRect(Vector2 pos, Vector2 scale) {
		this.g.drawRect((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2), (int) (scale.x * zoom), (int) (scale.y * zoom));
	}
	
	public void fillRect(Vector2 scale) {
		this.g.fillRect((int) - (scale.x * zoom) / 2, (int) - (scale.y * zoom) / 2, (int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void fillRect(Vector2 pos, Vector2 scale) {
		this.g.fillRect((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2), (int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public Color getColor() {
		return this.c;
	}


	public FontMetrics getFontSize(Font font) {
		return this.g.getFontMetrics(font);
	}

	public void resetTransform() {
		this.g.setTransform(af);
	}

	public void setColor(Color c) {
		this.c = c;
		this.g.setColor(c);
	}

	public void setFont(Font font) {
		this.g.setFont(font);
	}
}