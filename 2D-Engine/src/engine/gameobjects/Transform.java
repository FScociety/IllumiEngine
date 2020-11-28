package engine.gameobjects;

import java.io.Serializable;

import engine.math.Vector2;

public class Transform implements Serializable {

	public Vector2 position = new Vector2();

	public float rotation = 0;

	public Vector2 scale = new Vector2();
	public Vector2 defaultScale = new Vector2();

	/*
	 * public static Transform parentTransform(Transform transform, Transform
	 * transform2) { return new Transform(Vector2.add(transform.position,
	 * transform2.position), transform.rotation + transform2.rotation,
	 * Vector2.multiply(transform.scale, transform2.scale)); }
	 * 
	 * public static Transform toWindowCords(Transform transform) { Transform trans
	 * = new Transform(Vector2.add(Vector2.substract(transform.position,
	 * Camera.activeCam.gameObject.getTransformWithCaution().position),
	 * Vector2.divide(GameContainer.windowSize, 2)), transform.rotation,
	 * transform.scale); return trans; }
	 */

	public Transform() {
	}

	public Transform(Transform transform) {
		this.position = transform.position;
		this.rotation = transform.rotation;
		this.scale = transform.scale;
		this.defaultScale = transform.defaultScale;

	}

	public Transform(Vector2 position, float rotation, Vector2 scale, Vector2 defaultScale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.defaultScale = defaultScale;
	}

	public Transform getCopy() {
		// System.out.println(this.position + " " + this.rotation + " " + this.scale);
		return new Transform(this.position.getCopy(), this.rotation, this.scale.getCopy(), this.defaultScale.getCopy());
	}

	public void parentTransform(Transform transform) {
		this.position.add(transform.position);
		this.rotation += transform.rotation;
		this.scale.multiply(transform.scale);
	}

	@Override
	public String toString() {
		return ("[Position:" + this.position + ", Rotation:" + this.rotation + ", Scale:" + this.scale + "]");
	}
}