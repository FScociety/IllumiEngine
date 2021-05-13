package engine.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.gameobjects.GameObject;
import engine.gameobjects.Transform;
import engine.gameobjects.gamebehaviour.Bounds;
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
	
	public Graphics2D copyG;

	public Drawing(Graphics2D g) {
		this.g = g;
		this.copyG = (Graphics2D) Window.frame.getGraphics();
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
	
	public void drawCircle(Bounds b) {
		Vector2 p1 = b.getPoint1();
		Vector2 p2 = b.getPoint2();
		p1.substract(this.obj.getTransformWithCaution().position);
		p2.substract(this.obj.getTransformWithCaution().position);
		this.drawRect(p1, Vector2.add(Vector2.invert(p1), p2));
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

		this.g.drawOval((int) (pos.x * zoom), (int) (pos.y * zoom),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public void drawImage(BufferedImage img) {
		Vector2 scale = new Vector2(img.getWidth(), img.getHeight());
		scale.multiply(obj.getTransformWithCaution().scale);
		scale.multiply(zoom);
		
		this.g.drawImage(img, (int) -(scale.x) / 2, (int) -(scale.y) / 2,
				(int) (scale.x), (int) (scale.y), null);
	}
	
	public void drawImage(BufferedImage img, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		
		this.g.drawImage(img, (int) -((scale.x * zoom) / 2), (int) -((scale.y * zoom) / 2),
				(int) (scale.x * zoom), (int) (scale.y * zoom), null);
	}
	
	public void drawImage(BufferedImage img, Vector2 pos, Vector2 scale2) {
		Vector2 scale = scale2.getCopy();
		scale.multiply(obj.getTransformWithCaution().scale);
		pos.multiply(obj.getTransformWithCaution().scale);
		
		this.g.drawImage(img, (int) (pos.x * zoom), (int) (pos.y * zoom),
				(int) (scale.x * zoom), (int) (scale.y * zoom), null);
	}

	public void drawLine(Vector2 vec2) {
		Vector2 vec = vec2.getCopy();
		vec.multiply(obj.getTransformWithCaution().scale);
		this.g.drawLine(0, 0, (int) (vec.x * zoom), (int) (vec.y * zoom));
	}

	public void drawLine(Vector2 vec1, Vector2 vec2) {
		this.g.drawLine((int) (vec1.x * zoom), (int) (vec1.y * zoom), (int) (vec2.x * zoom), (int) (vec2.y * zoom));
	}
	
	public void drawRect(Bounds b) {
		Vector2 p1 = b.getPoint1(); //-100
		Vector2 p2 = b.getPoint2(); //100
		p1.substract(this.obj.getTransformWithCaution().position);
		p2.substract(this.obj.getTransformWithCaution().position);
		this.drawRect(p1, Vector2.add(Vector2.invert(p1), p2)); //-100 ; 200
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

		this.g.drawRect((int) (pos.x * zoom), (int) (pos.y * zoom),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}
	
	public void drawString(String text, float size, Vector2 offset) {
		AffineTransform af = this.g.getTransform(); //Create Copy of Transfrom
		
		float scaling = size;
		Vector2 realOffset = offset.getCopy();
		realOffset.multiply(zoom);
		
		this.g.scale(scaling * zoom, scaling * zoom);
		this.g.drawString(text, realOffset.x / scaling / zoom, realOffset.y / scaling / zoom);
		
		this.g.setTransform(af); //Reset Transform
	}

	/*public void drawString(String text) {
		Font f = this.g.getFont();
		//Y Scale wird nicht einberechnet was kake ist
		this.setFontSize(zoom * 10 * obj.getTransformWithCaution().scale.x);
		//-this.g.getFontMetrics().stringWidth(text) / 2
		//zoom * 5
		this.g.drawString(text, 0, 0);
		this.g.setFont(f);
	}

	public void drawString(String text, Vector2 vec2) {
		Font f = this.g.getFont();
		//Y Scale wird nicht einberechnet was kake ist
		this.setFontSize(zoom * 10 * obj.getTransformWithCaution().scale.x);
		this.g.drawString(text, vec2.x * zoom, vec2.y * zoom);
		this.g.setFont(f);
	}
	
	public void drawString(String text, Vector2 vec2, float size) {
		this.g.scale(size, size);
		
		Font f = this.g.getFont();
		//Y Scale wird nicht einberechnet was kake ist
		this.setFontSize(zoom * size * obj.getTransformWithCaution().scale.x);
		this.g.drawString(text, vec2.x * zoom, vec2.y * zoom);
		this.g.setFont(f);
	}*/
	
	public void fillCircle(Bounds b) {
		Vector2 p1 = b.getPoint1();
		Vector2 p2 = b.getPoint2();
		p1.substract(this.obj.getTransformWithCaution().position);
		p2.substract(this.obj.getTransformWithCaution().position);
		this.drawCircle(p1, Vector2.add(Vector2.invert(p1), p2));
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

		this.g.fillOval((int) (pos.x * zoom), (int) (pos.y * zoom),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}
	
	public void fillRect(Bounds b) {
		Vector2 p1 = b.getPoint1();
		Vector2 p2 = b.getPoint2();
		p1.substract(this.obj.getTransformWithCaution().position);
		p2.substract(this.obj.getTransformWithCaution().position);
		this.fillRect(p1, Vector2.add(Vector2.invert(p1), p2));
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

		this.g.fillRect((int) (pos.x * zoom), (int) (pos.y * zoom),
				(int) (scale.x * zoom), (int) (scale.y * zoom));
	}

	public Color getColor() {
		return this.c;
	}

	public FontMetrics getFontSize(Font font) {
		return this.copyG.getFontMetrics(font);
	}

	public void resetTransform() {
		this.g.setTransform(af);

		if (GameContainer.debug == true) {
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
	
	/*public void setFontSize(float size) {
		this.g.setFont(this.g.getFont().deriveFont(size));
	}*/
}