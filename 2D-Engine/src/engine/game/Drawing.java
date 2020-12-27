package engine.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.gameobjects.GameObject;
import engine.gameobjects.Transform;
import engine.gameobjects.gamebehaviour.builtin.camera.Camera;
import engine.math.Vector2;

public class Drawing {

	public static float lineMultiplier = 3520f;
	public Graphics2D g;
	private AffineTransform af;

	private Color c;

	public Vector2 cameraOffset = new Vector2();

	private float zoom;

	public GameObject obj;

	private boolean debug = false;

	public Drawing(Graphics2D g) {
		this.g = g;
		af = g.getTransform();
	}

	public void applyTransforms(GameObject obj) {
		this.obj = obj;
		Vector2 diffPos;

		if (obj.getInWorld()) {
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

		Transform transform = new Transform(diffPos, obj.getTransformWithCaution().rotation,
				obj.getTransformWithCaution().scale);

		this.g.rotate(Math.toRadians(transform.rotation));
		transform.position.rotate(-transform.rotation);
		//this.g.scale(transform.scale.x, transform.scale.y);

		this.g.translate(transform.position.x, transform.position.y); // Apply Object Position
	}

	public void drawCircle(Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);

		this.g.drawOval((int) -(scale.x * zoom) / 2, (int) -(scale.y * zoom) / 2, (int) (scale.x * zoom),
				(int) (scale.y * zoom));
	}

	public void drawCircle(Vector2 pos, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		pos.multiply(obj.getTransformWithCaution().scale);

		this.g.drawOval((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void drawImage(BufferedImage img) {
		this.g.drawImage(img, (int) -((img.getWidth() * zoom) / 2), (int) -((img.getHeight() * zoom) / 2),
				(int) (img.getWidth() * zoom), (int) (img.getHeight() * zoom), null);
	}

	public void drawLine(Vector2 vec2) {
		Vector2 vec = vec2.getCopy();
		vec.multiply(obj.getTransformWithCaution().scale);
		this.g.drawLine(0, 0, (int) (vec.x * zoom), (int) (vec.y * zoom));
	}

	public void drawLine(Vector2 vec1, Vector2 vec2) {
		vec2.scale(vec1, zoom);
		this.g.drawLine((int) vec1.x, (int) vec1.y, (int) vec2.x, (int) vec2.y);
	}

	public void drawRect(Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);

		this.g.drawRect((int) -(scale.x * zoom) / 2, (int) -(scale.y * zoom) / 2, (int) (scale.x * zoom),
				(int) (scale.y * zoom));
	}

	public void drawRect(Vector2 pos, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		pos.multiply(obj.getTransformWithCaution().scale);

		this.g.drawRect((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void drawString(String text) {
		Font f = this.g.getFont();
		//Y Scale wird nicht einberechnet was kake ist
		this.g.setFont(this.g.getFont().deriveFont(zoom * 10 * obj.getTransformWithCaution().scale.x));
		this.g.drawString(text, -this.g.getFontMetrics().stringWidth(text) / 2, zoom * 5);
		this.g.setFont(f);
	}

	public void drawString(String text, Vector2 vec2) {
		Font f = this.g.getFont();
		//Y Scale wird nicht einberechnet was kake ist
		this.setFontSize(zoom * 10 * obj.getTransformWithCaution().scale.x);
		this.g.drawString(text, vec2.x * zoom, vec2.y * zoom);
		this.g.setFont(f);
	}

	public void fillCircle(Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);

		this.g.fillOval((int) -(scale.x * zoom) / 2, (int) -(scale.y * zoom) / 2, (int) (scale.x * zoom),
				(int) (scale.y * zoom));
	}

	public void fillCircle(Vector2 pos, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		pos.multiply(obj.getTransformWithCaution().scale);

		this.g.fillOval((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void fillRect(Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);

		this.g.fillRect((int) -(scale.x * zoom) / 2, (int) -(scale.y * zoom) / 2, (int) (scale.x * zoom),
				(int) (scale.y * zoom));
	}

	public void fillRect(Vector2 pos, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		pos.multiply(obj.getTransformWithCaution().scale);

		this.g.fillRect((int) ((pos.x - scale.x) * zoom / 2), (int) ((pos.y - scale.y) * zoom / 2),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public Color getColor() {
		return this.c;
	}

	public FontMetrics getFontSize(Font font) {
		return this.g.getFontMetrics(font);
	}

	public void resetTransform() {
		this.g.setTransform(af);

		if (debug == true) {
			this.g.setColor(Color.GREEN);
			this.g.fillRect((int) obj.getTransformWithCaution().position.x - 2,
					(int) obj.getTransformWithCaution().position.y - 2, 4, 4);
		}
	}

	public void setColor(Color c) {
		this.c = c;
		this.g.setColor(c);
	}

	public void setFont(Font font) {
		this.g.setFont(font);
	}
	
	public void setFontSize(float size) {
		this.g.setFont(this.g.getFont().deriveFont(size));
	}
}